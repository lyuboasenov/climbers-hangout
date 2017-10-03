using System;

namespace ClimbersHangout.Core.Models {
   public class Exercise {
      public Guid Id { get; set; }
      public string Name { get; set; }
      public int SetCount { get; set; }
      public bool InfiniteSets { get; set; }
      public int RepetitionCount { get; set; }
      public int WorkDuration { get; set; }
      public int RestDuration { get; set; }
      public int PauseDuration { get; set; }
   }
}