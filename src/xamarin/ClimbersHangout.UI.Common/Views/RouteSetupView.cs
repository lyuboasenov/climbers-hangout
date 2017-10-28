using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using ClimbersHangout.Core.Models.Routes;
using ClimbersHangout.UI.Common.Helpers;
using SkiaSharp;
using SkiaSharp.Views.Forms;
using Xamarin.Forms;
using Path = ClimbersHangout.Core.Models.Routes.Path;

namespace ClimbersHangout.UI.Common.Views {
   public class RouteSetupView : SKCanvasView {
      private const double ABSOLUTE_MIN_SCALE_FACTOR = 1;
      private const double ABSOLUTE_MAX_SCALE_FACTOR = 4;

      private readonly Route route = new Route();

      private Path inProgressPath;
      private Hold inProgressHold;

      private SKBitmap bitmap;

      /// <summary>
      /// Current bitmap scale factor
      /// </summary>
      private double scaleFactor = 1;
      /// <summary>
      /// Minimum scale factor so the image can fill the whole canvas area
      /// </summary>
      private double minimumScaleFactor = 1;
      /// <summary>
      /// Used to save scale factor on zoom start. In order to revert to it on cancel
      /// </summary>
      private double startScaleFactor;

      /// <summary>
      /// Used to verify canvas size changes on surface invalidation
      /// in order to calculate minimum scale factor only when that happen.
      /// </summary>
      private SKSize lastCanvasSize;

      /// <summary>
      /// Current point relative from which the background is taken.
      /// </summary>
      private Point originPoint = new Point(0, 0);

      /// <summary>
      /// Saved location durring the panning action
      /// </summary>
      private Point startPanTopLeftPoint;

      /// <summary>
      /// Bag for holding current touch events
      /// </summary>
      private readonly Dictionary<long, TouchManipulationInfo> touchDictionary =
         new Dictionary<long, TouchManipulationInfo>();

      public static readonly BindableProperty EditModeProperty =
         BindableProperty.Create(nameof(EditMode), typeof(Mode), typeof(RouteSetupView), Mode.Move,
            propertyChanged: OnImageLocationChanged);

      public static readonly BindableProperty ImageLocationProperty =
         BindableProperty.Create(nameof(ImageLocation), typeof(string), typeof(RouteSetupView), null,
            propertyChanged: OnImageLocationChanged);

      public Mode EditMode {
         get { return (Mode)GetValue(EditModeProperty); }
         set { if (EditMode != value) { SetValue(EditModeProperty, value); } }
      }

      public string ImageLocation {
         get { return (string)GetValue(ImageLocationProperty); }
         set { if (ImageLocation != value) { SetValue(ImageLocationProperty, value); } }
      }

      public RouteSetupView() {
         EnableTouchEvents = true;
      }

      private void VerifyPanningInBounds() {
         if (null != bitmap) {
            var boundWidth = lastCanvasSize.Width / scaleFactor;
            var boundHeight = lastCanvasSize.Height / scaleFactor;
            float x = (float)Math.Min(bitmap.Width - boundWidth, originPoint.X);
            x = Math.Max(0, x);
            float y = (float)Math.Min(bitmap.Height - boundHeight, originPoint.Y);
            y = Math.Max(0, y);

            originPoint = new Point(x, y);
         }
      }

      /// <summary>
      /// Recalculates the scale factor so ensuring it is in the
      /// absolute scale bounds. This method assumes that minimumScaleFactor
      /// is greater then ABSOLUTE_MIN_SCALE_FACTOR
      /// </summary>
      /// <param name="scale"></param>
      private void VerifyScaleFactorInBounds() {
         scaleFactor = Math.Max(minimumScaleFactor, scaleFactor);
         scaleFactor = Math.Min(ABSOLUTE_MAX_SCALE_FACTOR, scaleFactor);

         VerifyPanningInBounds();
      }

      /// <summary>
      ///Calculate minimum scale factor to ensure bitmap is scale so 
      ///that the whole are of the control is covered
      /// </summary>
      private void RecalculateMinimumScaleFactor() {
         if (null != bitmap) {
            double boundsAspectRatio = CanvasSize.Width / CanvasSize.Height;
            double widthAspect = CanvasSize.Width / bitmap.Width;
            double bitmapAspectRatio = (double)bitmap.Width / bitmap.Height;

            minimumScaleFactor = Math.Max(widthAspect, (bitmapAspectRatio / boundsAspectRatio) * widthAspect);
            minimumScaleFactor = Math.Max(ABSOLUTE_MIN_SCALE_FACTOR, minimumScaleFactor);

            VerifyScaleFactorInBounds();
         }
      }

      /// <summary>
      /// On change method for image location. Reloads the image background from the new location.
      /// </summary>
      /// <param name="bindable"></param>
      /// <param name="oldvalue"></param>
      /// <param name="newvalue"></param>
      private static void OnImageLocationChanged(BindableObject bindable, object oldvalue, object newvalue) {
         RouteSetupView view = ((RouteSetupView)bindable);
         view.InitImage();
         view.InvalidateSurface();
      }

      /// <summary>
      /// Reloads image background from ImageLocation.
      /// </summary>
      private void InitImage() {
         bitmap = SkiaSharpHelper.LoadBitmap(ImageLocation, Route.MAX_SIZE);
         route.ImageLocation = ImageLocation;
      }

      /// <summary>
      /// Overload to capture touch events forwarded from base class.
      /// </summary>
      /// <param name="e"></param>
      protected override void OnTouch(SKTouchEventArgs e) {
         base.OnTouch(e);

         if (!e.Handled) {
            OnTouchEvent(e.Id, e.ActionType, e.Location);
            e.Handled = true;
         }
      }

      /// <summary>
      /// Method to handle touch event
      /// </summary>
      /// <param name="id"></param>
      /// <param name="type"></param>
      /// <param name="location"></param>
      private void OnTouchEvent(long id, SKTouchAction type, SKPoint location) {
         switch (type) {
            case SKTouchAction.Pressed:
               if (touchDictionary.ContainsKey(id)) {
                  touchDictionary.Remove(id);
               }
               touchDictionary.Add(id, new TouchManipulationInfo {
                  InitialPoint = location,
                  PreviousPoint = location,
                  NewPoint = location
               });
               ProcessTouchEvent(type);
               break;

            case SKTouchAction.Moved:
               TouchManipulationInfo info = touchDictionary[id];
               info.NewPoint = location;
               ProcessTouchEvent(type);
               info.PreviousPoint = info.NewPoint;
               break;

            case SKTouchAction.Released:
               touchDictionary[id].NewPoint = location;
               ProcessTouchEvent(type);
               touchDictionary.Remove(id);
               break;

            case SKTouchAction.Cancelled:
               ProcessTouchEvent(type);
               touchDictionary.Remove(id);
               break;
         }
      }

      /// <summary>
      /// Process touch event depending on EditMode.
      /// </summary>
      /// <param name="type"></param>
      private void ProcessTouchEvent(SKTouchAction type) {
         TouchManipulationInfo[] infos = new TouchManipulationInfo[touchDictionary.Count];
         touchDictionary.Values.CopyTo(infos, 0);

         switch (EditMode) {
            case Mode.Move:
               NormalizeBackground(infos, type);
               break;
            case Mode.Hold:
               ManageHolds(infos, type);
               break;
            case Mode.Line:
               ManageLines(infos, type);
               break;
         }
      }

      /// <summary>
      /// Handles touch events in Holds EditMode
      /// </summary>
      /// <param name="infos"></param>
      /// <param name="type"></param>
      private void ManageHolds(TouchManipulationInfo[] infos, SKTouchAction type) {
         if (EditMode == Mode.Hold) {
            var currentPoint = new Point(
               infos[0].NewPoint.X / scaleFactor + originPoint.X,
               infos[0].NewPoint.Y / scaleFactor + originPoint.Y);
            double radius = 20d;
            if (infos.Length > 1) {
               radius = 20
                        + infos[0].NewPoint.Distance(infos[1].NewPoint)
                        - infos[0].InitialPoint.Distance(infos[1].InitialPoint);
               radius = radius < 20 ? 20 : radius;
            } else if (inProgressHold != null) {
               //if current hold have already been enlarged
               radius = inProgressHold.Radius;
            }

            switch (type) {
               case SKTouchAction.Pressed:
                  if (inProgressHold == null) {
                     inProgressHold = route.GetNearestHoldOrNew(currentPoint);
                     inProgressHold.Center = currentPoint;
                     inProgressHold.Radius = radius;
                     InvalidateSurface();
                  }
                  break;
               case SKTouchAction.Moved:
                  inProgressHold.Center = currentPoint;
                  inProgressHold.Radius = radius;
                  InvalidateSurface();
                  break;
               case SKTouchAction.Released:
                  //only if all fingers are released, the in progress hold
                  //is counted as completed
                  if (infos.Count() <= 1) {
                     route.AddHold(inProgressHold);
                     inProgressHold = null;
                  }
                  InvalidateSurface();
                  break;
               case SKTouchAction.Cancelled:
                  if (infos.Count() <= 1) {
                     inProgressHold = null;
                  }
                  InvalidateSurface();
                  break;
            }
         }
      }

      /// <summary>
      /// Process touch event in Lines EditMode
      /// </summary>
      /// <param name="infos"></param>
      /// <param name="type"></param>
      private void ManageLines(TouchManipulationInfo[] infos, SKTouchAction type) {
         if (EditMode == Mode.Line) {
            var currentPoint = new Point(
               (float)(infos[0].NewPoint.X / scaleFactor + originPoint.X),
               (float)(infos[0].NewPoint.Y / scaleFactor + originPoint.Y));
            switch (type) {
               case SKTouchAction.Pressed:
                  inProgressPath = new Path();
                  inProgressPath.AddPoint(currentPoint);
                  InvalidateSurface();
                  break;
               case SKTouchAction.Moved:
                  inProgressPath.AddPoint(currentPoint);
                  InvalidateSurface();
                  break;
               case SKTouchAction.Released:
                  route.AddPath(inProgressPath);
                  inProgressPath = null;
                  InvalidateSurface();
                  break;
               case SKTouchAction.Cancelled:
                  inProgressPath = null;
                  InvalidateSurface();
                  break;
            }
         }
      }

      /// <summary>
      /// Normalizes background image depending on top-left offset and scale factor
      /// </summary>
      /// <param name="infos"></param>
      /// <param name="type"></param>
      private void NormalizeBackground(TouchManipulationInfo[] infos, SKTouchAction type) {
         if (infos.Length == 1) {
            switch (type) {
               case SKTouchAction.Pressed:
                  startPanTopLeftPoint = originPoint;
                  break;
               case SKTouchAction.Moved:
                  double totalX = (infos[0].NewPoint.X - infos[0].InitialPoint.X) / scaleFactor;
                  double totalY = (infos[0].NewPoint.Y - infos[0].InitialPoint.Y) / scaleFactor;
                  originPoint = new Point(
                     startPanTopLeftPoint.X - totalX,
                     startPanTopLeftPoint.Y - totalY);

                  VerifyPanningInBounds();
                  InvalidateSurface();
                  break;
               case SKTouchAction.Released:
                  VerifyPanningInBounds();
                  InvalidateSurface();
                  break;
               case SKTouchAction.Cancelled:
                  originPoint = startPanTopLeftPoint;
                  VerifyPanningInBounds();
                  InvalidateSurface();
                  break;
            }
         } else {
            double initialDistance = infos[0].InitialPoint.Distance(infos[1].InitialPoint);
            double distance = infos[0].NewPoint.Distance(infos[1].NewPoint);
            double scale = distance / initialDistance;
            switch (type) {
               case SKTouchAction.Pressed:
                  startScaleFactor = scaleFactor;
                  break;
               case SKTouchAction.Moved:
                  scaleFactor = scale * startScaleFactor;
                  VerifyScaleFactorInBounds();
                  InvalidateSurface();
                  break;
               case SKTouchAction.Released:
                  scaleFactor = scale * startScaleFactor;
                  VerifyScaleFactorInBounds();
                  InvalidateSurface();
                  break;
               case SKTouchAction.Cancelled:
                  scaleFactor = startScaleFactor;
                  VerifyScaleFactorInBounds();
                  InvalidateSurface();
                  break;
            }
         }
      }

      protected override void OnPaintSurface(SKPaintSurfaceEventArgs e) {
         base.OnPaintSurface(e);

         if (lastCanvasSize != CanvasSize) {
            RecalculateMinimumScaleFactor();
            lastCanvasSize = CanvasSize;
         }

         SKCanvas canvas = e.Surface.Canvas;
         canvas.Clear();

         DrawImage(canvas);

         DrawHolds(canvas);

         DrawPaths(canvas);
      }

      private void DrawPaths(SKCanvas canvas) {
         foreach (Path path in route.Paths) {
            canvas.DrawPath(path.ConvertToSKPath(originPoint, scaleFactor), SkiaSharpHelper.CompletedPaint);
         }

         if (null != inProgressPath) {
            canvas.DrawPath(inProgressPath.ConvertToSKPath(originPoint, scaleFactor), SkiaSharpHelper.InProgressPaint);
         }
      }

      private void DrawImage(SKCanvas canvas) {
         if (bitmap != null) {
            var bounds = lastCanvasSize;

            var dest = new SKRect() {
               Location = new SKPoint(0, 0),
               Size = new SKSize(bounds.Width, bounds.Height)
            };

            var source = new SKRect() {
               Location = new SKPoint((float)originPoint.X, (float)originPoint.Y),
               Size = new SKSize(
                  (float)(bounds.Width / scaleFactor),
                  (float)(bounds.Height / scaleFactor))
            };

            canvas.DrawBitmap(bitmap, source, dest);
         }
      }

      private void DrawHolds(SKCanvas canvas) {
         foreach (var hold in route.Holds) {
            if (hold != inProgressHold) {
               DrawHold(canvas, hold, SkiaSharpHelper.CompletedPaint);
            }
         }

         if (null != inProgressHold) {
            DrawHold(canvas, inProgressHold, SkiaSharpHelper.InProgressPaint);
         }
      }

      private void DrawHold(SKCanvas canvas, Hold hold, SKPaint paint) {
         canvas.DrawCircle(
            (float)((hold.Center.X - originPoint.X) * scaleFactor),
            (float)((hold.Center.Y - originPoint.Y) * scaleFactor),
            (float)(hold.Radius * scaleFactor),
            paint);
      }

      public enum Mode {
         Move,
         Line,
         Hold
      }

      private class TouchManipulationInfo {
         public SKPoint PreviousPoint { set; get; }
         public SKPoint NewPoint { set; get; }
         public SKPoint InitialPoint { get; set; }
      }
   }
}