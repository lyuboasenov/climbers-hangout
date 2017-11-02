using System;
using System.Collections.Generic;
using System.Text;

namespace ClimbersHangout.Core.Models.Routes {
   public class GpsPosition {
      public double Latitude { get; set; }
      public double Longitude { get; set; }

      public GpsPosition() { }

      public GpsPosition(double latitude, double longitude) {
         Latitude = latitude;
         Longitude = longitude;
      }
   }
}
