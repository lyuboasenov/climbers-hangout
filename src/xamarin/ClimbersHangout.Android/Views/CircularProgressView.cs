using Android.Content;
using SkiaSharp.Views.Android;
using Android.Util;
using System;
using Android.Runtime;
using Xamarin.Forms;

namespace ClimbersHangout.Android.Views {

   public class CircularProgressView : SKCanvasView {
      private double progress;
      private double min;
      private double max;
      private Color color;

      #region Constructors

      public CircularProgressView(Context context) : base(context) {
         this.PaintSurface += OnPaintCanvas;
      }

      public CircularProgressView(Context context, IAttributeSet attributes) : base(context, attributes) {
         this.PaintSurface += OnPaintCanvas;
      }

      public CircularProgressView(Context context, IAttributeSet attributes, int defStyleAtt) : base(context, attributes, defStyleAtt) {
         this.PaintSurface += OnPaintCanvas;
      }

      public CircularProgressView(IntPtr ptr, JniHandleOwnership jni) : base(ptr, jni) {
         this.PaintSurface += OnPaintCanvas;
      }

      #endregion

      public double Progress {
         get { return progress; }
         set {
            if (progress != value) {
               progress = value;
               Invalidate();
            }
         }
      }

      public double Min {
         get { return min; }
         set {
            if (min != value) {
               min = value;
               Invalidate();
            }
         }
      }

      public double Max {
         get { return max; }
         set {
            if (max != value) {
               max = value;
               Invalidate();
            }
         }
      }

      public Color Color {
         get { return color; }
         set {
            if (color != value) {
               color = value;
               Invalidate();
            }
         }
      }

      private void OnPaintCanvas(object sender, SKPaintSurfaceEventArgs e) {
         UI.Common.Views.CircularProgressView.Drawer.Draw(
            e.Surface.Canvas, e.Info.Width, e.Info.Height, Color, Progress, Min, Max);
      }
   }
}