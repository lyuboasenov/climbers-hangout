using ClimbersHangout.UI.Common.Common.FontAwesome;
using Xamarin.Forms;

namespace ClimbersHangout.UI.Common.Views {
   public class FontAwesomeIcon : Label {
      public static readonly BindableProperty IconProperty =
         BindableProperty.Create(nameof(Icon), typeof(Icon), typeof(FontAwesomeIcon), Icon.Wifi,
            propertyChanged: OnIconChanged);

      private static void OnIconChanged(BindableObject bindable, object oldvalue, object newvalue) {
         var fontAwesomeIcon = ((FontAwesomeIcon)bindable);
         fontAwesomeIcon.Text = IconResolver.Resolve(fontAwesomeIcon.Icon);
      }

      //Must match the exact "Name" of the font which you can get by double clicking the TTF in Windows
      public const string Typeface = "FontAwesome";

      public FontAwesomeIcon() : this(null) { }

      public FontAwesomeIcon(string fontAwesomeIcon = null) {
         FontFamily = Typeface;    //iOS is happy with this, Android needs a renderer to add ".ttf"
         Text = fontAwesomeIcon;
      }

      public Icon Icon {
         get { return (Icon)GetValue(IconProperty); }
         set { SetValue(IconProperty, value); }
      }
   }
}