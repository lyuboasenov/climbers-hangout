using Acr.UserDialogs;
using Android.App;
using Android.Widget;
using Android.OS;
using ClimbersHangout.UI.Common;
using Plugin.CrossPlatformTintedImage.Android;

namespace ClimbersHangout.Android {
   [Activity(Label = "ClimbersHangout.Android", MainLauncher = true)]
   public class MainActivity : global::Xamarin.Forms.Platform.Android.FormsApplicationActivity {
      protected override void OnCreate(Bundle savedInstanceState) {
         base.OnCreate(savedInstanceState);

         global::Xamarin.Forms.Forms.Init(this, savedInstanceState);

         //Load themes
         //var x = typeof(Xamarin.Forms.Themes.DarkThemeResources);
         var x = typeof(Xamarin.Forms.Themes.LightThemeResources);
         x = typeof(Xamarin.Forms.Themes.Android.UnderlineEffect);

         TintedImageRenderer.Init();
         UserDialogs.Init(() => this);

         LoadApplication(new App());
      }
   }
}

