using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using SkiaSharp;
using SkiaSharp.Views.Forms;
using Xamarin.Forms;

namespace ClimbersHangout.UI.Common.Views {
   public class RouteSetupView : SKCanvasView {
      private const double INITIAL_MAX_SIZE = 2048d;
      private const double ABSOLUTE_MIN_SCALE_FACTOR = 1;
      private const double ABSOLUTE_MAX_SCALE_FACTOR = 4;

      private Dictionary<long, SKPath> inProgressPaths = new Dictionary<long, SKPath>();
      private List<SKPath> completedPaths = new List<SKPath>();
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

      private SKPoint topLeftPoint = new SKPoint(0, 0);

      private SKPoint startPanTopLeftPoint;

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
         var pinchRecognizer = new PinchGestureRecognizer();
         pinchRecognizer.PinchUpdated += PinchRecognizer_PinchUpdated;
         GestureRecognizers.Add(pinchRecognizer);

         var panRecognizer = new PanGestureRecognizer();
         panRecognizer.PanUpdated += PanRecognizer_PanUpdated;
         GestureRecognizers.Add(panRecognizer);

         var tapRecognizer = new TapGestureRecognizer();
         tapRecognizer.Tapped += TapRecognizer_Tapped;
      }

      private void TapRecognizer_Tapped(object sender, EventArgs e) {

      }

      private void PanRecognizer_PanUpdated(object sender, PanUpdatedEventArgs e) {
         if (EditMode == Mode.Move) {
            switch (e.StatusType) {
               case GestureStatus.Started:
                  startPanTopLeftPoint = topLeftPoint;
                  break;
               case GestureStatus.Running:
                  topLeftPoint = new SKPoint((float)(startPanTopLeftPoint.X - e.TotalX), (float)(startPanTopLeftPoint.Y - e.TotalY));
                  VerifyPanningInBounds();
                  InvalidateSurface();
                  break;
               case GestureStatus.Completed:
                  VerifyPanningInBounds();
                  InvalidateSurface();
                  break;
               case GestureStatus.Canceled:
                  topLeftPoint = startPanTopLeftPoint;
                  VerifyPanningInBounds();
                  InvalidateSurface();
                  break;
            }
         }
      }

      private void PinchRecognizer_PinchUpdated(object sender, PinchGestureUpdatedEventArgs e) {
         if (EditMode == Mode.Move) {
            switch (e.Status) {
               case GestureStatus.Started:
                  startScaleFactor = scaleFactor;
                  break;
               case GestureStatus.Running:
                  scaleFactor *= e.Scale;
                  VerifyScaleFactorInBounds();
                  InvalidateSurface();
                  break;
               case GestureStatus.Completed:
                  scaleFactor *= e.Scale;
                  VerifyScaleFactorInBounds();
                  InvalidateSurface();
                  break;
               case GestureStatus.Canceled:
                  scaleFactor = startScaleFactor;
                  VerifyScaleFactorInBounds();
                  InvalidateSurface();
                  break;
            }
         }
      }

      private void VerifyPanningInBounds() {
         var boundWidth = lastCanvasSize.Width / scaleFactor;
         var boundHeight = lastCanvasSize.Height / scaleFactor;
         float x = (float)Math.Min(bitmap.Width - boundWidth, topLeftPoint.X);
         x = Math.Max(0, x);
         float y = (float)Math.Min(bitmap.Height - boundHeight, topLeftPoint.Y);
         y = Math.Max(0, y);

         topLeftPoint = new SKPoint(x, y);
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
         double boundsAspectRatio = CanvasSize.Width / CanvasSize.Height;
         double widthAspect = CanvasSize.Width / bitmap.Width;
         double bitmapAspectRatio = (double)bitmap.Width / bitmap.Height;

         minimumScaleFactor = Math.Max(widthAspect, (bitmapAspectRatio / boundsAspectRatio) * widthAspect);
         minimumScaleFactor = Math.Max(ABSOLUTE_MIN_SCALE_FACTOR, minimumScaleFactor);

         VerifyScaleFactorInBounds();
      }

      //TODO: make paint to be dynamic
      SKPaint paint = new SKPaint {
         Style = SKPaintStyle.Stroke,
         Color = SKColors.Blue,
         StrokeWidth = 10,
         StrokeCap = SKStrokeCap.Round,
         StrokeJoin = SKStrokeJoin.Round
      };

      private static void OnImageLocationChanged(BindableObject bindable, object oldvalue, object newvalue) {
         RouteSetupView view = ((RouteSetupView)bindable);
         view.InitImage();
         view.InvalidateSurface();
      }

      private void InitImage() {
         using (SKStream stream = new SKManagedStream(File.OpenRead(ImageLocation))) {
            bitmap = SKBitmap.Decode(stream);
         }

         var bitmapAspectRatio = (double)bitmap.Width / bitmap.Height;
         double factor = bitmapAspectRatio > 1 ? INITIAL_MAX_SIZE / bitmap.Width : INITIAL_MAX_SIZE / bitmap.Height;

         SKImageInfo info = new SKImageInfo((int)(bitmap.Width * factor), (int)(bitmap.Height * factor));
         bitmap = bitmap.Resize(info, SKBitmapResizeMethod.Triangle);
      }

      protected override void OnTouch(SKTouchEventArgs e) {
         base.OnTouch(e);
         if (EditMode == Mode.Line) {
            switch (e.ActionType) {
               case SKTouchAction.Pressed:
                  if (!inProgressPaths.ContainsKey(e.Id)) {
                     SKPath path = new SKPath();
                     path.MoveTo(e.Location);
                     inProgressPaths.Add(e.Id, path);
                     InvalidateSurface();
                  }
                  break;
               case SKTouchAction.Moved:
                  if (inProgressPaths.ContainsKey(e.Id)) {
                     SKPath path = inProgressPaths[e.Id];
                     path.LineTo(e.Location);
                     InvalidateSurface();
                  }
                  break;
               case SKTouchAction.Released:
                  if (inProgressPaths.ContainsKey(e.Id)) {
                     completedPaths.Add(inProgressPaths[e.Id]);
                     inProgressPaths.Remove(e.Id);
                     InvalidateSurface();
                  }
                  break;
               case SKTouchAction.Cancelled:
                  if (inProgressPaths.ContainsKey(e.Id)) {
                     inProgressPaths.Remove(e.Id);
                     InvalidateSurface();
                  }
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

         foreach (SKPath path in completedPaths) {
            canvas.DrawPath(path, paint);
         }

         foreach (SKPath path in inProgressPaths.Values) {
            canvas.DrawPath(path, paint);
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
               Location = topLeftPoint,
               Size = new SKSize(
                  (float)(bounds.Width / scaleFactor),
                  (float)(bounds.Height / scaleFactor))
            };

            canvas.DrawBitmap(bitmap, source, dest);
         }
      }

      public enum Mode {
         Move,
         Line,
         Hold
      }
   }
}
