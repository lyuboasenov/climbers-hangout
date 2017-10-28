using System;
using System.Collections.Generic;
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
   }
}
