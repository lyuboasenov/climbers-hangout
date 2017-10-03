using System;
using ClimbersHangout.Core;
using ClimbersHangout.Core.Models;
using ClimbersHangout.Core.Services;

namespace ClimbersHangout.UI.Console {
   class Program {
      static void Main(string[] args) {
         var trainings = TimeTrainingService.GetAllTrainings();
         Timer timer = new Timer(trainings[1]);
         timer.timerTick += Timer_timerTick;
         timer.Start();

         while (System.Console.ReadKey().KeyChar != 'q') { }
      }

      private static IPeriod lastPeriod;
      private static void Timer_timerTick(object sender, TimerEventArgs e) {
         if (e.CurrentPeriod != lastPeriod) {
            lastPeriod = e.CurrentPeriod;
            System.Console.WriteLine(String.Format("{0} {1}", lastPeriod.Duration, lastPeriod.GetType(0)));
         }
      }
   }
}
