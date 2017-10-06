using System;
using System.Collections.Generic;
using System.Text;
using ClimbersHangout.Core.Models;

namespace ClimbersHangout.Core {
   public class TimerEventArgs : EventArgs {
      public Period Period { get; }
      public long Passed { get; }
      public long Now { get; }

      public TimerEventArgs(Period period, long passed, long now) {
         this.Period = period;
         this.Passed = passed;
         this.Now = now;
      }
   }
}
