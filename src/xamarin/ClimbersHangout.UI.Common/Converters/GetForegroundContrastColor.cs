using System;
using System.Collections.Generic;
using System.Globalization;
using System.Text;
using Xamarin.Forms;

namespace ClimbersHangout.UI.Common.Converters {
   class GetForegroundContrastColor : IValueConverter {
      public object Convert(object value, Type targetType, object parameter, CultureInfo culture) {
         var currentBackgroundColor = App.Current.Resources["backgroundColor"];

         return value;
      }

      public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture) {
         throw new NotImplementedException();
      }
   }
}
