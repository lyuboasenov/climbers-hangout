using System;
using System.Collections.Generic;
using System.Text;
using ClimbersHangout.Core.Models;

namespace ClimbersHangout.Core {
   public class TimerEventArgs : EventArgs {
      public IPeriod CurrentPeriod { get; }

      public TimerEventArgs(IPeriod currentPeriod) {
         this.CurrentPeriod = currentPeriod;
      }
   }
}
