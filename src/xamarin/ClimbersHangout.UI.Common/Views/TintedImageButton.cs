using ClimbersHangout.UI.Common.Helpers;
using Plugin.CrossPlatformTintedImage.Abstractions;
using Xamarin.Forms;

namespace ClimbersHangout.UI.Common.Views {
   public class TintedImageButton : TintedImage {
      public static readonly BindableProperty CommandProperty =
         BindableProperty.Create("Command", typeof(Command), typeof(ClickableTintedImage), null, propertyChanged: OnCommandPropertyChanged);

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

      private static void OnCommandPropertyChanged(BindableObject bindable, object oldvalue, object newvalue) {
         TintedImageButton button = ((TintedImageButton)bindable);
         button.Command.CanExecuteChanged += (sender, e) => button.CanExecuteChanged();
      }

      private void CanExecuteChanged() {
         if (Command.CanExecute(null)) {
            Opacity = 1;
         } else {
            Opacity = 0.4;
         }
      }
   }
}
