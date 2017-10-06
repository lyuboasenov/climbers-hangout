using System;
using System.Collections.Generic;
using System.Net;
using System.Text;
using System.Threading;
using ClimbersHangout.Core.Models;

namespace ClimbersHangout.Core {
   public class TrainingRunner {

      private int TIMER_INTERVAL = 50;

      private System.Threading.Timer timer;
      private TrainingEnumerator trainingEnumerator;
      private long startTime;
      private long pauseStartTime;
      private long offset;
      private long passed;

      public event EventHandler<TimerEventArgs> timerTick;
      public event EventHandler finished;

      public TrainingRunner(PeriodGroup periods) {
         if (null == periods) {
            throw new ArgumentNullException(nameof(periods));
         }
         Periods = periods;
         trainingEnumerator = new TrainingEnumerator(Periods);
      }

      private PeriodGroup Periods { get; set; }

      public bool IsRunning { get; private set; }
      public bool IsPaused { get; private set; }

      public void Start() {
         if (!IsRunning) {
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

      public void Stop() {
         if (IsRunning) {
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

      public void Pause() {
         if (IsRunning && !IsPaused) {
            IsPaused = true;
            pauseStartTime = GetNowAsMilliseconds();
            //pause the timer too
            StopTimer();
         }
      }

      public void Resume() {
         if (IsRunning && IsPaused) {
            offset += GetNowAsMilliseconds() - pauseStartTime;
            pauseStartTime = 0;
            //restart the timer
            StartTimer();
         }
      }

      private void StartTimer() {
         timer.Change(0, TIMER_INTERVAL);
      }

      private void StopTimer() {
         timer.Change(Timeout.Infinite, TIMER_INTERVAL);
      }

      private void Tick(object state) {
         //Stop timer
         StopTimer();

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
               //restart timer
               StartTimer();
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
         timerTick?.Invoke(this, e);
      }

      protected virtual void OnFinished() {
         finished?.Invoke(this, EventArgs.Empty);
      }

      private long GetNowAsMilliseconds() {
         return DateTime.Now.Ticks / 10000;
      }
   }
}
