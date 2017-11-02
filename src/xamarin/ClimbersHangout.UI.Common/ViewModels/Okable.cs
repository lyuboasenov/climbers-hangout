using System;
using System.Collections.Generic;
using System.Text;
using Xamarin.Forms;

namespace ClimbersHangout.UI.Common.ViewModels {
   public interface Okable {
      Command OkCommand { get; }
   }
}
