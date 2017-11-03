using System;
using System.Collections.Generic;
using System.Text;

namespace ClimbersHangout.Core.Models.Routes {
   public class Position {
      public double Latitude { get; set; }
      public double Longitude { get; set; }

      public Position() { }

      public Position(double latitude, double longitude) {
         Latitude = latitude;
         Longitude = longitude;
      }
   }
}
