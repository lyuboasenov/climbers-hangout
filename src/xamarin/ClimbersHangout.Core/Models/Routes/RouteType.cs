using System;
using System.Collections.Generic;
using System.Text;

namespace ClimbersHangout.Core.Models.Routes {
   [Flags]
   public enum RouteType {
      Boulder = 1,
      SportRoute = 2,
      TradRoute = 4
   }
}
