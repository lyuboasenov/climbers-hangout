using System;
using ClimbersHangout.UI.Common.Views;
using SlideOverKit;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace ClimbersHangout.UI.Common.Pages {
   [XamlCompilation(XamlCompilationOptions.Compile)]
   public partial class TimerTrainingListPage : BaseContentPage {
      public TimerTrainingListPage() {
         InitializeComponent();
         //SlideMenu = new QuickInnerMenuView(MenuOrientation.BottomToTop);
      }

      private void VisualElement_OnFocused(object sender, FocusEventArgs e) {
         ShowMenu();
      }

      private void VisualElement_OnUnfocused(object sender, FocusEventArgs e) {
         HideMenu();
      }

      private void TapGestureRecognizer_OnTapped(object sender, EventArgs e) {
         if (SlideMenu != null) {
            if (SlideMenu.IsShown) {
               HideMenu();
            } else {
               ShowMenu();
            }
         }
      }

      private void Button_OnClicked(object sender, EventArgs e) {
         SlideMenu = new QuickInnerMenuView(MenuOrientation.BottomToTop);
      }
   }
}