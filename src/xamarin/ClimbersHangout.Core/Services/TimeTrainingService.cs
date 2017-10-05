using ClimbersHangout.Core.Models;

namespace ClimbersHangout.Core.Services {
   public class TimeTrainingService {
      public static IPeriodGroup[] GetAllTrainings() {
         #region Training 1
         var training1 = new PeriodGroup() {
            Name = "Training 1",
            Description = "Sample training 1",
            IsInfiniteRepetition = false
         };
         training1.Add(new Period() {
            Duration = 20000,
            Type = PeriodType.Prep
         });
         var training11 = new PeriodGroup() {
            Name = "Training 1 body",
            Description = "Sample training 1 body",
            IsInfiniteRepetition = true
         };
         training11.Add(new Period() {
            Duration = 6000,
            Type = PeriodType.Work
         });
         training11.Add(new Period() {
            Duration = 4000,
            Type = PeriodType.Rest
         });
         training1.Add(training11);
         #endregion

         var training2 = new PeriodGroup() {
            Name = "Training 2",
            Description = "Sample training 2",
            IsInfiniteRepetition = false
         };
         training2.Add(new Period() {
            Duration = 18000,
            Type = PeriodType.Prep
         });
         var training21 = new PeriodGroup() {
            Name = "Training 2 body",
            Description = "Sample training 2 body",
            IsInfiniteRepetition = false,
            RepeatCount = 1
         };

         var training211 = new PeriodGroup() {
            Name = "Exercise 1",
            Description = "Training 2 exercise 1",
            IsInfiniteRepetition = false,
            RepeatCount = 5
         };
         var training2111 = new PeriodGroup() {
            Name = "Repetition 1",
            Description = "Training 2 repetition 1",
            IsInfiniteRepetition = false,
            RepeatCount = 6
         };
         training2111.Add(new Period() {
            Duration = 6000,
            Type = PeriodType.Work
         });
         training2111.Add(new Period() {
            Duration = 4000,
            Type = PeriodType.Rest,
            SkipOnLast = true
         });
         training211.Add(training2111);
         training211.Add(new Period() {
            Duration = 120000,
            Type = PeriodType.Pause
         });
         training21.Add(training211);

         var training212 = new PeriodGroup() {
            Name = "Exercise 2",
            Description = "Training 2 exercise 2",
            IsInfiniteRepetition = false,
            RepeatCount = 3
         };
         var training2121 = new PeriodGroup() {
            Name = "Repetition 1",
            Description = "Training 2 repetition 1",
            IsInfiniteRepetition = false,
            RepeatCount = 6
         };
         training2121.Add(new Period() {
            Duration = 7000,
            Type = PeriodType.Work
         });
         training2121.Add(new Period() {
            Duration = 3000,
            Type = PeriodType.Rest,
            SkipOnLast = true
         });
         training212.Add(training2121);
         training212.Add(new Period() {
            Duration = 180000,
            Type = PeriodType.Pause
         });
         training21.Add(training212);
         training2.Add(training21);

         return new IPeriodGroup[] {
            training1, training2
         };
      }
   }
}
