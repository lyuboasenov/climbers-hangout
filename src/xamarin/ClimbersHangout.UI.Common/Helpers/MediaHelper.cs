using System;
using System.Threading.Tasks;
using ClimbersHangout.UI.Common.ViewModels.Pages;
using Plugin.Media;
using Plugin.Media.Abstractions;

namespace ClimbersHangout.UI.Common.Helpers {
   public static class MediaHelper {
      public static async Task<MediaFile> TakeOrPickImage(this BasePageModel pageModel) {
         MediaFile imageFile = null;
         var result = await pageModel.CoreMethods.DisplayActionSheet("Add route", "Cancel", null, new[] { "Take photo", "From gallery" });
         if (result == "Take photo") {
            imageFile = await TakeImage();
         } else {
            imageFile = await PickImageFromGallery();
         }

         return imageFile;
      }

      private static async Task<MediaFile> PickImageFromGallery() {
         MediaFile result = null;

         await CrossMedia.Current.Initialize();
         if (CrossMedia.Current.IsPickPhotoSupported) {
            result = await CrossMedia.Current.PickPhotoAsync();
         }

         return result;
      }

      private static async Task<MediaFile> TakeImage() {
         MediaFile result = null;

         await CrossMedia.Current.Initialize();
         if (CrossMedia.Current.IsCameraAvailable
             && CrossMedia.Current.IsTakePhotoSupported) {
            result = await CrossMedia.Current.TakePhotoAsync(
               new StoreCameraMediaOptions() {
                  PhotoSize = PhotoSize.Full,
                  MaxWidthHeight = 2048,
                  Name = Guid.NewGuid().ToString("N"),
                  DefaultCamera = CameraDevice.Rear,
               });
         }

         return result;
      }
   }
}
