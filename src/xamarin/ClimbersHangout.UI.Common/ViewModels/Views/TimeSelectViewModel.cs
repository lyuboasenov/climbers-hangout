using System;
using ClimbersHangout.UI.Common.Models;
using FreshMvvm;
using PropertyChanged;
using Xamarin.Forms;

namespace ClimbersHangout.UI.Common.ViewModels.Views {
   [AddINotifyPropertyChangedInterface]
   public class TimeSelectViewModel : FreshBasePageModel {
      public Command minusCommand;
      public Command plusCommand;
      private TimeSelectModel model;

      public TimeSelectViewModel() : this(new TimeSelectModel()) {

      }

      public TimeSelectViewModel(TimeSelectModel model) {
         this.model = model;
         SecondsFocused = true;
         Minutes = model.Time.Minutes;
         Seconds = model.Time.Seconds;
      }

      public int Minutes { get; set; }
      public int Seconds { get; set; }

      public bool IsMinutesFocused { get; set; }
      public bool IsSecondsFocused { get; set; }

      public bool MinutesFocused { get; set; }
      public bool SecondsFocused { get; set; }

      public Command MinusCommand {
         get { return minusCommand ?? (minusCommand = new Command(MinusExecute)); }
      }

      public Command PlusCommand {
         get { return plusCommand ?? (plusCommand = new Command(PlusExecute)); }
      }

      private void OnIsMinutesFocusedChanged() {
         if (IsMinutesFocused) {
            MinutesFocused = true;
            SecondsFocused = false;
         }
      }

      private void OnIsSecondsFocusedChanged() {
         if (IsSecondsFocused) {
            MinutesFocused = false;
            SecondsFocused = true;
         }
      }

      private void MinusExecute() {
         if (MinutesFocused) {
            Minutes = Minutes < 1 ? 0 : Minutes - 1;
         } else {
            Seconds = Seconds < 1 ? 0 : Seconds - 1;
         }
      }

      private void PlusExecute() {
         if (MinutesFocused) {
            Minutes = Minutes > 58 ? 59 : Minutes + 1;
         } else {
            Seconds = Seconds > 58 ? 59 : Seconds + 1;
         }
      }

      private void OnMinutesChanged() {
         ValueChanged();
      }

      private void OnSecondsChanged() {
         ValueChanged();
      }

      private void ValueChanged() { model.Time = TimeSpan.FromSeconds(Minutes * 60 + Seconds); }
   }
}
