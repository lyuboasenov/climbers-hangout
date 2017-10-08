using System;
using System.Collections.Generic;
using System.Text;
using PropertyChanged;

namespace ClimbersHangout.UI.Common.Models {
   [AddINotifyPropertyChangedInterface]
   public class TimeSelectModel {
      public TimeSpan Time { get; set; }
   }
}
