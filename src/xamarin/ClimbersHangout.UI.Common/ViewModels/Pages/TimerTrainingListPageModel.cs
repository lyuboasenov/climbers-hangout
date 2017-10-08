using ClimbersHangout.Core.Models;
using ClimbersHangout.Core.Services;
using FreshMvvm;
using PropertyChanged;
using Xamarin.Forms;

namespace ClimbersHangout.UI.Common.ViewModels.Pages {
   [AddINotifyPropertyChangedInterface]
   public class TimerTrainingListPageModel : FreshBasePageModel {

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

      private void AddTraining() {

      }
   }
}
