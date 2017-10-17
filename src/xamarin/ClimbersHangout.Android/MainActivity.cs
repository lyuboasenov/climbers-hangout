using Acr.UserDialogs;
using Android.App;
using Android.Content.PM;
using Android.OS;
using ClimbersHangout.UI.Common;
using Plugin.Permissions;

namespace ClimbersHangout.Android {
   [Activity(Label = "ClimbersHangout.Android", MainLauncher = true)]
   public class MainActivity : global::Xamarin.Forms.Platform.Android.FormsApplicationActivity {
      protected override void OnCreate(Bundle savedInstanceState) {
         base.OnCreate(savedInstanceState);

         global::Xamarin.Forms.Forms.Init(this, savedInstanceState);

         UserDialogs.Init(() => this);

         LoadApplication(new App());
      }

      public override void OnRequestPermissionsResult(int requestCode, string[] permissions, Permission[] grantResults) {
         PermissionsImplementation.Current.OnRequestPermissionsResult(requestCode, permissions, grantResults);
      }
   }
}

