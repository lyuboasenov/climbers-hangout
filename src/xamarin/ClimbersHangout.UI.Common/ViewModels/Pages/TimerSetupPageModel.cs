using System;
using System.Collections.Generic;
using ClimbersHangout.Core.Helpers;
using ClimbersHangout.Core.Models;
using ClimbersHangout.UI.Common.Models;
using FreshMvvm;
using PropertyChanged;
using Xamarin.Forms;

namespace ClimbersHangout.UI.Common.ViewModels.Pages {
   [AddINotifyPropertyChangedInterface]
   public class TimerSetupPageModel : BasePageModel {
      private Command startTimerCommand;

      public TimerSetupPageModel() {
         PreparationTimeModel = new TimeSelectModel() { Time = TimeSpan.FromSeconds(20) };
         WorkTimeModel = new TimeSelectModel() { Time = TimeSpan.FromSeconds(6) };
         RestTimeModel = new TimeSelectModel() { Time = TimeSpan.FromSeconds(4) };
         PauseTimeModel = new TimeSelectModel() { Time = TimeSpan.FromMinutes(2) };
         RepetitionCountModel = new NumberUpDownModel() { MinValue = 1, Value = 5 };
      }

      public Command StartTimerCommand {
         get { return startTimerCommand ?? (startTimerCommand = new Command(StartTimer, CanStartTimer)); }
      }

      public TimeSelectModel PreparationTimeModel { get; set; }
      public TimeSelectModel WorkTimeModel { get; set; }
      public TimeSelectModel RestTimeModel { get; set; }
      public TimeSelectModel PauseTimeModel { get; set; }
      public NumberUpDownModel RepetitionCountModel { get; set; }

      private void StartTimer() {
         CoreMethods.PushPageModel<TimerPageModel>(CreateTraining(), true);
      }

      private PeriodGroup CreateTraining() {
         return PeriodHelper.CreateSimpleTimerTraining(
            (long)PreparationTimeModel.Time.TotalMilliseconds,
            RepetitionCountModel.Value,
            (long)WorkTimeModel.Time.TotalMilliseconds,
            (long)RestTimeModel.Time.TotalMilliseconds,
            (long)PauseTimeModel.Time.TotalMilliseconds);
      }

      private bool CanStartTimer() {
         return true;
      }
   }
}
