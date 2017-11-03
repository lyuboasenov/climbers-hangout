using System;
using ClimbersHangout.Core.Models.Routes;

namespace ClimbersHangout.Core.Helpers {
   public class GpsHelper {
      /// <summary>
      /// calculate distance between two points in kilometers
      /// taken from http://www.geodatasource.com/developers/c-sharp
      /// </summary>
      /// <returns></returns>
      public static double Distance(Position pos1, Position pos2, DistanceUnit unit = DistanceUnit.Kilometers) {
         double theta = pos1.Longitude - pos2.Longitude;
         double dist = Math.Sin(DegToRad(pos1.Latitude)) * Math.Sin(DegToRad(pos2.Latitude)) + Math.Cos(DegToRad(pos1.Latitude)) * Math.Cos(DegToRad(pos2.Latitude)) * Math.Cos(DegToRad(theta));
         dist = Math.Acos(dist);
         dist = RadToDeg(dist);
         dist = dist * 60 * 1.1515;
         if (unit == DistanceUnit.Kilometers) {
            dist = dist * 1.609344;
         } else if (unit == DistanceUnit.NauticalMiles) {
            dist = dist * 0.8684;
         }
         return (dist);
      }

      /// <summary>
      /// This function converts decimal degrees to radians
      /// taken from http://www.geodatasource.com/developers/c-sharp
      /// </summary>
      /// <param name="deg"></param>
      /// <returns></returns>
      public static double DegToRad(double deg) {
         return (deg * Math.PI / 180.0);
      }

      /// <summary>
      /// This function converts radians to decimal degrees
      /// taken from http://www.geodatasource.com/developers/c-sharp
      /// </summary>
      /// <param name="rad"></param>
      /// <returns></returns>
      public static double RadToDeg(double rad) {
         return (rad / Math.PI * 180.0);
      }
   }

   public enum DistanceUnit {
      Kilometers,
      NauticalMiles,
      StatuteMiles
   }
}
