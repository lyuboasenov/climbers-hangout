using System;
using System.Collections.Generic;
using ClimbersHangout.UI.Common.Helpers;
using FreshMvvm;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace ClimbersHangout.UI.Common.Views {
   [XamlCompilation(XamlCompilationOptions.Compile)]
   public partial class TimeSelectView : BaseContentView {

      public TimeSelectView() {
         InitializeComponent();
      }
   }
}