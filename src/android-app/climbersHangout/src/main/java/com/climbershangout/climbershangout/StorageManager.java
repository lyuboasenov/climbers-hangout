package com.climbershangout.climbershangout;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * Created by lyuboslav on 9/24/2017.
 */

public class StorageManager {

    //Members
    private Dictionary<String, File> storageDirs;
    private Context context;
    private static StorageManager storageManager;

    //Properties
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static StorageManager getStorageManager() {
        if (null == storageManager) {
            throw new IllegalArgumentException("Context not initialized. Call the overload with context argument.");
        }
        return storageManager;
    }

    public static StorageManager getStorageManager(Context context) {
        if (null == storageManager) {
            storageManager = new StorageManager();
            storageManager.setContext(context);
        }
        return storageManager;
    }

    //Constructor
    private StorageManager(){
        storageDirs = new Hashtable();
    }

    //Methods
    public File createTempFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename = timeStamp;

        File tempFile = createFile(filename, "temp", ".tmp");

        return tempFile;
    }

    public File createFile(String filename, String storageType, String extension) throws IOException {
        // Create an image file name
        File storageDir = getStorageDir(storageType);

        File image = File.createTempFile(
                filename,        /* prefix */
                extension,       /* suffix */
                storageDir       /* directory */
        );

        return image;
    }

    public File getStorageDir(String storageType) throws FileNotFoundException {
        File storageDir = storageDirs.get(storageType);
        if (storageDir == null) {
            //Get external storage state to check for write perm
            String state = Environment.getExternalStorageState();

            //storage type for external storage has and _ext suffix
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                storageType += "_ext";
            }

            //get storage path from @xml/file_paths
            XmlResourceParser parser = getContext().getResources().getXml(R.xml.file_paths);
            XPath xpath = XPathFactory.newInstance().newXPath();
            String expression = "//paths/files-path[@name='" + storageType + "']/@path";
            String storageDirPath = "temp";
            try {
                storageDirPath = (String) xpath.evaluate(expression, parser, XPathConstants.STRING);
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }

            //get storage directory
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                // We can read and write the media
                storageDir = getContext().getExternalFilesDir(storageDirPath);
            } else {
                storageDir = new File(getContext().getFilesDir(), storageDirPath);
            }

            //create directory if does not exist
            if (!storageDir.exists()) {
                if (!storageDir.mkdirs())
                    throw new FileNotFoundException("Couldn't make directory.");
                if (!storageDir.isDirectory())
                    throw new FileNotFoundException("Couldn't verify directory.");
            }

            //cache storage dir
            storageDirs.put(storageType, storageDir);
        }

        return storageDir;
    }
}
