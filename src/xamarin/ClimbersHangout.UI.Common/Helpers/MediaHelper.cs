using System;
using System.Threading.Tasks;
using ClimbersHangout.UI.Common.ViewModels.Pages;
using ExifLib;
using Plugin.Geolocator.Abstractions;
using Plugin.Media;
using Plugin.Media.Abstractions;

namespace ClimbersHangout.UI.Common.Helpers {
   public static class MediaHelper {
      public static async Task<MediaFile> TakeOrPickImage(this BasePageModel pageModel) {
         MediaFile imageFile = null;
         var result = await pageModel.CoreMethods.DisplayActionSheet("Add route", "Cancel", null, new[] { "Take photo", "From gallery" });
         if (result == "Take photo") {
            imageFile = await TakeImage();
         } else if (result == "From gallery") {
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

      public static Position GetImageGpsLocation(MediaFile imageMediaFile) {
         var stream = imageMediaFile.GetStream();
         var jpegInfo = ExifReader.ReadJpeg(stream);

         var latitude = ExifGpsToDouble(jpegInfo.GpsLatitudeRef == ExifGpsLatitudeRef.North, jpegInfo.GpsLatitude[0], jpegInfo.GpsLatitude[1], jpegInfo.GpsLatitude[2]);
         var longitude = ExifGpsToDouble(jpegInfo.GpsLongitudeRef == ExifGpsLongitudeRef.East, jpegInfo.GpsLongitude[0], jpegInfo.GpsLongitude[1], jpegInfo.GpsLongitude[2]);

         return new Position(latitude, longitude);
      }

      private static double ExifGpsToDouble(bool positive, double degrees, double minutes, double seconds) {
         double coorditate = degrees + (minutes / 60d) + (seconds / 3600d);
         if (!positive)
            coorditate = coorditate * -1;
         return coorditate;
      }
   }
}
