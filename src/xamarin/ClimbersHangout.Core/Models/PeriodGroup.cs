using System.Collections.Generic;

namespace ClimbersHangout.Core.Models {
   public interface PeriodGroup : Period {
      IReadOnlyList<Period> Periods { get; }
      bool IsInfiniteRepetition { get; }
      int RepeatCount { get; }
   }
}
