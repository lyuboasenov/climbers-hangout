using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Text;

namespace ClimbersHangout.Core.Models.Routes {
   public class Grade {
      private int absoluteValue;
      private RouteType supportedRouteType;
      public string Name { get; set; }

      public Grade(int absoluteValue, RouteType supportedRouteType, string name) {
         this.absoluteValue = absoluteValue;
         this.supportedRouteType = supportedRouteType;
         Name = name;
      }

      public static IEnumerable<Grade> GetGradeList(RouteType routeType) {
         IEnumerable<Grade> result = null;
         switch (routeType) {
            case RouteType.Boulder:
            case RouteType.IndoorBoulder:
               result = FontBoulderGrade.Grades;
               break;
            case RouteType.SportRoute:
            case RouteType.TradRoute:
               result = FrenchRouteGrade.Grades;
               break;
         }
         return result;
      }
   }
}
