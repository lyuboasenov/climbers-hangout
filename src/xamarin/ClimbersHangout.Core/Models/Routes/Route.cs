using System;
using Xamarin.Forms;
using System.Collections.Generic;

namespace ClimbersHangout.Core.Models.Routes {
   public class Route {
      
      public Guid Id { get; set; }
      public string Name { get; set; }
      public string Description { get; set; }
      public RouteType Type { get; set; }
      public Grade Grade { get; set; }
      public Position Position { get; set; }

      public Route() {
         Id = Guid.NewGuid();
      }
   }
}
