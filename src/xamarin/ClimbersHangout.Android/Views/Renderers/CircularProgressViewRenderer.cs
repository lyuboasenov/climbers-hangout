using System.ComponentModel;
using ClimbersHangout.Android.Views.Renderers;
using ClimbersHangout.UI.Common.Views;
using Xamarin.Forms;
using Xamarin.Forms.Platform.Android;

[assembly: ExportRenderer(typeof(CircularProgressView), typeof(CircularProgressViewRenderer))]

namespace ClimbersHangout.Android.Views.Renderers {
   public class CircularProgressViewRenderer :
      ViewRenderer<UI.Common.Views.CircularProgressView, Android.Views.CircularProgressView> {
      protected override void OnElementChanged(ElementChangedEventArgs<UI.Common.Views.CircularProgressView> e) {
         base.OnElementChanged(e);

         if (e.OldElement != null || this.Element == null) {
            return;
         }
         var progress = new Android.Views.CircularProgressView(Forms.Context) {
            Progress = Element.Progress,
            Color = Element.Color,
            Min = Element.Min,
            Max = Element.Max,
         };

         SetNativeControl(progress);
      }

      protected override void OnElementPropertyChanged(object sender, PropertyChangedEventArgs e) {
         base.OnElementPropertyChanged(sender, e);

         if (e.PropertyName == nameof(Element.Color)) {
            Control.Color = Element.Color;
         } else if (e.PropertyName == nameof(Element.Progress)) {
            Control.Progress = Element.Progress;
         } else if (e.PropertyName == nameof(Element.Min)) {
            Control.Min = Element.Min;
         } else if (e.PropertyName == nameof(Element.Max)) {
            Control.Max = Element.Max;
         }
      }
   }
}