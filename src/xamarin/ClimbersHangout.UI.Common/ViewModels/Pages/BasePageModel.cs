using System;
using System.Collections.Generic;
using System.Text;
using FreshMvvm;
using PropertyChanged;

namespace ClimbersHangout.UI.Common.ViewModels.Pages {
   [AddINotifyPropertyChangedInterface]
   public class BasePageModel : FreshBasePageModel {
      public BasePageModel() {

      }
   }
}
