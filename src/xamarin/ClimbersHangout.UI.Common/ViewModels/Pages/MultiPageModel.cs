using System;
using System.Collections.Generic;
using System.Linq;
using ClimbersHangout.UI.Common.Pages.Routes;
using FreshMvvm;
using PropertyChanged;
using Xamarin.Forms;

namespace ClimbersHangout.UI.Common.ViewModels.Pages {
   [AddINotifyPropertyChangedInterface]
   public class MultiPageModel : BasePageModel {
      private readonly IList<Child> children = new List<Child>();
      
      public IEnumerable<Page> Pages { get; private set; }

      public void AddChild(string title, BasePageModel pageModel, object data = null) {
         var child = new Child() {
            Title = title,
            PageModel = pageModel,
            Data = data,
            Page = FreshPageModelResolver.ResolvePageModel(pageModel.GetType(), data)
         };

         child.Page.Title = child.Title;
         children.Add(child);
         Pages = children.Select(c => c.Page).ToArray();
      }
      
      private class Child {
         public string Title { get; set; }
         public BasePageModel PageModel { get; set; }
         public object Data { get; set; }
         public Page Page { get; set; }
      }
   }
}
