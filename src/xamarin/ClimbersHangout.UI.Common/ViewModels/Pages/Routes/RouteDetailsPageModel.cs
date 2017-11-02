using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using ClimbersHangout.Core.Models.Routes;
using ClimbersHangout.UI.Common.Resources;
using ClimbersHangout.UI.Common.Views;
using FreshMvvm;
using Plugin.Media.Abstractions;
using PropertyChanged;
using Xamarin.Forms;
using Xamarin.Forms.Maps;
using Position = Plugin.Geolocator.Abstractions.Position;

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
      public Xamarin.Forms.Maps.Position CurrentLocation { get; set; }
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

         CurrentLocation = new Xamarin.Forms.Maps.Position(route.Position.Latitude, route.Position.Longitude);
         VisibleRegion = MapSpan.FromCenterAndRadius(CurrentLocation, new Distance(100));
         GradeList = new ObservableCollection<Grade>(Grade.GetGradeList(route.Type));
      }

      private void OnCurrentLocationChanged() {
         route.Position.Latitude = CurrentLocation.Latitude;
         route.Position.Longitude = CurrentLocation.Longitude;
      }

      private void OnNameChanged() {
         route.Name = Name;
      }

      private void OnDescriptionChanged() {
         route.Description = Description;
      }

      private void OnSelectedGradeChanged() {
         route.Grade = SelectedGrade;
      }
      
      private bool CanOk() {
         return true;
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
