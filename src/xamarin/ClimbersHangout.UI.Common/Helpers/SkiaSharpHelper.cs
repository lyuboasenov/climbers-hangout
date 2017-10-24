using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using ClimbersHangout.Core.Models.Routes;
using SkiaSharp;
using Xamarin.Forms;
using Path = ClimbersHangout.Core.Models.Routes.Path;

namespace ClimbersHangout.UI.Common.Helpers {
   static class SkiaSharpHelper {


      public static readonly SKPaint CompletedPaint = new SKPaint {
         Style = SKPaintStyle.Stroke,
         Color = SKColors.White,
         StrokeWidth = 10,
         StrokeCap = SKStrokeCap.Round,
         StrokeJoin = SKStrokeJoin.Round
      };

      public static readonly SKPaint InProgressPaint = new SKPaint {
         Style = SKPaintStyle.Stroke,
         Color = SKColors.Red,
         StrokeWidth = 10,
         StrokeCap = SKStrokeCap.Round,
         StrokeJoin = SKStrokeJoin.Round
      };


      public static SKBitmap LoadBitmap(string location, double maxSize) {
         SKBitmap bitmap;
         using (SKStream stream = new SKManagedStream(File.OpenRead(location))) {
            bitmap = SKBitmap.Decode(stream);
         }

         var bitmapAspectRatio = (double)bitmap.Width / bitmap.Height;
         double factor = bitmapAspectRatio > 1 ? maxSize / bitmap.Width : maxSize / bitmap.Height;

         SKImageInfo info = new SKImageInfo((int)(bitmap.Width * factor), (int)(bitmap.Height * factor));
         bitmap = bitmap.Resize(info, SKBitmapResizeMethod.Triangle);

         return bitmap;
      }

      public static SKBitmap Export(this Route route) {
         var bitmap = LoadBitmap(route.ImageLocation, Route.MAX_SIZE);
         SKCanvas canvas = new SKCanvas(bitmap);

         //DrawHolds
         foreach (var hold in route.Holds) {
            canvas.DrawCircle(
               (float)hold.Center.X,
               (float)hold.Center.Y,
               (float)hold.Radius,
               CompletedPaint);
         }

         //DrawPaths
         foreach (Path path in route.Paths) {
            canvas.DrawPath(path.ConvertToSKPath(), SkiaSharpHelper.CompletedPaint);
         }

         return bitmap;
      }

      /// <summary>
      /// Calculates distance between two skpoints.
      /// </summary>
      /// <param name="p1"></param>
      /// <param name="p2"></param>
      /// <returns></returns>
      public static double Distance(this SKPoint p1, SKPoint p2) {
         return Math.Sqrt(Math.Pow(p2.X - p1.X, 2) + Math.Pow(p2.Y - p1.Y, 2));
      }

      /// <summary>
      /// Converts Path to SKPath
      /// </summary>
      /// <param name="path">Path to be converted</param>
      /// <param name="originPoint">Origin point if transposition is to be made</param>
      /// <param name="scaleFactor">Scale factor if scaling is to be made</param>
      /// <returns></returns>
      public static SKPath ConvertToSKPath(this Path path, Point? originPoint = null, double scaleFactor = 1) {
         var normalizedPath = new SKPath();
         if (path.PointCount > 0) {
            Point firstPoint = path.Points[0];
            normalizedPath.MoveTo(firstPoint.ConvertToSKPoint(originPoint, scaleFactor));

            if (path.PointCount >= 3) {
               for (int i = 2; i < path.PointCount; i++) {
                  var point1 = path.Points[i - 2].ConvertToSKPoint(originPoint, scaleFactor);
                  var point2 = path.Points[i - 1].ConvertToSKPoint(originPoint, scaleFactor);
                  var point3 = path.Points[i].ConvertToSKPoint(originPoint, scaleFactor);
                  normalizedPath.CubicTo(point1, point2, point3);
               }
            } else {
               foreach (Point point in path.Points) {
                  normalizedPath.LineTo(point.ConvertToSKPoint(originPoint, scaleFactor));
               }
            }
         }

         return normalizedPath;
      }

      /// <summary>
      /// Converts a Point to SKPoint
      /// </summary>
      /// <param name="point">Point to be converted</param>
      /// <param name="originPoint">Origin point if transposition is to be made</param>
      /// <param name="scaleFactor">Scale factor if scaling is to be made</param>
      /// <returns></returns>
      public static SKPoint ConvertToSKPoint(this Point point, Point? originPoint = null, double scaleFactor = 1) {
         double originX = originPoint?.X ?? 0;
         double originY = originPoint?.Y ?? 0;
         return new SKPoint((float)((point.X - originX) * scaleFactor), (float)((point.Y - originY) * scaleFactor));
      }
   }
}
