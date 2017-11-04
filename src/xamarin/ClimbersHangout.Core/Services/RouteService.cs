using System;
using System.Collections.Generic;
using ClimbersHangout.Core.Models.Routes;
using Newtonsoft.Json;
using System.IO;
using ClimbersHangout.Core.Helpers;
using SkiaSharp;
using Path = System.IO.Path;

namespace ClimbersHangout.Core.Services {
   public class RouteService {
      private static readonly string RoutesPath = Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.LocalApplicationData), "Routes");
      private static readonly string RoutesCachePath = Path.Combine(RoutesPath, "Cache");
      private static readonly string RoutesUnsavedPath = Path.Combine(RoutesPath, "Unsaved");

      public static IEnumerable<Route> GetRoutes(Position position, double distance, DistanceUnit unit = DistanceUnit.Kilometers) {
         var routesList = new List<Route>();
         //Get locally saved routes 
         if (Directory.Exists(RoutesPath)) {
            var routesFiles = Directory.GetFiles(RoutesPath, "*.json", SearchOption.AllDirectories);

            foreach (var routeFile in routesFiles) {
               var route = JsonConvert.DeserializeObject(File.ReadAllText(routeFile), typeof(Route)) as Route;
               var currentDistance = GpsHelper.Distance(position, route.Position, unit);
               if (currentDistance < distance) {
                  routesList.Add(route);
               }
            }
         }

         //Get remote routes
         //TODO

         return routesList;
      }

      public static void SaveRoute(RouteTemplate routeTemplate) {
         var route = routeTemplate as Route;

         //Save route locally
         if (!Directory.Exists(RoutesUnsavedPath)) {
            Directory.CreateDirectory(RoutesUnsavedPath);
         }

         var internalName = route.Id.ToString("N");
         var dataFilePath = Path.Combine(RoutesPath, internalName + ".json");
         var schemaFilePath = Path.Combine(RoutesPath, internalName + ".jpg");

         var routeJson = JsonConvert.SerializeObject(route);
         File.WriteAllText(dataFilePath, routeJson);

         var bitmap = routeTemplate.Export();
         using (var fileStream = new SKFileWStream(schemaFilePath)) {
            bitmap.Encode(fileStream, SKEncodedImageFormat.Jpeg, 100);
         }

         //Save route remotely

         //Remove local copy on success
      }
   }
}
