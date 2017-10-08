namespace ClimbersHangout.Core.Models {
   public class PeriodImpl : Period {
      public PeriodType Type { get; set; }
      public long Duration { get; set; }
      public bool SkipOnLast { get; set; }
   }
}
