﻿using System;
using System.Collections.Generic;
using System.Text;

namespace ClimbersHangout.Core.Models.Routes {
   public class FrenchRouteGrade : Grade {
      public static readonly FrenchRouteGrade f3a = new FrenchRouteGrade(20, "3a");
      public static readonly FrenchRouteGrade f3aPlus = new FrenchRouteGrade(25, "3a+");
      public static readonly FrenchRouteGrade f3b = new FrenchRouteGrade(30, "3b");
      public static readonly FrenchRouteGrade f3bPlus = new FrenchRouteGrade(35, "3b+");
      public static readonly FrenchRouteGrade f3c = new FrenchRouteGrade(40, "3c");
      public static readonly FrenchRouteGrade f3cPlus = new FrenchRouteGrade(45, "3c+");
      public static readonly FrenchRouteGrade f4a = new FrenchRouteGrade(50, "4a");
      public static readonly FrenchRouteGrade f4aPlus = new FrenchRouteGrade(55, "4a+");
      public static readonly FrenchRouteGrade f4b = new FrenchRouteGrade(60, "4b");
      public static readonly FrenchRouteGrade f4bPlus = new FrenchRouteGrade(65, "4b+");
      public static readonly FrenchRouteGrade f4c = new FrenchRouteGrade(70, "4c");
      public static readonly FrenchRouteGrade f4cPlus = new FrenchRouteGrade(75, "4c+");
      public static readonly FrenchRouteGrade f5a = new FrenchRouteGrade(80, "5a");
      public static readonly FrenchRouteGrade f5aPlus = new FrenchRouteGrade(85, "5a+");
      public static readonly FrenchRouteGrade f5b = new FrenchRouteGrade(90, "5b");
      public static readonly FrenchRouteGrade f5bPlus = new FrenchRouteGrade(95, "5b+");
      public static readonly FrenchRouteGrade f5c = new FrenchRouteGrade(100, "5c");
      public static readonly FrenchRouteGrade f5cPlus = new FrenchRouteGrade(105, "5c+");
      public static readonly FrenchRouteGrade f6a = new FrenchRouteGrade(110, "6a");
      public static readonly FrenchRouteGrade f6aPlus = new FrenchRouteGrade(115, "6a+");
      public static readonly FrenchRouteGrade f6b = new FrenchRouteGrade(120, "6b");
      public static readonly FrenchRouteGrade f6bPlus = new FrenchRouteGrade(125, "6b+");
      public static readonly FrenchRouteGrade f6c = new FrenchRouteGrade(130, "6c");
      public static readonly FrenchRouteGrade f6cPlus = new FrenchRouteGrade(135, "6c+");
      public static readonly FrenchRouteGrade f7a = new FrenchRouteGrade(140, "7a");
      public static readonly FrenchRouteGrade f7aPlus = new FrenchRouteGrade(145, "7a+");
      public static readonly FrenchRouteGrade f7b = new FrenchRouteGrade(150, "7b");
      public static readonly FrenchRouteGrade f7bPlus = new FrenchRouteGrade(155, "7b+");
      public static readonly FrenchRouteGrade f7c = new FrenchRouteGrade(160, "7c");
      public static readonly FrenchRouteGrade f7cPlus = new FrenchRouteGrade(165, "7c+");
      public static readonly FrenchRouteGrade f8a = new FrenchRouteGrade(170, "8a");
      public static readonly FrenchRouteGrade f8aPlus = new FrenchRouteGrade(175, "8a+");
      public static readonly FrenchRouteGrade f8b = new FrenchRouteGrade(180, "8b");
      public static readonly FrenchRouteGrade f8bPlus = new FrenchRouteGrade(185, "8b+");
      public static readonly FrenchRouteGrade f8c = new FrenchRouteGrade(190, "8c");
      public static readonly FrenchRouteGrade f8cPlus = new FrenchRouteGrade(195, "8c+");
      public static readonly FrenchRouteGrade f9a = new FrenchRouteGrade(200, "9a");
      public static readonly FrenchRouteGrade f9aPlus = new FrenchRouteGrade(205, "9a+");
      public static readonly FrenchRouteGrade f9b = new FrenchRouteGrade(210, "9b");
      public static readonly FrenchRouteGrade f9bPlus = new FrenchRouteGrade(215, "9b+");
      public static readonly FrenchRouteGrade f9c = new FrenchRouteGrade(220, "9c");
      public static readonly FrenchRouteGrade f9cPlus = new FrenchRouteGrade(225, "9c+");

      public static readonly Grade[] Grades = new Grade[] {
         f3a, f3aPlus, f3b, f3bPlus, f3c, f3cPlus,
         f4a, f4aPlus, f4b, f4bPlus, f4c, f4cPlus,
         f5a, f5aPlus, f5b, f5bPlus, f5c, f5cPlus,
         f6a, f6aPlus, f6b, f6bPlus, f6c, f6cPlus,
         f7a, f7aPlus, f7b, f7bPlus, f7c, f7cPlus,
         f8a, f8aPlus, f8b, f8bPlus, f8c, f8cPlus,
         f9a, f9aPlus, f9b, f9bPlus, f9c, f9cPlus
      };

      private FrenchRouteGrade(int absoluteValue, string name) : base(absoluteValue, RouteType.SportRoute | RouteType.TradRoute, name) {

      }
   }
}
