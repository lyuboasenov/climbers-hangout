using Acr.UserDialogs;
using ClimbersHangout.UI.Common.ViewModels.Pages;
using ClimbersHangout.UI.Common.ViewModels.Pages.Routes;
using FreshMvvm;
using Xamarin.Forms;

namespace ClimbersHangout.UI.Common {
   public partial class App : Application {
      public App() {
         //Set theme to light
         //var themeResources = typeof(Xamarin.Forms.Themes.DarkThemeResources);
         //App.Current.Resources = new ResourceDictionary { MergedWith = themeResources };

         //Substitute builtin PageModelMapper with a custom one.
         FreshPageModelResolver.PageModelMapper = new ViewModelMapper();

         //FreshIOC.Container.Register<IDatabaseService, DatabaseService>();
         FreshIOC.Container.Register<IUserDialogs>(UserDialogs.Instance);

         var masterDetailNav = new FreshMasterDetailNavigationContainer();
         masterDetailNav.Init("Menu");
         masterDetailNav.AddPage<RoutesMapPageModel>("Routes");
         masterDetailNav.AddPage<TimerTrainingListPageModel>("Timed trainings");
         masterDetailNav.AddPage<TimerSetupPageModel>("Timer");
         masterDetailNav.AddPage<AboutPageModel>("About");
         MainPage = masterDetailNav;

         MessagingCenter.Subscribe<Pages.Routes.RoutesMapPage>(this, "GoBackToMainPage", (m) => {
            Device.BeginInvokeOnMainThread(() => {
               MainPage = masterDetailNav;
            });
         });
      }

      protected override void OnStart() {
         // Handle when your app starts
      }

      protected override void OnSleep() {
         // Handle when your app sleeps
      }

      protected override void OnResume() {
         // Handle when your app resumes
      }
   }
}
