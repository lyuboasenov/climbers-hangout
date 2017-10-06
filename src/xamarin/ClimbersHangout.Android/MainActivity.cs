using Acr.UserDialogs;
using Android.App;
using Android.Widget;
using Android.OS;
using ClimbersHangout.UI.Common;

namespace ClimbersHangout.Android {
   [Activity(Label = "ClimbersHangout.Android", MainLauncher = true)]
   public class MainActivity : global::Xamarin.Forms.Platform.Android.FormsApplicationActivity {
      protected override void OnCreate(Bundle savedInstanceState) {
         base.OnCreate(savedInstanceState);

         global::Xamarin.Forms.Forms.Init(this, savedInstanceState);
         UserDialogs.Init(() => this);

         LoadApplication(new App());
      }
   }
}

