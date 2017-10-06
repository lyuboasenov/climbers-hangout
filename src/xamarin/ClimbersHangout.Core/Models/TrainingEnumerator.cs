using System;
using System.Collections;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace ClimbersHangout.Core.Models {
   public class TrainingEnumerator : IEnumerator<Period>, IEnumerable<Period> {
      private readonly PeriodGroup rootPeriodGroup;
      private Stack<PeriodFrame> periodStack;
      private Period currentPeriod;
      private Task<Period> nextPeriod;

      public TrainingEnumerator(PeriodGroup periodGroup) {
         if (null == periodGroup) {
            throw new ArgumentNullException(nameof(periodGroup));
         }
         rootPeriodGroup = periodGroup;
      }

      public Period GetCurrentPeriod() {
         if (currentPeriod == null) {
            Reset();
         }
         return currentPeriod;
      }

      public Period GetNextPeriod() {
         if (nextPeriod == null) {
            nextPeriod = MoveToNextAvailablePeriodAsync();
         }
         return nextPeriod.Result;
      }
      #region IEnumerable

      public void Reset() {
         periodStack = new Stack<PeriodFrame>();
         periodStack.Push(new PeriodFrame(rootPeriodGroup));
         currentPeriod = MoveToNextAvailablePeriodAsync().Result;
         nextPeriod = MoveToNextAvailablePeriodAsync();
      }

      public Period Current { get { return GetCurrentPeriod(); } }

      object IEnumerator.Current {
         get { return Current; }
      }

      public bool MoveNext() {

         if (currentPeriod == null) {
            Reset();
         } else {
            currentPeriod = nextPeriod.Result;
         }

         nextPeriod = MoveToNextAvailablePeriodAsync();
         return currentPeriod != null;
      }

      public void Dispose() {

      }

      #endregion IEnumerable

      #region IEnumerator

      public IEnumerator<Period> GetEnumerator() {
         return this;
      }

      IEnumerator IEnumerable.GetEnumerator() {
         return GetEnumerator();
      }

      #endregion IEnumerator

      private Task<Period> MoveToNextAvailablePeriodAsync() {
         return Task.Factory.StartNew<Period>(MoveToNextAvailablePeriod);
      }

      private Period MoveToNextAvailablePeriod() {
         Period period = null;
         var topFrame = periodStack.Peek();
         PeriodGroup group = null;
         int index = 0, repetitionCount = 0;

         if (!(topFrame.Period is PeriodGroup)) {
            do {
               //Remove top period as 
               periodStack.Pop();

               //It's assumed that prev period is a group
               topFrame = periodStack.Peek();

               //Get groups props
               group = topFrame.Period as PeriodGroup;
               index = topFrame.Index;
               repetitionCount = topFrame.RepetitionCount;
            } while (CheckEndOfGroupReached(group, index, repetitionCount)
                     || CheckEndOfGroupSkipOnLast(group, index, repetitionCount));
         }

         if (group != null) {
            repetitionCount = index + 1 == group.Periods.Count ? repetitionCount + 1 : repetitionCount;
            index = (index + 1) % group.Periods.Count;
         }

         if (group != null && repetitionCount > group.RepeatCount && !group.IsInfiniteRepetition) {
            return null;
         }

         while (topFrame.Period is PeriodGroup currentPeriodGroup) {

            period = currentPeriodGroup.Periods[index];
            topFrame.Index = index;
            topFrame.RepetitionCount = repetitionCount;

            periodStack.Push(new PeriodFrame(period));

            index = 0;
            repetitionCount = 0;

            topFrame = periodStack.Peek();
         }

         return period;
      }

      private bool CheckEndOfGroupReached(PeriodGroup group, int index, int repetition) {
         return index + 1 == group.Periods.Count
                && !group.IsInfiniteRepetition && group.RepeatCount - 1 == repetition;
      }

      private bool CheckEndOfGroupSkipOnLast(PeriodGroup group, int index, int repetition) {
         return index + 1 == group.Periods.Count - 1 && group.Periods[index].SkipOnLast
                && !group.IsInfiniteRepetition && group.RepeatCount - 1 == repetition;
      }

      private class PeriodFrame {
         public PeriodFrame() : this(null, 0, 0) {

         }

         public PeriodFrame(Period period) : this(period, 0, 0) {

         }

         public PeriodFrame(Period period, int index) : this(period, index, 0) {

         }

         public PeriodFrame(Period period, int index, int repetitions) {
            Period = period;
            Index = index;
            RepetitionCount = repetitions;
         }

         public Period Period { get; set; }
         public int Index { get; set; }
         public int RepetitionCount { get; set; }
      }
   }
}
