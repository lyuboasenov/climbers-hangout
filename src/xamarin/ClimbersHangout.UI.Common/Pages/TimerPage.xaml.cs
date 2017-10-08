using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace ClimbersHangout.UI.Common.Pages {
   [XamlCompilation(XamlCompilationOptions.Compile)]
   public partial class TimerPage : BaseContentPage {
      public TimerPage() {
         InitializeComponent();
      }
   }
}