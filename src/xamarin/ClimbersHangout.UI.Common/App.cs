using Acr.UserDialogs;
using ClimbersHangout.UI.Common.PageModels;
using FreshMvvm;
using Xamarin.Forms;

namespace ClimbersHangout.UI.Common {
   public class App : Application {
      public App() {
         //FreshIOC.Container.Register<IDatabaseService, DatabaseService>();
         FreshIOC.Container.Register<IUserDialogs>(UserDialogs.Instance);
         var masterDetailNav = new FreshMasterDetailNavigationContainer();
         masterDetailNav.Init("Menu");
         masterDetailNav.AddPage<TimerTrainingListPageModel>("Timer");
         masterDetailNav.AddPage<AboutPageModel>("About");
         MainPage = masterDetailNav;
         //         var masterDetail = new ThemedMasterDetailNavigationContainer();
         //         masterDetail.Init("Menu", "hamburger.png");
         //         masterDetail.AddPageWithIcon<TimerTrainingListPageModel>("Timer", "hamburger.png");
         //         masterDetail.AddPageWithIcon<AboutPageModel>("About", "hamburger.png");
         //         MainPage = masterDetail;
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
