using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using ClimbersHangout.Core.Models.Routes;
using ClimbersHangout.Core.Services;
using ClimbersHangout.UI.Common.Resources;
using PropertyChanged;
using Xamarin.Forms;

namespace ClimbersHangout.UI.Common.ViewModels.Pages.Routes {
   [AddINotifyPropertyChangedInterface]
   public class AddRoutePageModel : MultiPageModel, Okable, Cancelable {
      private RouteTemplate route;
      private Command okCommand;
      private Command cancelCommand;
      private RouteDetailsPageModel detailsPageModel;
      private RouteSchemaPageModel schemaPageModel;

      public Command OkCommand {
         get { return okCommand ?? (okCommand = new Command(Ok)); }
      }

      public Command CancelCommand {
         get { return cancelCommand ?? (cancelCommand = new Command(Cancel)); }
      }

      public AddRoutePageModel() {
         detailsPageModel = new RouteDetailsPageModel() { Parent = this };
         schemaPageModel = new RouteSchemaPageModel() { Parent = this };
      }

      public override void Init(object initData) {
         base.Init(initData);
         route = initData as RouteTemplate;
         if (null == route) {
            throw new ArgumentNullException("Invalid route template supplied.");
         }

         if (string.IsNullOrEmpty(route.ImageLocation)) {
            throw new ArgumentNullException("No image file supplied.");
         }

         AddChild(Strings.AddRouteDetailsPageTitle, detailsPageModel, initData);
         AddChild(Strings.AddRouteSchemaPageTitle, schemaPageModel, initData);
      }

      private async void Ok() {
         SaveRoute();
         PopPageModel();
      }

      private void SaveRoute() {
         RouteService.SaveRoute(route);
      }

      private async void Cancel() {
         PopPageModel();
      }

      private async void PopPageModel() {
         await CoreMethods.PopPageModel(true);
      }
   }
}
