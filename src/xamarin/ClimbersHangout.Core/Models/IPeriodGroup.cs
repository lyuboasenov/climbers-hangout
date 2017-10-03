using System.Collections.Generic;

namespace ClimbersHangout.Core.Models {
   public interface IPeriodGroup : IPeriod {
      IReadOnlyList<IPeriod> Periods { get; }
   }
}
