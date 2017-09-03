package com.climbershangout.db;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import com.climbershangout.climbershangout.R;
import com.climbershangout.entities.Training;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyuboslav on 11/28/2016.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 10;
    public static final String DATABASE_NAME = "climbers_hangout.db";
    private Context context;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        setContext(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Contract.TrainingEntry.CREATE_ENTRY);
        db.execSQL(Contract.UniformTimedExerciseEntry.CREATE_ENTRY);

        db.execSQL(Contract.WorkoutEntry.CREATE_ENTRY);
        db.execSQL(Contract.WorkoutItemEntry.CREATE_ENTRY);

        initializeDBValues(db);
    }

    private void initializeDBValues(SQLiteDatabase db) {
        List<Training> trainings = getDefaultEmbeddedTrainings();

        for(Training training : trainings) {
            training.save(db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
        db.execSQL(Contract.UniformTimedExerciseEntry.DELETE_ENTRY);
        db.execSQL(Contract.TrainingEntry.DELETE_ENTRY);
        db.execSQL(Contract.WorkoutItemEntry.DELETE_ENTRY);
        db.execSQL(Contract.WorkoutEntry.DELETE_ENTRY);
        */

        if(oldVersion > 0 && oldVersion < 8){
            db.execSQL(Contract.UniformTimedExerciseEntry.DELETE_ENTRY);
            db.execSQL(Contract.TrainingEntry.DELETE_ENTRY);

            db.execSQL(Contract.TrainingEntry.CREATE_ENTRY);
            db.execSQL(Contract.UniformTimedExerciseEntry.CREATE_ENTRY);

            initializeDBValues(db);

        }

        if (newVersion <= 8) {
            db.execSQL(Contract.WorkoutEntry.CREATE_ENTRY);
            db.execSQL(Contract.WorkoutItemEntry.CREATE_ENTRY);
        }

        if (newVersion <= 10) {
            db.execSQL(Contract.WorkoutItemEntry.DELETE_ENTRY);
            db.execSQL(Contract.WorkoutEntry.DELETE_ENTRY);

            db.execSQL(Contract.WorkoutEntry.CREATE_ENTRY);
            db.execSQL(Contract.WorkoutItemEntry.CREATE_ENTRY);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private List<Training> getDefaultEmbeddedTrainings() {
        List<Training> trainings = new ArrayList<>();
        try {
            InputStream is = getContext().getResources().openRawResource(R.raw.default_trainings);
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            JSONArray jsonArray = new JSONArray(writer.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                trainings.add(Training.fromJson(jsonArray.getJSONObject(i)));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

        return trainings;
    }

    private Context getContext() {
        return context;
    }

    private void setContext(Context context) {
        this.context = context;
    }
}
