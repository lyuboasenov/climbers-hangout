using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using FreshMvvm;

namespace ClimbersHangout.UI.Common {
   class ViewModelMapper : IFreshPageModelMapper {
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
