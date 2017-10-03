package com.climbershangout.climbershangout.climb;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import com.climbershangout.climbershangout.R;

/**
 * Created by lyuboslav on 9/29/2017.
 */

public class ChooseImageSourceDialog extends AppCompatDialog {

    //Members
    private View view;
    private Dialog.OnClickListener fromGalleryOnClickListener;
    private Dialog.OnClickListener fromCameraOnClickListener;

    //Property
    private ImageButton getFromGalleryButton(){
        return (ImageButton) view.findViewById(R.id.choose_image_gallery);
    }

    private ImageButton getFromCameraButton(){
        return (ImageButton) view.findViewById(R.id.choose_image_camera);
    }

    public ChooseImageSourceDialog setFromGalleryOnClickListener(OnClickListener fromGalleryOnClickListener) {
        this.fromGalleryOnClickListener = fromGalleryOnClickListener;
        return this;
    }

    public ChooseImageSourceDialog setFromCameraOnClickListener(OnClickListener fromCameraOnClickListener) {
        this.fromCameraOnClickListener = fromCameraOnClickListener;
        return this;
    }

    private ChooseImageSourceDialog getDialog(){
        return this;
    }

    //Constructors
    public ChooseImageSourceDialog(Context context) {
        super(context);
    }

    //Methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.dialog_choose_image_source, null);

        setContentView(view);

        getFromCameraButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fromCameraOnClickListener != null){
                    fromCameraOnClickListener.onClick(getDialog(), 0);
                }

                dismiss();
            }
        });
        getFromGalleryButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fromGalleryOnClickListener != null){
                    fromGalleryOnClickListener.onClick(getDialog(), 0);
                }

                dismiss();
            }
        });

    }
}
