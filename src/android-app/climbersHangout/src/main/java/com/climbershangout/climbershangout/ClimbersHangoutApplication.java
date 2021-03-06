package com.climbershangout.climbershangout;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by lyuboslav on 1/29/2017.
 */

public class ClimbersHangoutApplication extends Application {

    //Members
    private static ClimbersHangoutApplication current;
    private User user;
    private StorageHelper storageHelper;

    //Properties
    public static ClimbersHangoutApplication getCurrent() {
        return current;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public StorageHelper getStorageHelper() {
        return storageHelper;
    }

    public void setStorageHelper(StorageHelper storageHelper) {
        this.storageHelper = storageHelper;
    }


    //Methods
    @Override
    public void onCreate() {
        super.onCreate();

        current = this;

        // Setup handler for uncaught exceptions.
        Thread.setDefaultUncaughtExceptionHandler (new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException (Thread thread, Throwable e) {
                handleUncaughtException (thread, e);
            }
        });

        BoardManager.getBoardManager().initialize(this);
        setUser(User.getUser(this));
        setStorageHelper(StorageHelper.getStorageManager(this));
    }

    public boolean isUIThread(){
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    public void handleUncaughtException(Thread thread, final Throwable e) {
        e.printStackTrace(); // not all Android versions will print the stack trace automatically
        Log.e("Climbers hangout", e.getMessage(), e);

        if(isUIThread()) {
            invokeLogActivity(e);
        }else{  //handle non UI thread throw uncaught exception
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    invokeLogActivity(e);
                }
            });
        }
    }

    private void invokeLogActivity(Throwable e){
        Intent intent = new Intent ();
        intent.setAction ("com.climbershangout.SEND_LOG"); // see step 5.
        intent.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK); // required when starting from Application

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        intent.putExtra(SendLogActivity.MESSAGE, e.getMessage() + "\n" + sw.toString());
        startActivity (intent);

        System.exit(1); // kill off the crashed app
    }
}
