using System;
using System.Collections.Generic;
using System.Text;
using ClimbersHangout.Core.Models;

namespace ClimbersHangout.Core.Helpers {
   public class PeriodHelper {
      public static Period GetPeriod(PeriodType type, long duration) {
         return GetPeriod(type, duration, false);
      }

      public static Period GetPeriod(PeriodType type, long duration, bool skipOnLast) {
         return new PeriodImpl() {
            Type = type,
            Duration = duration,
            SkipOnLast = skipOnLast
         };
      }

      public static PeriodGroup GetPeriodGroup(Period[] periods) {
         return GetPeriodGroup(string.Empty, string.Empty, periods, 0, true, false);
      }

      public static PeriodGroup GetPeriodGroup(Period[] periods, int repeatCount) {
         if (repeatCount <= 0) {
            throw new ArgumentException("Repetition count should be at least 1.");
         }
         return GetPeriodGroup(string.Empty, string.Empty, periods, repeatCount, false, false);
      }

      public static PeriodGroup GetPeriodGroup(string name, string description, Period[] periods, int repeatCount, bool infiniteLoop, bool skipOnLast) {
         if (periods == null) {
            throw new ArgumentNullException("No periods supplied to create a group.");
         }
         if (infiniteLoop == false && repeatCount < 1) {
            throw new ArgumentException("Either the group should repeat infinitely or the count of time to repeat should be supplied.");
         }

         var group = new PeriodGroupImpl() {
            Name = name,
            Description = description,
            IsInfiniteRepetition = infiniteLoop,
            RepeatCount = repeatCount,
            SkipOnLast = skipOnLast,
         };

         foreach (var period in periods) {
            group.Add(period);
         }

         return group;
      }

      public static PeriodGroup CreateSimpleTimerTraining(
         long preparationTime, int repeatCount, long workTime, long restTime, long pauseTime) {

         return GetPeriodGroup(new Period[] {
            GetPeriod(PeriodType.Prep, preparationTime),
            GetPeriodGroup(new Period[] {
               GetPeriodGroup(new Period[] {
                  GetPeriod(PeriodType.Work, workTime),
                  GetPeriod(PeriodType.Rest, restTime, true)
               }, repeatCount),
               GetPeriod(PeriodType.Pause, pauseTime)
            })
         }, 1);

      }
   }
}
