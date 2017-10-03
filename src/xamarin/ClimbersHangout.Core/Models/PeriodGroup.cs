﻿using System;
using System.Linq;

namespace ClimbersHangout.Core.Models {

   using System.Collections.Generic;

   public class PeriodGroup : IPeriodGroup {
      public PeriodGroup() {
         periods = new List<IPeriod>();
         timeSpans = new List<Tuple<long, long>>();
      }

      private readonly IList<IPeriod> periods;
      private IList<Tuple<long, long>> timeSpans;

      public string Name { get; set; }
      public string Description { get; set; }
      public int RepeatCount { get; set; }
      public bool IsInfiniteRepetition { get; set; }
      public long Duration { get; set; }
      public bool SkipOnLast { get; set; }

      public void Add(IPeriod period) {
         periods.Add(period);
         long start = Duration;
         Duration += period.Duration;
         timeSpans.Add(new Tuple<long, long>(start, Duration));
      }

      public void Remove(IPeriod period) {
         if (periods.Contains(period)) {
            periods.Remove(period);
            Duration -= period.Duration;
            RebuildTimeSpans();
         }
      }

      public void Remove(int index) {
         var period = periods[index];
         if (periods.Count > index) {
            periods.RemoveAt(index);
            Duration -= period.Duration;
            RebuildTimeSpans();
         }
      }

      private void RebuildTimeSpans() {
         long start = 0;
         timeSpans = new List<Tuple<long, long>>();
         foreach (var period in periods) {
            timeSpans.Add(new Tuple<long, long>(start, start + period.Duration));
            start += Duration;
         }
      }

      public PeriodType GetType(long time) {
         var type = PeriodType.Undefined;
         var timeSpan = timeSpans.FirstOrDefault(t => t.Item1 <= time && time <= t.Item2);
         if (null != timeSpans) {
            int index = timeSpans.IndexOf(timeSpan);
            type = periods[index].GetType(time);
         }
         return type;
      }

      public IReadOnlyList<IPeriod> Periods {
         get {
            return (IReadOnlyList<IPeriod>)periods;
         }
      }
   }
}
