using FreshMvvm;
using PropertyChanged;

namespace ClimbersHangout.UI.Common.ViewModels.Pages {
   [AddINotifyPropertyChangedInterface]
   public class BasePageModel : FreshBasePageModel {

      public BasePageModel Parent { get; set; }

      public BasePageModel() {
         
      }
   }
}
