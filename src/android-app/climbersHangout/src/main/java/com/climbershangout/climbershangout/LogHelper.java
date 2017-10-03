package com.climbershangout.climbershangout;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import java.util.Objects;

/**
 * Created by lyuboslav on 9/30/2017.
 */

public class LogHelper {
    public static void l(String tag, String message){
        Log.d(tag, message);
    }

    public static void l(String tag, String format, Object... params){
        l(tag, format(format, params));
    }

    public static void l(Fragment fragment, String message){
        l(fragment.getClass().getSimpleName(), message);
    }

    public static void l(Fragment fragment, String format, Object... params){
        l(fragment, format(format, params));
    }

    public static void l(View view, String message){
        l(view.getClass().getSimpleName(), message);
    }

    public static void l(View view, String format, Object... params){
        l(view, format(format, params));
    }

    private static String format(String format, Object... params){
        return String.format(format, params);
    }
}
