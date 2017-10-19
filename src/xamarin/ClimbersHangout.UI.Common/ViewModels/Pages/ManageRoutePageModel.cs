using ClimbersHangout.UI.Common.Views;
using FreshMvvm;
using Plugin.Media.Abstractions;
using PropertyChanged;

namespace ClimbersHangout.UI.Common.ViewModels.Pages {
   [AddINotifyPropertyChangedInterface]
   public class ManageRoutePageModel : FreshBasePageModel {
      private MediaFile imageFile;

      public RouteSetupView.Mode Mode { get; set; }
      public string ImageLocation { get; set; }
      public int ModeSelectIndex { get; set; }

      public override void Init(object initData) {
         imageFile = initData as MediaFile;
         if (imageFile == null) {
            CoreMethods.PopPageModel(true);
         } else {
            ImageLocation = imageFile.Path;
         }
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
