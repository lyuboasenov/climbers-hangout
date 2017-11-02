using System;
using ClimbersHangout.Core.Models.Routes;
using ClimbersHangout.UI.Common.Views;
using Plugin.Media.Abstractions;
using PropertyChanged;
using Xamarin.Forms;

namespace ClimbersHangout.UI.Common.ViewModels.Pages.Routes {
   [AddINotifyPropertyChangedInterface]
   public class RouteSchemaPageModel : BasePageModel, Okable, Cancelable {
      private Command okCommand;
      private Command cancelCommand;

      public RouteSetupView.Mode Mode { get; set; }
      public int ModeSelectIndex { get; set; }

      public Route Route { get; private set; }
      public Command OkCommand {
         get { return okCommand ?? (okCommand = new Command(Ok, CanOk)); }
      }

      public Command CancelCommand {
         get { return cancelCommand ?? (cancelCommand = new Command(Cancel)); }
      }

      public override void Init(object initData) {
         base.Init(initData);
         Route = (Route)initData;
      }

      public void OnModeSelectIndexChanged() {
         switch (ModeSelectIndex) {
            case 0:
               Mode = RouteSetupView.Mode.Move;
               break;
            case 1:
               Mode = RouteSetupView.Mode.Hold;
               break;
            case 2:
               Mode = RouteSetupView.Mode.Line;
               break;
         }
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
         if (null != cancelable && cancelable.CancelCommand.CanExecute(null)) {
            cancelable.CancelCommand.Execute(null);
         }
      }
   }
}
