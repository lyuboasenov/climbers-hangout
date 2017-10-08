using System;
using System.Collections.Generic;
using System.Text;
using Plugin.CrossPlatformTintedImage.Abstractions;
using Xamarin.Forms;

namespace ClimbersHangout.UI.Common.Views {
   public class ClickableTintedImage : TintedImage {
      public static readonly BindableProperty CommandProperty =
         BindableProperty.Create("Command", typeof(Command), typeof(ClickableTintedImage), null);

      public ClickableTintedImage() {
         this.GestureRecognizers.Add(new TapGestureRecognizer(Tapped));
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
