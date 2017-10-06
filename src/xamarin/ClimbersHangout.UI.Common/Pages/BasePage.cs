using System;
using System.Collections.Generic;
using System.Text;
using Xamarin.Forms;

namespace ClimbersHangout.UI.Common.Pages {
   public class BasePage : ContentPage {
      public BasePage() {
         ToolbarItems.Add(new ToolbarItem("Main Menu", null, () => {
            Application.Current.MainPage = new NavigationPage(new LaunchPage((App)Application.Current));
         }));
      }
   }
}
