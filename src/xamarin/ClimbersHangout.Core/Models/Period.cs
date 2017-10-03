namespace ClimbersHangout.Core.Models {
   public class Period : IPeriod {

      public PeriodType Type { get; set; }
      public long Duration { get; set; }
      public bool SkipOnLast { get; set; }

      public PeriodType GetType(long time) {
         PeriodType type = PeriodType.Undefined;
         if (Duration < time) {
            type = Type;
         }
         return type;
      }
   }
}
