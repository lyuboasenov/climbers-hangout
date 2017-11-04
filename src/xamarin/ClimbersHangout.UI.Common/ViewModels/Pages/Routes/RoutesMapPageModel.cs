using System.Collections.Generic;
using ClimbersHangout.Core.Helpers;
using ClimbersHangout.Core.Models.Routes;
using ClimbersHangout.Core.Services;
using ClimbersHangout.UI.Common.Helpers;
using ClimbersHangout.UI.Common.Resources;
using PropertyChanged;
using Xamarin.Forms;
using Xamarin.Forms.Maps;
using GpsHelper = ClimbersHangout.Core.Helpers.GpsHelper;
using Position = ClimbersHangout.Core.Models.Routes.Position;

namespace ClimbersHangout.UI.Common.ViewModels.Pages.Routes {
   [AddINotifyPropertyChangedInterface]
   public class RoutesMapPageModel : BasePageModel {
      private Command<RouteType> addRouteCommand;

      public Xamarin.Forms.Maps.Position SelectedLocation { get; set; }
      public MapSpan VisibleRegion { get; set; }
      public IList<Pin> PinList { get; set; }
      public Command<RouteType> AddRouteCommand {
         get { return addRouteCommand ?? (addRouteCommand = new Command<RouteType>(AddRoute)); }
      }

      public RoutesMapPageModel() {
         Init();
      }

      private async void Init() {
         var position = await UI.Common.Helpers.GpsHelper.GetCurrentPositionAsync(true);
         VisibleRegion = new MapSpan(new Xamarin.Forms.Maps.Position(position.Latitude, position.Longitude), .1, .1);
      }

      private void OnVisibleRegionChanged() {
         UpdateRoutesOnMap();
      }

      private void UpdateRoutesOnMap() {
         var position = new Position(VisibleRegion.Center.Latitude, VisibleRegion.Center.Longitude);

         var availableRoutes =
            RouteService.GetRoutes(position, VisibleRegion.Radius.Kilometers, DistanceUnit.Kilometers);

         var pinList = new List<Pin>();
         if (null != availableRoutes) {
            foreach (var route in availableRoutes) {
               pinList.Add(
                  new Pin() {
                     Label = route.Name,
                     Type = PinType.Generic,
                     Position = new Xamarin.Forms.Maps.Position(route.Position.Latitude, route.Position.Longitude)
                  });
            }
         }
         PinList = pinList;
      }

      private async void AddRoute(RouteType routeType) {
         var imageFile = await this.TakeOrPickImage();
         if (null != imageFile) {
            var position = MediaHelper.GetImageGpsLocation(imageFile) ?? await UI.Common.Helpers.GpsHelper.GetCurrentPositionAsync(false);
            var routeTemplate = new RouteTemplate() {
               ImageLocation = imageFile.Path,
               Position = new Position(position.Latitude, position.Longitude),
               Type = routeType
            };

            await CoreMethods.PushPageModel<AddRoutePageModel>(routeTemplate, true);
         } else {
            await CoreMethods.DisplayAlert(Strings.Error, "No image selected!", Strings.Ok);
         }
      }
   }
}