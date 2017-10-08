using System;
using System.Collections.Generic;
using System.Text;
using PropertyChanged;

namespace ClimbersHangout.UI.Common.Models {
   [AddINotifyPropertyChangedInterface]
   public class NumberUpDownModel {
      public NumberUpDownModel() {
         MinValue = int.MinValue;
         MaxValue = int.MaxValue;
      }

      public int Value { get; set; }
      public int MinValue { get; set; }
      public int MaxValue { get; set; }
   }
}
