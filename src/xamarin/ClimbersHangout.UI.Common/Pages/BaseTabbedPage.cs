using System;
using System.Collections.Generic;
using System.Text;
using ClimbersHangout.UI.Common.Resources;
using ClimbersHangout.UI.Common.ViewModels.Pages;
using ClimbersHangout.UI.Common.ViewModels.Pages.Routes;
using FreshMvvm;
using SlideOverKit;
using Xamarin.Forms;

namespace ClimbersHangout.UI.Common.Pages {
   public class BaseTabbedPage : TabbedPage, IMenuContainerPage {
      SlideMenuView slideMenu;

      public BaseTabbedPage() {
         PopupViews = new Dictionary<string, SlidePopupView>();
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
            SetupTabs();
         }
      }

      private void SetupTabs() {

      }

      public SlideMenuView SlideMenu {
         get {
            return slideMenu;
         }
         set {
            if (slideMenu != null)
               slideMenu.Parent = null;
            slideMenu = value;
            if (slideMenu != null)
               slideMenu.Parent = this;
         }
      }
      public Dictionary<string, SlidePopupView> PopupViews { get; set; }
      public Action ShowMenuAction { get; set; }
      public Action HideMenuAction { get; set; }
      public Action<string> ShowPopupAction { get; set; }
      public Action HidePopupAction { get; set; }

      public void ShowMenu() {
         if (ShowMenuAction != null)
            ShowMenuAction();
      }

      public void HideMenu() {
         if (HideMenuAction != null)
            HideMenuAction();
      }

      public void ShowPopup(string name) {
         if (ShowPopupAction != null)
            ShowPopupAction(name);
      }

      public void HidePopup() {
         if (HidePopupAction != null)
            HidePopupAction();
      }
   }
}
