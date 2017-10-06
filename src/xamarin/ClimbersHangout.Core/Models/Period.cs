using System;
using System.Collections.Generic;
using System.Text;

namespace ClimbersHangout.Core.Models {
   public interface Period {
      long Duration { get; }
      bool SkipOnLast { get; }
      PeriodType Type { get; }
   }
}
