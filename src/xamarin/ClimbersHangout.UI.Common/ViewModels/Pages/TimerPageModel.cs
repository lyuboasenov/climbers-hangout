using System;
using ClimbersHangout.Core;
using ClimbersHangout.Core.Models;
using FreshMvvm;
using PropertyChanged;
using SkiaSharp;
using Xamarin.Forms;

namespace ClimbersHangout.UI.Common.ViewModels.Pages {
   [AddINotifyPropertyChangedInterface]
   public class TimerPageModel : FreshBasePageModel {

      private TrainingRunner runner;
      private Command startCommand;
      private Command stopCommand;
      private Command pauseCommand;
      private Command resumeCommand;

      public override void Init(object initData) {
         var training = initData as PeriodGroup;
         if (training == null) {
            CoreMethods.PopPageModel(true);
         } else {
            this.runner = new TrainingRunner(training, 100);
            this.runner.TimerTick += Runner_TimerTick;
            this.runner.Finished += Runner_Finished;
         }
      }

      public string CurrentTime { get; set; }

      public string PeriodType { get; set; }

      public Color Color { get; set; }

      public double Progress { get; set; }

      public double Duration { get; set; }

      #region Commands

      public Command StartCommand {
         get { return startCommand ?? (startCommand = new Command(StartTimer, CanStartTimer)); }
      }

      public Command StopCommand {
         get { return stopCommand ?? (stopCommand = new Command(StopTimer, CanStopTimer)); }
      }

      public Command ResumeCommand {
         get { return resumeCommand ?? (resumeCommand = new Command(ResumeTimer, CanResumeTimer)); }
      }

      public Command PauseCommand {
         get { return pauseCommand ?? (pauseCommand = new Command(PauseTimer, CanPauseTimer)); }
      }

      private void StartTimer() {
         if (CanStartTimer()) {
            runner.Start();
         }
         InvalidateCommands();
      }

      private bool CanStartTimer() {
         return runner.CanStart();
      }

      private void StopTimer() {
         if (CanStopTimer()) {
            runner.Stop();
         }
         InvalidateCommands();
      }

      private bool CanStopTimer() {
         return runner.CanStop();
      }

      private void ResumeTimer() {
         if (CanResumeTimer()) {
            runner.Resume();
         }
         InvalidateCommands();
      }

      private bool CanResumeTimer() {
         return runner.CanResume();
      }

      private void PauseTimer() {
         if (CanPauseTimer()) {
            runner.Pause();
         }
         InvalidateCommands();
      }

      private bool CanPauseTimer() {
         return runner.CanPause();
      }

      private void InvalidateCommands() {
         StartCommand.ChangeCanExecute();
         StopCommand.ChangeCanExecute();
         ResumeCommand.ChangeCanExecute();
         PauseCommand.ChangeCanExecute();
      }
      #endregion Commands

      private void Runner_Finished(object sender, EventArgs e) {
         runner.Stop();
      }

      private void Runner_TimerTick(object sender, TimerEventArgs e) {
         CurrentTime = GetCurrentTime(e.Now, e.Passed, e.Period.Duration);
         PeriodType = e.Period.Type.ToString();
         Color = GetFormsColor(e.Period.Type);
         Progress = e.Now - e.Passed;
         Duration = e.Period.Duration;
      }

      private Color GetFormsColor(PeriodType type) {
         Color color;
         switch (type) {
            case Core.Models.PeriodType.Prep:
               color = Color.DarkGoldenrod;
               break;
            case Core.Models.PeriodType.Rest:
               color = Color.DarkRed;
               break;
            case Core.Models.PeriodType.Pause:
               color = Color.DarkOrange;
               break;
            case Core.Models.PeriodType.Work:
               color = Color.DarkGreen;
               break;
            default:
               color = Color.DarkMagenta;
               break;
         }
         return color;
      }

      private string GetCurrentTime(long now, long passed, long duration) {
         var currentPeriodPassed = now - passed;

         return ((duration - currentPeriodPassed) / 1000).ToString("00");
      }

      private float GetValue(long now) {
         now = now % 60000;

         return now / 60;
      }

      private float GetValue(long now, long passed, long duration) {
         var currentPeriodPassed = now - passed;

         return ((float)currentPeriodPassed * 100 / duration);
      }

      private SKColor GetColor(PeriodType type) {
         string colorCode;
         switch (type) {
            case Core.Models.PeriodType.Prep:
               colorCode = "#fbf831";
               break;
            case Core.Models.PeriodType.Rest:
               colorCode = "#f03434";
               break;
            case Core.Models.PeriodType.Pause:
               colorCode = "#f09b34";
               break;
            case Core.Models.PeriodType.Work:
               colorCode = "#58dc40";
               break;
            default:
               colorCode = "#da40dc";
               break;
         }
         return SKColor.Parse(colorCode);
      }
   }
}
