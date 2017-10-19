using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Collections.Specialized;
using FreshEssentials;
using Xamarin.Forms;

namespace ClimbersHangout.UI.Common.Views {
   public class FontIconSegmentedButtonGroup : SegmentedButtonGroup {

      public new IList<SegmentedButton> SegmentedButtons {
         get;
         internal set;
      }

      public FontIconSegmentedButtonGroup() : base() {
         var segmentedButtons = new ObservableCollection<SegmentedButton>();
         segmentedButtons.CollectionChanged += SegmentedButtons_CollectionChanged;
         SegmentedButtons = segmentedButtons;
      }

      private void SegmentedButtons_CollectionChanged(object sender, NotifyCollectionChangedEventArgs e) {
         RebuildButtons();
      }

      protected override void OnBindingContextChanged() {
         base.OnBindingContextChanged();

         RebuildButtons();
      }

      protected override void OnPropertyChanged(string propertyName) {
         base.OnPropertyChanged(propertyName);

         if (propertyName == "SelectedIndex") {
            SetSelectedIndex();
         }
      }

      private void RebuildButtons() {
         this.ColumnDefinitions.Clear();
         this.Children.Clear();

         for (int i = 0; i < SegmentedButtons.Count; i++) {
            var buttonSeg = SegmentedButtons[i];

            Label label = buttonSeg is FontIconSegmentedButton ? new FontIcon() : new Label();

            label.Text = buttonSeg.Title;
            label.Style = LabelStyle;
            label.HorizontalTextAlignment = TextAlignment.Center;
            label.VerticalTextAlignment = TextAlignment.Center;

            this.ColumnDefinitions.Add(new ColumnDefinition { Width = new GridLength(1, GridUnitType.Star) });
            var frame = new AdvancedFrame();
            if (i == 0)
               frame.Corners = RoundedCorners.left;
            else if ((i + 1) == SegmentedButtons.Count)
               frame.Corners = RoundedCorners.right;
            else
               frame.Corners = RoundedCorners.none;

            frame.CornerRadius = CornerRadius;
            frame.OutlineColor = OnColor;
            frame.Content = label;
            frame.HorizontalOptions = LayoutOptions.FillAndExpand;
            frame.VerticalOptions = LayoutOptions.FillAndExpand;

            if (i == SelectedIndex) {
               frame.InnerBackground = OnColor;
               label.TextColor = OffColor;
            } else {
               frame.InnerBackground = OffColor;
               label.TextColor = OnColor;
            }

            var tapGesture = new TapGestureRecognizer();
            tapGesture.Command = ItemTapped;
            tapGesture.CommandParameter = i;
            frame.GestureRecognizers.Add(tapGesture);

            this.Children.Add(frame, i, 0);
         }
      }

      public Command ItemTapped {
         get {
            return new Command((obj) => {
               int index = (int)obj;
               SelectedIndex = index;
               Command?.Execute(this.SegmentedButtons[index].Title);
            });
         }
      }

      void SetSelectedIndex() {
         for (int i = 0; i < Children.Count; i++) {
            var frame = Children[i] as AdvancedFrame;
            var label = frame.Content as Label;
            if (i == SelectedIndex) {
               frame.InnerBackground = OnColor;
               label.TextColor = OffColor;
            } else {
               frame.InnerBackground = OffColor;
               label.TextColor = OnColor;
            }
         }
      }
   }
}
