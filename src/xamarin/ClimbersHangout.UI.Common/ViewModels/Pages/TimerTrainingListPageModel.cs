using System;
using System.Collections.Generic;
using ClimbersHangout.Core.Models;
using ClimbersHangout.Core.Models.Routes;
using ClimbersHangout.Core.Services;
using ClimbersHangout.UI.Common.ViewModels.Pages.Routes;
using PropertyChanged;
using Xamarin.Forms;
using ClimbersHangout.UI.Common.Helpers;
using ClimbersHangout.UI.Common.Resources;

namespace ClimbersHangout.UI.Common.ViewModels.Pages {
   [AddINotifyPropertyChangedInterface]
   public class TimerTrainingListPageModel : BasePageModel {

      private Command timerCommand;
      private Command<RouteType> addRouteCommand;

      public TimerTrainingListPageModel() {
         TrainingList = TimeTrainingService.GetAllTrainings();
      }

      public PeriodGroup[] TrainingList { get; private set; }

      public PeriodGroup SelectedTraining { get; set; }

      public Command TimerCommand {
         get { return timerCommand ?? (timerCommand = new Command(Timer)); }
      }

      public Command<RouteType> AddRouteCommand {
         get { return addRouteCommand ?? (addRouteCommand = new Command<RouteType>(AddRoute)); }
      }

      public void OnSelectedTrainingChanged() {
         if (null != SelectedTraining) {
            CoreMethods.PushPageModel<TimerPageModel>(SelectedTraining, true);
            SelectedTraining = null;
         }
      }

      private async void Timer() {
         await CoreMethods.PushPageModel<TimerSetupPageModel>(true);
      }

      private async void AddRoute(RouteType routeType) {
         var imageFile = await this.TakeOrPickImage();
         if (null != imageFile) {
            var position = MediaHelper.GetImageGpsLocation(imageFile) ?? await GpsHelper.GetCurrentPosition(false);
            var routeTemplate = new Route() {
               ImageLocation = imageFile.Path,
               Position = new GpsPosition(position.Latitude, position.Longitude),
               Type = routeType
            };

            await CoreMethods.PushPageModel<AddRoutePageModel>(routeTemplate, true);
         } else {
            await CoreMethods.DisplayAlert(Strings.Error, "No image selected!", Strings.Ok);
         }
      }
   }
}
