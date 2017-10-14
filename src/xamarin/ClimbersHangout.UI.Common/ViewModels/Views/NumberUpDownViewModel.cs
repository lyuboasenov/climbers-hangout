using System;
using ClimbersHangout.UI.Common.Models;
using FreshMvvm;
using PropertyChanged;
using Xamarin.Forms;

namespace ClimbersHangout.UI.Common.ViewModels.Views {
   [AddINotifyPropertyChangedInterface]
   public class NumberUpDownViewModel : FreshBasePageModel {
      public Command minusCommand;
      public Command plusCommand;
      private NumberUpDownModel model;

      public NumberUpDownViewModel() : this(new NumberUpDownModel()) {

      }

      public NumberUpDownViewModel(NumberUpDownModel model) {
         this.model = model;
         MinValue = model.MinValue;
         MaxValue = model.MaxValue;
         Value = model.Value;
      }

      public int Value { get; set; }
      public int MinValue { get; set; }
      public int MaxValue { get; set; }

      public Command MinusCommand {
         get { return minusCommand ?? (minusCommand = new Command(MinusExecute, MinusCanExecute)); }
      }

      public Command PlusCommand {
         get { return plusCommand ?? (plusCommand = new Command(PlusExecute, PlusCanExecute)); }
      }

      private bool MinusCanExecute() {
         return Value > MinValue;
      }

      private bool PlusCanExecute() {
         return Value < MaxValue;
      }

      private void MinusExecute() {
         Value = Value - 1 < MinValue ? MinValue : Value - 1;
         InvalidateCommands();
      }

      private void PlusExecute() {
         Value += 1;
         InvalidateCommands();
      }

      private void OnValueChanged() {
         model.Value = Value;
      }

      private void InvalidateCommands() {
         MinusCommand?.ChangeCanExecute();
         PlusCommand?.ChangeCanExecute();
      }
   }
}
