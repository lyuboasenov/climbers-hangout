using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using Plugin.Geolocator;
using Plugin.Geolocator.Abstractions;

namespace ClimbersHangout.UI.Common.Helpers {
   class GpsHelper {
      public static async Task<Position> GetCurrentPositionAsync(bool tryLastKnown) {
         Position position = null;

         var locator = CrossGeolocator.Current;
         locator.DesiredAccuracy = 0;

         if (tryLastKnown) {
            position = await locator.GetLastKnownPositionAsync();
         }

         if (null == position && await locator.GetIsGeolocationAvailableAsync() && await locator.GetIsGeolocationEnabledAsync()) {
            position = await locator.GetPositionAsync(TimeSpan.FromSeconds(20), null, true);
         }

         return position;
      }
   }
}
