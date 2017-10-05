using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;

namespace ClimbersHangout.Core.Models {
   public class Training : IEnumerator<IPeriod>, IEnumerable<IPeriod> {
      private readonly IPeriodGroup rootPeriodGroup;
      private Stack<PeriodFrame> periodStack;
      private IPeriod currentPeriod;
      private IPeriod nextPeriod;

      public Training(IPeriodGroup periodGroup) {
         rootPeriodGroup = periodGroup;
      }

      public IPeriod GetCurrentPeriod() {
         if (currentPeriod == null) {
            Reset();
         }
         return currentPeriod;
      }

      public IPeriod GetNextPeriod() {
         if (nextPeriod == null) {
            nextPeriod = MoveToNextAvailablePeriod();
         }
         return nextPeriod;
      }

      public void Reset() {
         periodStack = new Stack<PeriodFrame>();
         periodStack.Push(new PeriodFrame(rootPeriodGroup));
         currentPeriod = MoveToNextAvailablePeriod();
      }

      public IPeriod Current { get { return GetCurrentPeriod(); } }

      object IEnumerator.Current {
         get { return Current; }
      }

      public bool MoveNext() {

         if (currentPeriod == null) {
            Reset();
            GetNextPeriod();
         } else {
            currentPeriod = nextPeriod;
            nextPeriod = MoveToNextAvailablePeriod();
         }

         return currentPeriod != null;
      }

      public void Dispose() {

      }

      public IEnumerator<IPeriod> GetEnumerator() {
         return this;
      }

      IEnumerator IEnumerable.GetEnumerator() {
         return GetEnumerator();
      }

      private IPeriod MoveToNextAvailablePeriod() {
         IPeriod period = null;
         var topFrame = periodStack.Peek();
         IPeriodGroup group = null;
         int index = 0, repetitionCount = 0;

         if (!(topFrame.Period is IPeriodGroup)) {
            do {
               //Remove top period as 
               periodStack.Pop();

               //It's assumed that prev period is a group
               topFrame = periodStack.Peek();

               //Get groups props
               group = topFrame.Period as IPeriodGroup;
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

         while (topFrame.Period is IPeriodGroup currentPeriodGroup) {

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

      private bool CheckEndOfGroupReached(IPeriodGroup group, int index, int repetition) {
         return index + 1 == group.Periods.Count
                && !group.IsInfiniteRepetition && group.RepeatCount - 1 == repetition;
      }

      private bool CheckEndOfGroupSkipOnLast(IPeriodGroup group, int index, int repetition) {
         return index + 1 == group.Periods.Count - 1 && group.Periods[index].SkipOnLast
                && !group.IsInfiniteRepetition && group.RepeatCount - 1 == repetition;
      }

      private class PeriodFrame {
         public PeriodFrame() : this(null, 0, 0) {

         }

         public PeriodFrame(IPeriod period) : this(period, 0, 0) {

         }

         public PeriodFrame(IPeriod period, int index) : this(period, index, 0) {

         }

         public PeriodFrame(IPeriod period, int index, int repetitions) {
            Period = period;
            Index = index;
            RepetitionCount = repetitions;
         }

         public IPeriod Period { get; set; }
         public int Index { get; set; }
         public int RepetitionCount { get; set; }
      }
   }
}
