using System;
using ClimbersHangout.UI.Common.Common.FontAwesome;
using Xamarin.Forms;

namespace ClimbersHangout.UI.Common.Views {
   public class FontIconButton : Button {
      public static readonly BindableProperty IconProperty =
         BindableProperty.Create(nameof(Icon), typeof(Icon), typeof(FontIcon), Icon.Wifi,
            propertyChanged: OnIconChanged);

      public static readonly BindableProperty TintProperty =
         BindableProperty.Create(nameof(Tint), typeof(Color), typeof(FontIcon), Color.White,
            propertyChanged: OnTintChanged);

      private static void OnIconChanged(BindableObject bindable, object oldvalue, object newvalue) {
         var fontAwesomeButton = ((FontIconButton)bindable);
         fontAwesomeButton.Text = IconResolver.Resolve(fontAwesomeButton.Icon);
      }

      private static void OnTintChanged(BindableObject bindable, object oldvalue, object newvalue) {
         var fontAwesomeButton = ((FontIconButton)bindable);
         fontAwesomeButton.TextColor = fontAwesomeButton.Tint;
      }

      //Must match the exact "Name" of the font which you can get by double clicking the TTF in Windows
      public const string Typeface = "FontAwesome";

      public FontIconButton() : this(null) { }

      public FontIconButton(string fontAwesomeIcon = null) {
         BackgroundColor = Color.Transparent;
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

      protected override SizeRequest OnMeasure(double widthConstraint, double heightConstraint) {
         double sizeConstraint = Math.Min(widthConstraint, heightConstraint);
         return base.OnMeasure(sizeConstraint, sizeConstraint);
      }
   }
}