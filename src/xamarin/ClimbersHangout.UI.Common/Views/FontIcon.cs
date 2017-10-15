using ClimbersHangout.UI.Common.Common.FontAwesome;
using Xamarin.Forms;

namespace ClimbersHangout.UI.Common.Views {
   public class FontIcon : Label {
      public static readonly BindableProperty IconProperty =
         BindableProperty.Create(nameof(Icon), typeof(Icon), typeof(FontIcon), Icon.Wifi,
            propertyChanged: OnIconChanged);

      public static readonly BindableProperty TintProperty =
         BindableProperty.Create(nameof(Tint), typeof(Color), typeof(FontIcon), Color.White,
            propertyChanged: OnTintChanged);

      private static void OnIconChanged(BindableObject bindable, object oldvalue, object newvalue) {
         var fontAwesomeIcon = ((FontIcon)bindable);
         fontAwesomeIcon.Text = IconResolver.Resolve(fontAwesomeIcon.Icon);
      }

      private static void OnTintChanged(BindableObject bindable, object oldvalue, object newvalue) {
         var fontAwesomeIcon = ((FontIcon)bindable);
         fontAwesomeIcon.TextColor = fontAwesomeIcon.Tint;
      }

      //Must match the exact "Name" of the font which you can get by double clicking the TTF in Windows
      public const string Typeface = "FontAwesome";

      public FontIcon() : this(null) { }

      public FontIcon(string fontAwesomeIcon = null) {
         FontFamily = Typeface;    //iOS is happy with this, Android needs a renderer to add ".ttf"
         Text = fontAwesomeIcon;
      }

      public Icon Icon {
         get { return (Icon)GetValue(IconProperty); }
         set { SetValue(IconProperty, value); }
      }

      public Color Tint {
         get { return (Color)GetValue(TintProperty); }
         set { SetValue(TintProperty, value); }
      }
   }
}