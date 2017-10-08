using System;
using System.Collections.Generic;
using System.Text;
using ClimbersHangout.UI.Common.Helpers;
using Plugin.CrossPlatformTintedImage.Abstractions;
using Xamarin.Forms;

namespace ClimbersHangout.UI.Common.Views {
   public class TintedImageButton : TintedImage {
      public static readonly BindableProperty CommandProperty =
         BindableProperty.Create("Command", typeof(Command), typeof(ClickableTintedImage), null);

      public TintedImageButton() {
         this.GestureRecognizers.Add(new TapGestureRecognizer(Tapped));
         this.WidthRequest = SizeHelper.SIZE_25;
         this.HeightRequest = SizeHelper.SIZE_25;
         SetBinding(TintColorProperty, new Binding() { Source = ColorHelper.ContrastColor });
      }

      public Command Command {
         get { return (Command)GetValue(CommandProperty); }
         set { SetValue(CommandProperty, value); }
      }

      private void Tapped(View obj) {
         if (Command != null && Command.CanExecute(null)) {
            Command.Execute(null);
         }
      }
   }
}
