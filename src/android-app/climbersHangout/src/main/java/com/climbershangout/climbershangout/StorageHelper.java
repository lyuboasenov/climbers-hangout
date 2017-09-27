package com.climbershangout.climbershangout;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Created by lyuboslav on 9/24/2017.
 */

public class StorageHelper {

    //Members
    private Dictionary<String, File> storageDirs;
    private Context context;
    private static StorageHelper storageHelper;

    //Properties
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static StorageHelper getStorageHelper() {
        if (null == storageHelper) {
            throw new IllegalArgumentException("Context not initialized. Call the overload with context argument.");
        }
        return storageHelper;
    }

    public static StorageHelper getStorageManager(Context context) {
        if (null == storageHelper) {
            storageHelper = new StorageHelper();
            storageHelper.setContext(context);
        }
        return storageHelper;
    }

    //Constructor
    private StorageHelper(){
        storageDirs = new Hashtable();
    }

    //Methods
    public File createTempFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename = timeStamp;

        File tempFile = createFile(filename, ".tmp", "temp", false);

        return tempFile;
    }

    public File createFile(String filename, String extension, String path) throws IOException {
        return createFile(filename, extension, path, false);
    }

    public File createFile(String filename, String extension, String path, boolean external) throws IOException {
        // Create an image file name
        File storageDir = getStorageDir(path, external);

        File file = new File(storageDir, filename + extension);
        file.createNewFile();

        return file;
    }

    public File getStorageDir(String path, boolean external) throws FileNotFoundException {
        //Get external storage state to check for write perm
        String state = Environment.getExternalStorageState();

        File storageDir;
        //get storage directory
        if (Environment.MEDIA_MOUNTED.equals(state) && external) {
            // We can read and write the media
            storageDir = getContext().getExternalFilesDir(path);
        } else {
            storageDir = new File(getContext().getFilesDir(), path);
        }

        //create directory if does not exist
        if (!storageDir.exists()) {
            if (!storageDir.mkdirs())
                throw new FileNotFoundException("Couldn't make directory.");
            if (!storageDir.isDirectory())
                throw new FileNotFoundException("Couldn't verify directory.");
        }

        return storageDir;
    }
}
