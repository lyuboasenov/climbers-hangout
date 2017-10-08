using System;
using ClimbersHangout.Core;
using ClimbersHangout.Core.Models;
using ClimbersHangout.Core.Services;

namespace ClimbersHangout.UI.Console {
   class Program {
      static void Main(string[] args) {
         var trainings = TimeTrainingService.GetAllTrainings();

         //         TrainingEnumerator trainingEnumerator = new TrainingEnumerator(trainings[1]);
         //
         //         foreach (var period in trainingEnumerator) {
         //            System.Console.WriteLine("{0} {1}", period.Type, period.Duration);
         //            System.Console.WriteLine("");
         //
         //            if (System.Console.ReadKey().KeyChar == 'q') break;
         //         }




         TrainingRunner timer = new TrainingRunner(trainings[0]);
         timer.TimerTick += Timer_timerTick;
         timer.Start();

         while (System.Console.ReadKey().KeyChar != 'q') { }
      }

      private static long lastSeconds;
      private static void Timer_timerTick(object sender, TimerEventArgs e) {
         long now = e.Now / 1000;
         if (now != lastSeconds) {
            lastSeconds = now;
            int left = (int)((e.Period.Duration - e.Now + e.Passed) / 1000);
            System.Console.WriteLine(String.Format("{0} {1}", e.Period.Type, left));
         }
      }
   }
}
