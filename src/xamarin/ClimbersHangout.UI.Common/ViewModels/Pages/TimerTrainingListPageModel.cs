using System;
using System.Threading.Tasks;
using ClimbersHangout.Core.Models;
using ClimbersHangout.Core.Services;
using FreshMvvm;
using Plugin.Media;
using Plugin.Media.Abstractions;
using PropertyChanged;
using Xamarin.Forms;

namespace ClimbersHangout.UI.Common.ViewModels.Pages {
   [AddINotifyPropertyChangedInterface]
   public class TimerTrainingListPageModel : FreshBasePageModel {

      private Command addTrainingCommand;

      public TimerTrainingListPageModel() {
         TrainingList = TimeTrainingService.GetAllTrainings();
      }

      public PeriodGroup[] TrainingList { get; private set; }

      public PeriodGroup SelectedTraining { get; set; }

      public Command AddTrainingCommand {
         get { return addTrainingCommand ?? (addTrainingCommand = new Command(AddTraining)); }
      }

      public void OnSelectedTrainingChanged() {
         if (null != SelectedTraining) {
            CoreMethods.PushPageModel<TimerPageModel>(SelectedTraining, true);
            SelectedTraining = null;
         }
      }

      private async void AddTraining() {
         var result = await CurrentPage.DisplayActionSheet("Add route", "Cancel", null, new[] { "Take photo", "From gallery" });
         MediaFile image;
         if (result == "Take photo") {
            image = await TakeImage();
         } else {
            image = await PickImageFromGallery();
         }

         if (image != null) {
            await CoreMethods.PushPageModel<ManageRoutePageModel>(image, true);
         }
      }

      private async Task<MediaFile> PickImageFromGallery() {
         MediaFile result = null;

         await CrossMedia.Current.Initialize();
         if (CrossMedia.Current.IsPickPhotoSupported) {
            result = await CrossMedia.Current.PickPhotoAsync();
         }

         return result;
      }

      private async Task<MediaFile> TakeImage() {
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
