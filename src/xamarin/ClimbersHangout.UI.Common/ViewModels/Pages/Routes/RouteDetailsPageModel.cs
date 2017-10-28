using System.Collections.ObjectModel;
using ClimbersHangout.Core.Models.Routes;
using ClimbersHangout.UI.Common.Views;
using FreshMvvm;
using Plugin.Media.Abstractions;
using PropertyChanged;

namespace ClimbersHangout.UI.Common.ViewModels.Pages.Routes {
   [AddINotifyPropertyChangedInterface]
   public class RouteDetailsPageModel : BasePageModel {

      public string Name { get; set; }
      public string Description { get; set; }
      public ObservableCollection<Grade> GradeList { get; set; }
      public Grade SelectedGrade { get; set; }
   }
}
