using ClimbersHangout.UI.Common.Resources;
using PropertyChanged;

namespace ClimbersHangout.UI.Common.ViewModels.Pages.Routes {
   [AddINotifyPropertyChangedInterface]
   public class AddRoutePageModel : MultiPageModel {
      public AddRoutePageModel() {
         
      }

      public override void Init(object initData) {
         base.Init(initData);
         AddChild(Strings.AddRouteDetailsPageTitle, new RouteDetailsPageModel());
         AddChild(Strings.AddRouteSchemaPageTitle, new RouteSchemaPageModel(), initData);
      }
   }
}
