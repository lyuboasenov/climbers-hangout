using System;
using System.Collections.Generic;
using System.Net;
using System.Text;
using System.Threading;
using ClimbersHangout.Core.Models;

namespace ClimbersHangout.Core {
   public class Timer {

      private int TIMER_INTERVAL = 50;

      private System.Threading.Timer timer;
      private Stack<TimerState> runningStack;
      private long startTime;
      private long pauseStartTime;
      private long offset;
      private long passed;

      public event EventHandler<TimerEventArgs> timerTick;
      public event EventHandler finished;

      public Timer(IPeriodGroup periods) {
         Periods = periods;
      }

      private IPeriodGroup Periods { get; set; }

      public bool IsRunning { get; private set; }
      public bool IsPaused { get; private set; }

      public void Start() {
         if (!IsRunning) {
            runningStack = new Stack<TimerState>();
            runningStack.Push(new TimerState() { Period = Periods, Index = 0 });
            //-1 is specified to prevent timer from starting
            timer = new System.Threading.Timer(Tick, null, -1, TIMER_INTERVAL);

            IsRunning = true;
            IsPaused = false;
            startTime = GetNowAsMills();
            offset = 0;
            passed = 0;
            pauseStartTime = 0;

            //starting the timer
            timer.Change(0, TIMER_INTERVAL);
         }
      }

      public void Stop() {
         if (IsRunning) {
            //stop timer and dispose
            timer.Change(Timeout.Infinite, TIMER_INTERVAL);
            timer.Dispose();

            runningStack = null;
            IsRunning = false;
            IsPaused = false;
            runningStack = null;
            startTime = 0;
            offset = 0;
            passed = 0;
            pauseStartTime = 0;
         }
      }

      public void Pause() {
         if (IsRunning && !IsPaused) {
            IsPaused = true;
            pauseStartTime = GetNowAsMills();
            //pause the timer too
            timer.Change(Timeout.Infinite, TIMER_INTERVAL);
         }
      }

      public void Resume() {
         if (IsRunning && IsPaused) {
            offset += GetNowAsMills() - pauseStartTime;
            pauseStartTime = 0;
            //restart the timer
            timer.Change(0, TIMER_INTERVAL);
         }
      }

      private void Tick(object state) {
         //Stop timer
         timer.Change(Timeout.Infinite, TIMER_INTERVAL);

         long now = GetNowAsMills();
         long normalizedNow = now - startTime - offset;
         IPeriod currentPeriod = GetCurrentPeriod();
         if (passed + currentPeriod.Duration < normalizedNow) {
            passed += currentPeriod.Duration;
            currentPeriod = MoveNext();
         }

         OnTimerTick(new TimerEventArgs(currentPeriod));

         //restart timer
         timer.Change(Timeout.Infinite, TIMER_INTERVAL);
      }

      private IPeriod MoveNext() {
         //Move up the stack
         TimerState topState;

         IPeriodGroup group;
         int nextIndex;

         do {
            topState = runningStack.Pop();
            topState = runningStack.Peek();

            group = topState.Period as IPeriodGroup;
            nextIndex = topState.Index + 1;

         } while (nextIndex == group.Periods.Count - 1
                  || (nextIndex == group.Periods.Count - 2
                      && group.Periods[nextIndex].SkipOnLast));
         //TODO: Handle group repetition


         topState = runningStack.Peek();
         topState.Index += 1;
         IPeriod period = (topState as IPeriodGroup).Periods[topState.Index];
         runningStack.Push(new TimerState() { Period = period, Index = 0 });

         return GetCurrentPeriod();
      }

      private IPeriod GetCurrentPeriod() {
         TimerState top = runningStack.Peek();
         IPeriod currentPeriod = top.Period;
         while (currentPeriod is IPeriodGroup) {
            currentPeriod = (currentPeriod as IPeriodGroup).Periods[0];
            runningStack.Push(new TimerState() { Period = currentPeriod, Index = 0 });
         }
         return currentPeriod;
      }

      protected virtual void OnTimerTick(TimerEventArgs e) {
         timerTick?.Invoke(this, e);
      }

      private long GetNowAsMills() {
         return DateTime.Now.Ticks * 10000;
      }

      protected virtual void OnFinished() {
         finished?.Invoke(this, EventArgs.Empty);
      }
   }

   internal class TimerState {
      public IPeriod Period { get; set; }
      public int Index { get; set; }
      public int RepetitionCount { get; set; }
   }
}
