package com.climbershangout.climbershangout.climb;

import static com.climbershangout.climbershangout.climb.AddRouteActivity.FILE_URI_ARG;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.climbershangout.climbershangout.RequestCode;
import com.climbershangout.climbershangout.StorageHelper;

import java.io.File;
import java.io.IOException;

/**
 * Created by lyuboslav on 9/29/2017.
 */

public class RouteCreationHelper {

    //Members
    public static final int RC_NEW_ROUTE_REQUEST_IMAGE = RequestCode.RC_NEW_ROUTE_REQUEST_IMAGE;
    public static final int RC_CREATE_ROUTE = RequestCode.RC_CREATE_ROUTE;

    public static final int FROM_GALLERY = 0;
    public static final int FROM_CAMERA = 1;

    private int source;
    private Uri imageUri;
    private Context context;

    //Properties
    public Context getContext() {
        return context;
    }

    private void setContext(Context context) {
        this.context = context;
    }

    //Constructors
    public RouteCreationHelper(Context context, int source){
        setContext(context);
        this.source = source;
    }

    //Methods
    public Intent getRequestImageIntent(){
        Intent intent = null;

        switch (source){
            case FROM_CAMERA:
                intent = fromCamera();
                break;
            case FROM_GALLERY:
                intent = fromGallery();
                break;
        }

        return intent;
    }

    public Intent getCreateRouteActivityIntent(Intent requestImageIntent){

        if(null == imageUri && requestImageIntent.getData() == null){
            throw new IllegalArgumentException("Image not supplied. An activity should be started with intent supplied from getRequestImageIntent.");
        }

        if(source == FROM_GALLERY){
            imageUri = requestImageIntent.getData();
        }

        Intent createRouteActivity = new Intent(getContext(), AddRouteActivity.class);
        createRouteActivity.putExtra(FILE_URI_ARG, imageUri.toString());

        return createRouteActivity;
    }

    private Intent fromGallery(){
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        return chooserIntent;
    }

    private Intent fromCamera(){
        Intent intent = null;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {

            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = StorageHelper.getStorageHelper().createTempFile();
                imageUri = Uri.fromFile(photoFile);
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                imageUri = FileProvider.getUriForFile(getContext(),
                        "com.climbershangout.climbershangout.fileprovider",
                        photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                intent = takePictureIntent;
            }
        }

        return intent;
    }



}
