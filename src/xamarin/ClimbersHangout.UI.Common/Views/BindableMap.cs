using System;
using System.Collections.Generic;
using ClimbersHangout.UI.Common.Resources;
using Xamarin.Forms;
using Xamarin.Forms.Maps;

namespace ClimbersHangout.UI.Common.Views {
   public class BindableMap : Xamarin.Forms.Maps.Map {
      public static readonly BindableProperty CurrentLocationProperty =
         BindableProperty.Create(nameof(CurrentLocation), typeof(Position), typeof(BindableMap), new Position(0, 0),
            propertyChanged: OnCurrentLocationChanged);

      public static readonly BindableProperty BindablePinsProperty =
         BindableProperty.Create(nameof(BindablePins), typeof(IList<Pin>), typeof(BindableMap), null,
            propertyChanged: OnBindablePinsChanged);

      public static readonly BindableProperty BindableVisibleRegionProperty =
         BindableProperty.Create(nameof(BindableVisibleRegion), typeof(MapSpan), typeof(BindableMap), null,
            propertyChanged: OnBindableVisibleRegionChanged);

      private static void OnCurrentLocationChanged(BindableObject bindable, object oldvalue, object newvalue) {
         ((BindableMap)bindable).OnCurrentLocationChanged();
      }

      private static void OnBindableVisibleRegionChanged(BindableObject bindable, object oldvalue, object newvalue) {
         ((BindableMap)bindable).OnBindableVisibleRegionChanged();
      }

      private void OnCurrentLocationChanged() {
         Pins.Clear();
         Pins.Add(new Pin() { Position = CurrentLocation, Label = Strings.CurrentLocation });
      }

      private void OnBindableVisibleRegionChanged() {
         this.MoveToRegion(BindableVisibleRegion);
      }

      private static void OnBindablePinsChanged(BindableObject bindable, object oldvalue, object newvalue) {
         ((BindableMap)bindable).OnBindablePinsChanged();
      }

      private void OnBindablePinsChanged() {
         Pins.Clear();
         if (null != BindablePins) {
            foreach (var pin in BindablePins) {
               Pins.Add(pin);
            }
         }
      }

      public Position CurrentLocation {
         get { return (Position)GetValue(CurrentLocationProperty); }
         set { SetValue(CurrentLocationProperty, value); }
      }

      public IList<Pin> BindablePins {
         get { return (IList<Pin>)GetValue(BindablePinsProperty); }
         set { SetValue(BindablePinsProperty, value); }
      }

      public MapSpan BindableVisibleRegion {
         get { return (MapSpan)GetValue(BindableVisibleRegionProperty); }
         set { SetValue(BindableVisibleRegionProperty, value); }
      }

      public void OnTap(Position position) {
         CurrentLocation = position;
      }
   }
}
