using System;
using System.Collections.Generic;
using System.Net;
using System.Text;
using System.Threading;
using ClimbersHangout.Core.Models;

namespace ClimbersHangout.Core {
   public class TrainingRunner {

      private static int TIMER_INTERVAL = 50;

      private object syncObject = new object();

      private System.Threading.Timer timer;
      private readonly TrainingEnumerator trainingEnumerator;
      private readonly int notificationInterval;
      private long startTime;
      private long pauseStartTime;
      private long offset;
      private long passed;

      public event EventHandler<TimerEventArgs> TimerTick;
      public event EventHandler Finished;

      public TrainingRunner(PeriodGroup periods) : this(periods, TIMER_INTERVAL) { }

      public TrainingRunner(PeriodGroup periods, int notificationInterval) {
         if (null == periods) {
            throw new ArgumentNullException(nameof(periods));
         }
         Periods = periods;
         this.notificationInterval = notificationInterval;
         trainingEnumerator = new TrainingEnumerator(Periods);
      }

      private PeriodGroup Periods { get; set; }

      public bool IsRunning { get; private set; }
      public bool IsPaused { get; private set; }

      public void Start() {
         if (CanStart()) {
            lock (syncObject) {
               //-1 is specified to prevent timer from starting
               timer = new System.Threading.Timer(Tick, null, -1, TIMER_INTERVAL);
               trainingEnumerator.Reset();

               IsRunning = true;
               IsPaused = false;
               offset = 0;
               passed = 0;
               pauseStartTime = 0;
               startTime = GetNowAsMilliseconds();

               //starting the timer
               StartTimer();
            }
         }
      }

      public bool CanStart() {
         return !IsRunning;
      }

      public void Stop() {
         if (CanStop()) {
            lock (syncObject) {
               //stop timer and dispose
               StopTimer();
               timer.Dispose();
               timer = null;

               IsRunning = false;
               IsPaused = false;
               startTime = 0;
               offset = 0;
               passed = 0;
               pauseStartTime = 0;
            }
         }
      }

      public bool CanStop() {
         return IsRunning;
      }

      public void Pause() {
         if (CanPause()) {
            lock (syncObject) {
               IsPaused = true;
               pauseStartTime = GetNowAsMilliseconds();
               //pause the timer too
               StopTimer();
            }
         }
      }

      public bool CanPause() {
         return IsRunning && !IsPaused;
      }

      public void Resume() {
         if (CanResume()) {
            lock (syncObject) {
               IsPaused = false;
               offset += GetNowAsMilliseconds() - pauseStartTime;
               pauseStartTime = 0;
               //restart the timer
               StartTimer();
            }
         }
      }

      public bool CanResume() {
         return IsRunning && IsPaused;
      }

      private void StartTimer() {
         timer.Change(0, notificationInterval);
      }

      private void StopTimer() {
         timer?.Change(Timeout.Infinite, notificationInterval);
      }

      private void Tick(object state) {
         lock (syncObject) {
            //Stop timer
            StopTimer();

            if (!IsRunning || IsPaused) return;

            long now = GetNowAsMilliseconds();
            long normalizedNow = now - startTime - offset;
            Period currentPeriod = GetCurrentPeriod();

            if (null == currentPeriod) {
               OnFinished();
            } else {
               if (passed + currentPeriod.Duration < normalizedNow) {
                  passed += currentPeriod.Duration;
                  currentPeriod = MoveNext();
               }

               if (currentPeriod == null) {
                  OnFinished();
               } else {
                  OnTimerTick(new TimerEventArgs(currentPeriod, passed, normalizedNow));
                  if (IsRunning && !IsPaused) {
                     //restart timer
                     StartTimer();
                  }
               }
            }
         }
      }

      private Period MoveNext() {
         trainingEnumerator.MoveNext();
         return trainingEnumerator.Current;
      }

      private Period GetCurrentPeriod() {
         return trainingEnumerator.Current;
      }

      protected virtual void OnTimerTick(TimerEventArgs e) {
         TimerTick?.Invoke(this, e);
      }

      protected virtual void OnFinished() {
         Finished?.Invoke(this, EventArgs.Empty);
      }

      private long GetNowAsMilliseconds() {
         return DateTime.Now.Ticks / 10000;
      }
   }
}
