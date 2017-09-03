package com.climbershangout.entities;

import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

import com.climbershangout.db.DbHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyuboslav on 11/16/2016.
 */
public abstract class Exercise implements Parcelable {
    //region Members
    private String name;
    private String description;
    private int setCount;
    private boolean infiniteSets = false;
    //endregion

    protected Exercise() {}

    //region Parcelable
    protected Exercise(Parcel source) {
        setName(source.readString());
        setDescription(source.readString());
        setSetCount(source.readInt());
        setInfiniteSets(source.readByte() == 1);
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getName());
        parcel.writeString(getDescription());
        parcel.writeInt(getSetCount());
        parcel.writeByte((byte) (isInfiniteSets() ? 1 : 0));
    }

    public static final Parcelable.Creator<Exercise> CREATOR
            = new Parcelable.Creator<Exercise>() {
        public Exercise createFromParcel(Parcel in) {
            return new UniformTimedExercise(in);
        }

        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };
    //endregion

    //region Properties
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSetCount() { return setCount; }

    public void setSetCount(int setCount) { this.setCount = setCount; }

    public boolean isInfiniteSets() {
        return infiniteSets;
    }

    public void setInfiniteSets(boolean infiniteSets) {
        this.infiniteSets = infiniteSets;
    }
    //endregion

    public abstract Period[] getPeriods();

    //region JSON Serialization
    public static Exercise[] fromJson(JSONArray jsonExercises) throws JSONException {
        List<Exercise> exercises = new ArrayList<Exercise>();
        for (int i = 0; i < jsonExercises.length(); i++) {
            exercises.add(fromJson(jsonExercises.getJSONObject(i)));
        }
        return exercises.toArray(new Exercise[exercises.size()]);
    }

    private static Exercise fromJson(JSONObject jsonObj) throws JSONException {
        //TODO: Determine exact type
        UniformTimedExercise exercise = new UniformTimedExercise();
        exercise.setName(jsonObj.getString("name"));
        exercise.setDescription(jsonObj.getString("description"));
        exercise.setRepetitionCount(jsonObj.getInt("repetitionCount"));
        exercise.setWorkDuration(jsonObj.getInt("workDuration"));
        exercise.setRestDuration(jsonObj.getInt("restDuration"));
        exercise.setPauseDuration(jsonObj.getInt("pauseDuration"));
        exercise.setSetCount(jsonObj.getInt("setCount"));
        exercise.setInfiniteSets(jsonObj.getInt("setInfinite") == 1);

        return exercise;
    }
    //endregion

    //region DB
    public abstract void save(SQLiteDatabase db, Training training, int order);
    //endregion


}
