using System;
using System.Threading.Tasks;
using ClimbersHangout.UI.Common.Views;
using FreshMvvm;
using Plugin.Media;
using Plugin.Media.Abstractions;
using PropertyChanged;

namespace ClimbersHangout.UI.Common.ViewModels.Pages.Routes {
   [AddINotifyPropertyChangedInterface]
   public class RouteSchemaPageModel : BasePageModel {
      private MediaFile imageFile;
      private bool removeImageOnExit = false;

      public RouteSetupView.Mode Mode { get; set; }
      public string ImageLocation { get; set; }
      public int ModeSelectIndex { get; set; }

      public RouteSchemaPageModel() {
      }

      protected override void ViewIsAppearing(object sender, EventArgs e) {
         base.ViewIsAppearing(sender, e);
         InitImage();
      }

      private void InitImage() {

      }

      public void OnModeSelectIndexChanged() {
         switch (ModeSelectIndex) {
            case 0:
               Mode = RouteSetupView.Mode.Move;
               break;
            case 1:
               Mode = RouteSetupView.Mode.Hold;
               break;
            case 2:
               Mode = RouteSetupView.Mode.Line;
               break;
         }
      }
   }
}
