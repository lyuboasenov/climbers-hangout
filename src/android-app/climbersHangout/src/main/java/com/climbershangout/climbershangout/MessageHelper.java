package com.climbershangout.climbershangout;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by lyuboslav on 9/29/2017.
 */

public class MessageHelper {

    public static void showDialog(int icon, String caption, String message){
        new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert)
                .setTitle(caption)
                .setMessage(message)
                .setIcon(icon)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .show();
    }

    public static void showMessage(String message){
        showDialog(android.R.drawable.ic_dialog_info, getContext().getString(R.string.message), message);
    }

    public static void showMessage(int resourceId){
        showMessage(getContext().getString(resourceId));
    }

    public static void showError(String message){
        showDialog(android.R.drawable.ic_dialog_alert, getContext().getString(R.string.error), message);
    }

    public static void showError(int resourceId){
        showError(getContext().getString(resourceId));
    }

    private static Context getContext(){
        return ClimbersHangoutApplication.getCurrent();
    }
}
