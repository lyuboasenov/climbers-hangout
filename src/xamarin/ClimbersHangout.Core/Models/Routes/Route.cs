using System;
using Xamarin.Forms;
using System.Collections.Generic;

namespace ClimbersHangout.Core.Models.Routes {
   public class Route {
      public readonly Color DEFAULT_HOLD_COLOR = Color.OrangeRed;
      public const double NEAREST_HOLD_DISTANCE = 20;
      public const double MAX_SIZE = 2048d;

      private readonly List<Hold> holds = new List<Hold>();
      private readonly List<Path> paths = new List<Path>();

      public GpsPosition Position { get; set; }
      public List<Hold> Holds { get { return holds; } }
      public List<Path> Paths { get { return paths; } }
      public string ImageLocation { get; set; }

      public void AddHold(Hold hold) {
         if (null == hold) {
            throw new ArgumentNullException(nameof(hold));
         }

         if (!Holds.Contains(hold)) {
            Holds.Add(hold);
         }
      }

      public void AddPath(Path path) {
         if (null == path) {
            throw new ArgumentNullException(nameof(path));
         }

         if (!Paths.Contains(path)) {
            Paths.Add(path);
         }
      }

      /// <summary>
      /// Gets a point within NEAREST_HOLD_DISTANCE
      /// </summary>
      /// <param name="point"></param>
      /// <returns></returns>
      public Hold GetNearestHoldOrNew(Point point) {
         Hold hold = null;
         foreach (var currentHold in Holds) {
            if (currentHold.Center.Distance(point) < NEAREST_HOLD_DISTANCE) {
               hold = currentHold;
               break;
            }
         }

         return hold ?? new Hold() { Color = DEFAULT_HOLD_COLOR };
      }
   }
}
