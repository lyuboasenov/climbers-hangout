using System;
using ClimbersHangout.UI.Common.Helpers;
using SkiaSharp;
using SkiaSharp.Views.Forms;
using Xamarin.Forms;

namespace ClimbersHangout.UI.Common.Views {
   public class CircularProgressView : SKCanvasView {

      public static readonly BindableProperty ProgressProperty =
         BindableProperty.Create(nameof(Progress), typeof(double), typeof(CircularProgressView), 0d,
            propertyChanged: OnProgressChanged);

      public static readonly BindableProperty MaxProperty =
         BindableProperty.Create(nameof(Max), typeof(double), typeof(CircularProgressView), 100d,
            propertyChanged: OnMaxChanged);

      public static readonly BindableProperty MinProperty =
         BindableProperty.Create(nameof(Min), typeof(double), typeof(CircularProgressView), 0d,
            propertyChanged: OnMinChanged);

      public static readonly BindableProperty ColorProperty =
         BindableProperty.Create(nameof(Color), typeof(Color), typeof(CircularProgressView), Color.Black,
            propertyChanged: OnColorChanged);

      public CircularProgressView() {
         BackgroundColor = Color.Transparent;
         PaintSurface += OnPaintCanvas;
      }

      public double Progress {
         get { return (double)GetValue(ProgressProperty); }
         set { SetValue(ProgressProperty, value); }
      }

      public double Max {
         get { return (double)GetValue(MaxProperty); }
         set { SetValue(MaxProperty, value); }
      }

      public double Min {
         get { return (double)GetValue(MinProperty); }
         set { SetValue(MinProperty, value); }
      }

      public Color Color {
         get { return (Color)GetValue(ColorProperty); }
         set { SetValue(ColorProperty, value); }
      }

      private void OnPaintCanvas(object sender, SKPaintSurfaceEventArgs e) {
         Drawer.Draw(e.Surface.Canvas, e.Info.Width, e.Info.Height, Color, Progress, Min, Max);
      }

      private static void OnProgressChanged(BindableObject bindable, object oldValue, object newValue) {
         ((CircularProgressView)bindable).InvalidateSurface();
      }

      private static void OnMaxChanged(BindableObject bindable, object oldValue, object newValue) {
         ((CircularProgressView)bindable).InvalidateSurface();
      }

      private static void OnMinChanged(BindableObject bindable, object oldValue, object newValue) {
         ((CircularProgressView)bindable).InvalidateSurface();
      }

      private static void OnColorChanged(BindableObject bindable, object oldvalue, object newvalue) {
         ((CircularProgressView)bindable).InvalidateSurface();
      }

      public class Drawer {
         public static void Draw(SKCanvas canvas, int width, int height,
            Color color, double progress, double minimum, double maximum) {
            var lineWidth = 30;
            int marginLeft = 100;
            int marginTop = 100;
            int marginRight = 100;
            int marginBottom = 100;

            canvas.Clear(SKColor.Empty);

            var actualWidth = width - marginLeft - marginRight;
            var actualHeight = height - marginTop - marginBottom;

            var centerX = (int)(actualWidth / 2 + marginLeft);
            var centerY = (int)(actualHeight / 2 + marginTop);

            var radius = (float)(Math.Min(actualWidth, actualHeight) / 2);

            DrawGaugeArea(canvas, radius, centerX, centerY, lineWidth, color);
            DrawGauge(canvas, radius, centerX, centerY, lineWidth, color, progress, minimum, maximum);
         }

         private static void DrawGaugeArea(SKCanvas canvas, float radius, int cx, int cy,
            float strokeWidth, Color color) {
            using (var paint = new SKPaint {
               Style = SKPaintStyle.Stroke,
               StrokeWidth = strokeWidth,
               Color = ColorHelper.TranslateColor(color).WithAlpha(52),
               IsAntialias = true,
            }) {
               canvas.DrawCircle(cx, cy, radius, paint);
            }
         }

         private static void DrawGauge(SKCanvas canvas, float radius, int cx, int cy,
            float strokeWidth, Color color, double progress, double minimum, double maximum) {
            using (var paint = new SKPaint {
               Style = SKPaintStyle.Stroke,
               StrokeWidth = strokeWidth,
               StrokeCap = SKStrokeCap.Round,
               Color = ColorHelper.TranslateColor(color),
               IsAntialias = true,
               //               Shader = SKShader.CreateRadialGradient(
               //                  new SKPoint(cx, cy),
               //                  radius,
               //                  new SKColor[] { ColorHelper.TranslateColor(color), SKColor.Empty },
               //                  new[] { 0, 95f },
               //                  SKShaderTileMode.Repeat)
            }) {
               using (SKPath path = new SKPath()) {
                  var sweepAngle = (float)(360 * (Math.Abs(progress) - minimum) / maximum);
                  path.AddArc(SKRect.Create(cx - radius, cy - radius, 2 * radius, 2 * radius), 270, sweepAngle);
                  canvas.DrawPath(path, paint);
               }
            }
         }
      }
   }
}
