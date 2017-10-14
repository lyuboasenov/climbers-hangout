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
      private bool isChangingValues;
      private int MAX_VALUE = 3599;

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
         get { return minusCommand ?? (minusCommand = new Command(MinusExecute, MinusCanExecute)); }
      }

      public Command PlusCommand {
         get { return plusCommand ?? (plusCommand = new Command(PlusExecute, PlusCanExecute)); }
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

      private bool MinusCanExecute() {
         return model.Time.TotalSeconds > 0;
      }

      private bool PlusCanExecute() {
         return model.Time.TotalSeconds < MAX_VALUE;
      }

      private void MinusExecute() {
         if (MinutesFocused) {
            Minutes = Minutes < 1 ? 0 : Minutes - 1;
         } else {
            Seconds = Seconds < 1 ? 0 : Seconds - 1;
         }
         InvalidateCommands();
      }

      private void PlusExecute() {
         if (MinutesFocused) {
            Minutes = Minutes > 58 ? 59 : Minutes + 1;
         } else {
            Seconds = Seconds > 58 ? 59 : Seconds + 1;
         }
         InvalidateCommands();
      }

      private void InvalidateCommands() {
         MinusCommand?.ChangeCanExecute();
         PlusCommand?.ChangeCanExecute();
      }

      private void OnMinutesChanged() {
         if (!isChangingValues) {
            CollectValues();
            ValueChanged();
         }
      }

      private void OnSecondsChanged() {
         if (!isChangingValues) {
            CollectValues();
            ValueChanged();
         }
      }

      private void CollectValues() {
         int currentValue = Minutes * 60 + Seconds;
         if (currentValue != model.Time.TotalSeconds) {
            model.Time = TimeSpan.FromSeconds(currentValue > 3599 ? 3599 : currentValue);
            ValueChanged();
         }
      }

      private void ValueChanged() {
         if (!isChangingValues) {
            try {
               isChangingValues = true;
               Minutes = model.Time.Minutes;
               Seconds = model.Time.Seconds;
            } finally {
               isChangingValues = false;
            }
         }
         InvalidateCommands();
      }
   }
}
