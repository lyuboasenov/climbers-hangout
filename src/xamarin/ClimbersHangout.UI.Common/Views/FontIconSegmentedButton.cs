using System;
using System.Collections.Generic;
using System.Net.Mime;
using System.Text;
using ClimbersHangout.UI.Common.Common.FontAwesome;
using FreshEssentials;
using Xamarin.Forms;

namespace ClimbersHangout.UI.Common.Views {
   public class FontIconSegmentedButton : SegmentedButton {
      public Icon icon;
      public Icon Icon {
         get { return icon; }
         set {
            if (icon != value) {
               icon = value;
               OnIconChanged();
            }
         }
      }

      private void OnIconChanged() {
         this.Title = IconResolver.Resolve(Icon);
      }
   }
}
