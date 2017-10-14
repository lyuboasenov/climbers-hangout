using ClimbersHangout.Android.Views.Renderers;
using ClimbersHangout.UI.Common.Views;
using Xamarin.Forms;
using Xamarin.Forms.Platform.Android;

[assembly: ExportRenderer(typeof(NoUnderlineEntry), typeof(NoUnderlineEntryRenderer))]
namespace ClimbersHangout.Android.Views.Renderers {
   class NoUnderlineEntryRenderer : EntryRenderer {
      protected override void OnElementChanged(ElementChangedEventArgs<Entry> e) {
         base.OnElementChanged(e);
         Control?.SetBackgroundColor(global::Android.Graphics.Color.Transparent);
      }
   }
}