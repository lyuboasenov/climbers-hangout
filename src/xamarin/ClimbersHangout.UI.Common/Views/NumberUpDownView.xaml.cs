using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace ClimbersHangout.UI.Common.Views {
   [XamlCompilation(XamlCompilationOptions.Compile)]
   public partial class NumberUpDownView : BaseContentView {
      public static readonly BindableProperty MinValueProperty =
         BindableProperty.Create("MinValue", typeof(int), typeof(NumberUpDownView), int.MinValue);

      public static readonly BindableProperty MaxValueProperty =
         BindableProperty.Create("MaxValue", typeof(int), typeof(NumberUpDownView), int.MaxValue);

      public NumberUpDownView() {
         InitializeComponent();
      }

      public int MinValue {
         get { return (int)GetValue(MinValueProperty); }
         set { SetValue(MinValueProperty, value); }
      }

      public int MaxValue {
         get { return (int)GetValue(MaxValueProperty); }
         set { SetValue(MaxValueProperty, value); }
      }
   }
}