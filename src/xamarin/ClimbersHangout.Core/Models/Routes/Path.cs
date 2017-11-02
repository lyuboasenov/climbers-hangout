using System;
using System.Collections.Generic;
using Xamarin.Forms;

namespace ClimbersHangout.Core.Models.Routes {
   public class Path {

      private readonly List<Point> points = new List<Point>();
      public int PointCount { get { return points.Count; } }
      public List<Point> Points { get { return points; } }
      public Color Color { get; set; }

      public void AddPoint(Point point) {
         if (null == point) {
            throw new ArgumentNullException(nameof(point));
         }

         Points.Add(point);
      }
   }
}
