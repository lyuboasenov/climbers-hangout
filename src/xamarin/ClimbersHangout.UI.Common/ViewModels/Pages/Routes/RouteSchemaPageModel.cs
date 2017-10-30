using System;
using ClimbersHangout.UI.Common.Views;
using Plugin.Media.Abstractions;
using PropertyChanged;

namespace ClimbersHangout.UI.Common.ViewModels.Pages.Routes {
   [AddINotifyPropertyChangedInterface]
   public class RouteSchemaPageModel : BasePageModel {
      private MediaFile imageFile;

      public RouteSetupView.Mode Mode { get; set; }
      public string ImageLocation { get; set; }
      public int ModeSelectIndex { get; set; }
      
      public override void Init(object initData) {
         base.Init(initData);
         imageFile = initData as MediaFile;

         if (null == imageFile) {
            throw new ArgumentNullException("No image file supplied.");
         }

         ImageLocation = imageFile.Path;
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
