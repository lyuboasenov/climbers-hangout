using System;
using System.Collections.Generic;
using System.Text;

namespace ClimbersHangout.UI.Common.Common.FontAwesome {
   internal class IconResolver {
      private static Dictionary<Icon, string> iconMap;


      static IconResolver() {
         iconMap = new Dictionary<Icon, string>();
         InitializeIconMap();
      }

      public static string Resolve(Icon icon) {
         if (!iconMap.ContainsKey(icon)) {
            throw new ArgumentException(String.Format("{0} icon not supported.", icon));
         }

         return iconMap[icon];
      }

      private static void InitializeIconMap() {
         iconMap.Add(Icon.Wifi, "\uf1eb");
         iconMap.Add(Icon.WikipediaW, "\uf266");
         iconMap.Add(Icon.WindowClose, "\uf2d3");
         //iconMap.Add(Icon., "\u");
      }
   }
}
