using System;
using System.Collections.Generic;
using System.Text;
using ClimbersHangout.UI.Common.Helpers;
using Xamarin.Forms;

namespace ClimbersHangout.UI.Common.Views {
   public class SeparatorView : BoxView {
      public SeparatorView() {
         this.HeightRequest = 1;
         SetBinding(ColorProperty, new Binding() { Source = ColorHelper.ContrastColor });
      }
   }
}
