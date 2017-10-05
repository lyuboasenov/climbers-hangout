using System;
using System.Collections.Generic;
using System.Text;

namespace ClimbersHangout.Core.Models {
   public interface IPeriod {
      long Duration { get; }
      bool SkipOnLast { get; }
      PeriodType Type { get; }
   }
}
