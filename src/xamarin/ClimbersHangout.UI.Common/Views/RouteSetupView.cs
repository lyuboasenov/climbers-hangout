using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using SkiaSharp;
using SkiaSharp.Views.Forms;
using Xamarin.Forms;

namespace ClimbersHangout.UI.Common.Views {
   public class RouteSetupView : SKCanvasView {
      public static readonly BindableProperty ImageLocationProperty =
         BindableProperty.Create(nameof(ImageLocation), typeof(string), typeof(RouteSetupView), null,
            propertyChanged: OnImageLocationChanged);

      Dictionary<long, SKPath> inProgressPaths = new Dictionary<long, SKPath>();
      List<SKPath> completedPaths = new List<SKPath>();
      private SKBitmap bitmap;

      public string ImageLocation {
         get { return (string)GetValue(ImageLocationProperty); }
         set { if (ImageLocation != value) { SetValue(ImageLocationProperty, value); } }
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

         var bitmapAspect = bitmap.Width / bitmap.Height;
         double factor = bitmapAspect > 1 ? 2048 / bitmap.Width : 2048 / bitmap.Height;

         SKImageInfo info = new SKImageInfo((int)(bitmap.Width * factor), (int)(bitmap.Height * factor));
         bitmap = bitmap.Resize(info, SKBitmapResizeMethod.Triangle);

         WidthRequest = bitmap.Width;
         HeightRequest = bitmap.Height;
      }

      protected override void OnTouch(SKTouchEventArgs e) {
         base.OnTouch(e);
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

         SKPoint ConvertToPixel(Point pt) {
            return new SKPoint((float)(CanvasSize.Width * pt.X / Width),
               (float)(CanvasSize.Height * pt.Y / Height));
         }
      }

      protected override void OnPaintSurface(SKPaintSurfaceEventArgs e) {
         base.OnPaintSurface(e);
         SKCanvas canvas = e.Surface.Canvas;
         canvas.Clear();

         if (ImageLocation != null) {
            canvas.DrawBitmap(bitmap, 0, 0);
         }

         foreach (SKPath path in completedPaths) {
            canvas.DrawPath(path, paint);
         }

         foreach (SKPath path in inProgressPaths.Values) {
            canvas.DrawPath(path, paint);
         }
      }
   }
}
