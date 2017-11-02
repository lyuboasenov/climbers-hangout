using System;
using System.Collections.Generic;
using System.Text;
using ClimbersHangout.UI.Common.ViewModels.Pages;
using FreshMvvm;
using SlideOverKit;
using Xamarin.Forms;

namespace ClimbersHangout.UI.Common.Pages {
   public class BaseContentPage : MenuContainerPage {
      public BaseContentPage() {
         //         ToolbarItems.Add(new ToolbarItem("Main Menu", null, () => {
         //            Application.Current.MainPage = new NavigationPage(new LaunchPage((App)Application.Current));
         //         }));
      }

      protected override void OnBindingContextChanged() {
         base.OnBindingContextChanged();

         var mapper = (ViewModelMapper)FreshPageModelResolver.PageModelMapper;
         var typeName = mapper.GetPageModelTypeName(GetType());
         Type type = Type.GetType(typeName);

         if (null == BindingContext
             || (type != null && BindingContext.GetType() != type)) {
            var context = Activator.CreateInstance(type);
            (context as BasePageModel).CurrentPage = this;
            BindingContext = context;
         }

         if (null != SlideMenu) {
            SlideMenu.BindingContext = this.BindingContext;
         }

         if (null != PopupViews) {
            foreach (var popupView in PopupViews.Values) {
               popupView.BindingContext = this.BindingContext;
            }
         }
      }
   }
}
