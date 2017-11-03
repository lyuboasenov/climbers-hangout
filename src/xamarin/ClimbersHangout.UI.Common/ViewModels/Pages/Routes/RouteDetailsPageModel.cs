using System.Collections.ObjectModel;
using ClimbersHangout.Core.Models.Routes;
using PropertyChanged;
using Xamarin.Forms;
using Xamarin.Forms.Maps;
using Position = Xamarin.Forms.Maps.Position;

namespace ClimbersHangout.UI.Common.ViewModels.Pages.Routes {
   [AddINotifyPropertyChangedInterface]
   public class RouteDetailsPageModel : BasePageModel, Okable, Cancelable {
      private Route route;
      private Command okCommand;
      private Command cancelCommand;

      public string Name { get; set; }
      public string Description { get; set; }
      public ObservableCollection<Grade> GradeList { get; set; }
      public Grade SelectedGrade { get; set; }
      public Position SelectedLocation { get; set; }
      public MapSpan VisibleRegion { get; set; }
      public Command OkCommand {
         get { return okCommand ?? (okCommand = new Command(Ok, CanOk)); }
      }

      public Command CancelCommand {
         get { return cancelCommand ?? (cancelCommand = new Command(Cancel)); }
      }

      public override void Init(object initData) {
         base.Init(initData);
         route = (Route) initData;

         SelectedLocation = new Position(route.Position.Latitude, route.Position.Longitude);
         VisibleRegion = MapSpan.FromCenterAndRadius(SelectedLocation, new Distance(100));
         GradeList = new ObservableCollection<Grade>(Grade.GetGradeList(route.Type));
      }

      private void OnCurrentLocationChanged() {
         route.Position.Latitude = SelectedLocation.Latitude;
         route.Position.Longitude = SelectedLocation.Longitude;
      }

      private void OnNameChanged() {
         route.Name = Name;
         OkCommand.ChangeCanExecute();
      }

      private void OnDescriptionChanged() {
         route.Description = Description;
      }

      private void OnSelectedGradeChanged() {
         route.Grade = SelectedGrade;
         OkCommand.ChangeCanExecute();
      }
      
      private bool CanOk() {
         return !string.IsNullOrEmpty(Name) && SelectedGrade != null;
      }

      private void Ok() {
         var okable = Parent as Okable;
         if (null != okable) {
            okable.OkCommand.Execute(null);
         }
      }

      private void Cancel() {
         var cancelable = Parent as Cancelable;
         if (null != cancelable) {
            cancelable.CancelCommand.Execute(null);
         }
      }
   }
}
