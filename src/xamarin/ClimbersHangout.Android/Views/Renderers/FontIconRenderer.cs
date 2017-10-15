using Android.Graphics;
using ClimbersHangout.Android.Views.Renderers;
using ClimbersHangout.UI.Common.Views;
using Xamarin.Forms;
using Xamarin.Forms.Platform.Android;

[assembly: ExportRenderer(typeof(FontIcon), typeof(FontIconRenderer))]
namespace ClimbersHangout.Android.Views.Renderers {
   /// <summary>
   /// Add the FontAwesome.ttf to the Assets folder and mark as "Android Asset"
   /// </summary>
   public class FontIconRenderer : LabelRenderer {
      protected override void OnElementChanged(ElementChangedEventArgs<Label> e) {
         base.OnElementChanged(e);
         if (e.OldElement == null) {
            //The ttf in /Assets is CaseSensitive, so name it FontAwesome.ttf
            Control.Typeface = Typeface.CreateFromAsset(Forms.Context.Assets, FontIcon.Typeface + ".ttf");
         }
      }
   }
}