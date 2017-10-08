using System;
using FreshMvvm;
using Xamarin.Forms;

namespace ClimbersHangout.UI.Common.Views {
   public class BaseContentView : ContentView {
      public static readonly BindableProperty ModelProperty =
         BindableProperty.Create("Model", typeof(object), typeof(BaseContentView), propertyChanged: OnModelChanged);

      public object Model {
         get { return GetValue(ModelProperty); }
         set { SetValue(ModelProperty, value); }
      }

      private void OnModelChanged() {
         var mapper = (ViewModelMapper)FreshPageModelResolver.PageModelMapper;
         var typeName = mapper.GetPageModelTypeName(GetType());
         Type type = Type.GetType(typeName);

         if (null == BindingContext
            || BindingContext.GetType() != type) {
            var context = Activator.CreateInstance(type, Model);
            BindingContext = context;
         }
      }

      private static void OnModelChanged(BindableObject bindable, object oldvalue, object newvalue) {
         var baseContentView = bindable as BaseContentView;
         if (baseContentView != null) {
            baseContentView.OnModelChanged();
         }
      }
   }
}
