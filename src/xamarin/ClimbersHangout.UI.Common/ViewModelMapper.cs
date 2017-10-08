using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using FreshMvvm;

namespace ClimbersHangout.UI.Common {
   class ViewModelMapper : IFreshPageModelMapper {
      private static Type[] viewModelTypes;
      private static Type[] viewTypes;

      static ViewModelMapper() {
         var allTypes = typeof(ViewModelMapper).GetTypeInfo().Assembly.GetTypes();
         viewModelTypes = allTypes
            .Where(t => t.FullName.EndsWith("ViewModel") || t.FullName.EndsWith("PageModel"))
            .ToArray();

         viewTypes = allTypes
            .Where(t => t.FullName.EndsWith("View") || t.FullName.EndsWith("Page"))
            .ToArray();
      }

      public string GetPageTypeName(Type pageModelType) {
         return pageModelType.AssemblyQualifiedName
            .Replace(".ViewModels.", ".")
            .Replace("ViewModel", "View")
            .Replace("PageModel", "Page");
      }

      public string GetPageModelTypeName(Type pageType) {
         return pageType.AssemblyQualifiedName
            .Replace(".UI.Common.", ".UI.Common.ViewModels.")
            .Replace("View,", "ViewModel,")
            .Replace("Page,", "PageModel,");
      }
   }
}
