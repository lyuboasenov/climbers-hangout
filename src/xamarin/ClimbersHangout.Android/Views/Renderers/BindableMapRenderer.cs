using Android.Gms.Maps;
using ClimbersHangout.Android.Views.Renderers;
using ClimbersHangout.UI.Common.Views;
using Xamarin.Forms;
using Xamarin.Forms.Maps;
using Xamarin.Forms.Maps.Android;
using Xamarin.Forms.Platform.Android;

[assembly: ExportRenderer(typeof(BindableMap), typeof(BindableMapRenderer))]

namespace ClimbersHangout.Android.Views.Renderers {
   public class BindableMapRenderer : MapRenderer {
      // We use a native google map for Android
      private GoogleMap _map;

      protected override void OnMapReady(GoogleMap map) {
         base.OnMapReady(map);
         _map = map;

         if (_map != null)
            _map.MapClick += googleMap_MapClick;
      }

      protected override void OnElementChanged(ElementChangedEventArgs<Map> e) {
         if (_map != null)
            _map.MapClick -= googleMap_MapClick;

         base.OnElementChanged(e);
         
         if (Control != null)
            ((MapView)Control).GetMapAsync(this);
      }

      private void googleMap_MapClick(object sender, GoogleMap.MapClickEventArgs e) {
         ((BindableMap)Element).OnTap(new Position(e.Point.Latitude, e.Point.Longitude));
      }
   }
}