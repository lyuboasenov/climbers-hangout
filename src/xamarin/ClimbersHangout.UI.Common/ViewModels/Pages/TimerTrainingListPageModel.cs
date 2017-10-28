using ClimbersHangout.Core.Models;
using ClimbersHangout.Core.Services;
using ClimbersHangout.UI.Common.ViewModels.Pages.Routes;
using PropertyChanged;
using Xamarin.Forms;
using ClimbersHangout.UI.Common.Helpers;
using ClimbersHangout.UI.Common.Resources;
using FreshMvvm;
using Xamarin.Forms.BehaviorsPack;

namespace ClimbersHangout.UI.Common.ViewModels.Pages {
   [AddINotifyPropertyChangedInterface]
   public class TimerTrainingListPageModel : BasePageModel {

      private Command addTrainingCommand;

      public TimerTrainingListPageModel() {
         TrainingList = TimeTrainingService.GetAllTrainings();
      }

      public PeriodGroup[] TrainingList { get; private set; }

      public PeriodGroup SelectedTraining { get; set; }

      public Command AddTrainingCommand {
         get { return addTrainingCommand ?? (addTrainingCommand = new Command(AddTraining)); }
      }

      public void OnSelectedTrainingChanged() {
         if (null != SelectedTraining) {
            CoreMethods.PushPageModel<TimerPageModel>(SelectedTraining, true);
            SelectedTraining = null;
         }
      }

      private async void AddTraining() {
         var imageFile = await this.TakeOrPickImage();
         if (null != imageFile) {
            //await CoreMethods.PushPageModel<AddRoutePageModel>(imageFile, true);
            var tabbedNavContainer = new FreshTabbedNavigationContainer();
            tabbedNavContainer.AddTab<RouteDetailsPageModel>(Strings.AddRouteDetailsPageTitle, null);
            tabbedNavContainer.AddTab<RouteSchemaPageModel>(Strings.AddRouteSchemaPageTitle, null);

            await CoreMethods.PushNewNavigationServiceModal(tabbedNavContainer, null);

         } else {
            await CoreMethods.DisplayAlert("Error", "No image selected!", string.Empty);
         }
      }
   }
}
