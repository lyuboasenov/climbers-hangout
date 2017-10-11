using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using ClimbersHangout.Android.Renders;
using ClimbersHangout.UI.Common.Pages;
using SlideOverKit.Droid;
using Xamarin.Forms;
using Xamarin.Forms.Platform.Android;

[assembly: ExportRenderer(typeof(BaseContentPage), typeof(PageImpInterfaceRendererDroid))]
namespace ClimbersHangout.Android.Renders {
   // As your page cannot inherit from MenuContainerPage,
   // You must create a renderer page, copy these codes and rename.
   public class PageImpInterfaceRendererDroid : PageRenderer, ISlideOverKitPageRendererDroid {
      public Action<ElementChangedEventArgs<Page>> OnElementChangedEvent { get; set; }

      public Action<bool, int, int, int, int> OnLayoutEvent { get; set; }

      public Action<int, int, int, int> OnSizeChangedEvent { get; set; }

      public PageImpInterfaceRendererDroid() {
         new SlideOverKitDroidHandler().Init(this);
      }

      protected override void OnElementChanged(ElementChangedEventArgs<Page> e) {
         base.OnElementChanged(e);
         if (OnElementChangedEvent != null)
            OnElementChangedEvent(e);
      }

      protected override void OnLayout(bool changed, int l, int t, int r, int b) {
         base.OnLayout(changed, l, t, r, b);
         if (OnLayoutEvent != null)
            OnLayoutEvent(changed, l, t, r, b);
      }

      protected override void OnSizeChanged(int w, int h, int oldw, int oldh) {
         base.OnSizeChanged(w, h, oldw, oldh);
         if (OnSizeChangedEvent != null)
            OnSizeChangedEvent(w, h, oldw, oldh);
      }
   }
}