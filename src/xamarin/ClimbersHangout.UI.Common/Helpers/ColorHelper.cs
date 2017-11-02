using SkiaSharp;
using Xamarin.Forms;

namespace ClimbersHangout.UI.Common.Helpers {
   class ColorHelper {
      public static Color GetContrastColor(Color color) {
         var l = 0.2126 * color.R + 0.7152 * color.G + 0.0722 * color.B;

         return l < 0.5 ? Color.White : Color.Black;
      }

      public static Color GetBackgroundColor() {
         Color bgColor = Color.Black;
         var currentBackgroundColor = App.Current.Resources != null ? App.Current.Resources["backgroundColor"] : null;
         if (currentBackgroundColor != null) {
            bgColor = (Color)currentBackgroundColor;
         }

         return bgColor;
      }

      public static Color ContrastColor {
         get {
            var bgColor = GetBackgroundColor();
            return GetContrastColor(bgColor);
         }
      }

      public static SKColor TranslateColor(Color color) {
         return new SKColor((byte)(color.R * 255), (byte)(color.G * 255), (byte)(color.B * 255), (byte)(color.A * 255));
      }
   }
}
