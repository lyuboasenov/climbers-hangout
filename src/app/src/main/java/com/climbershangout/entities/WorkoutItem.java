package com.climbershangout.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.climbershangout.db.Contract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyuboslav on 2/5/2017.
 */

public class WorkoutItem {
    private WorkoutItemType type;
    private float duration;
    private float load;

    public WorkoutItem() {

    }

    public WorkoutItem(WorkoutItemType type, float duration, float load) {
        setType(type);
        setLoad(load);
        setDuration(duration);
    }

    public WorkoutItemType getType() {
        return type;
    }

    public void setType(WorkoutItemType type) {
        this.type = type;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public float getLoad() {
        return load;
    }

    public void setLoad(float load) {
        this.load = load;
    }

    @Override
    public String toString() {
        return String.format("{\"%1$s\" %2$s (%4$f) -> %3$f}", (getType() == WorkoutItemType.WORK ? "Work" : "Rest"), formatTime(getDuration()), getLoad(), getDuration());
    }

    private String formatTime(float duration) {
        int minutes = (int)duration / 60;
        int seconds = (int)duration % 60;
        int milliseconds = (int)(duration - (int)duration) * 1000;

        return String.format("%1$02d:%2$02d.%3$d", minutes, seconds, milliseconds);
    }

    public static WorkoutItem[] getWorkoutItemsByWorkoutId(SQLiteDatabase db, Workout workout) {
        String[] projection = {
                Contract.WorkoutItemEntry.COLUMN_WORKOUT_ID,
                Contract.WorkoutItemEntry.COLUMN_DURATION,
                Contract.WorkoutItemEntry.COLUMN_LOAD,
                Contract.WorkoutItemEntry.COLUMN_TYPE
        };

        String sortOrder =
                Contract.WorkoutItemEntry.COLUMN_ORDER + " ASC";

        Cursor cursor = db.query(
                Contract.WorkoutItemEntry.TABLE_NAME,
                projection,
                Contract.WorkoutItemEntry.COLUMN_WORKOUT_ID + " = ?",
                new String[] { workout.getId() },
                null,
                null,
                sortOrder
        );

        List<WorkoutItem> returnList = new ArrayList<>();
        while(cursor.moveToNext()){
            returnList.add(new WorkoutItem(WorkoutItemType.values()[cursor.getInt(3)], cursor.getFloat(1), cursor.getFloat(2)));
        }

        return returnList.toArray(new WorkoutItem[returnList.size()]);
    }

    public void save(SQLiteDatabase db, Workout workout, int index) {
        ContentValues workoutItemValues = new ContentValues();
        workoutItemValues.put(Contract.WorkoutItemEntry.COLUMN_WORKOUT_ID, workout.getId());
        workoutItemValues.put(Contract.WorkoutItemEntry.COLUMN_DURATION, getDuration());
        workoutItemValues.put(Contract.WorkoutItemEntry.COLUMN_LOAD, getLoad());
        workoutItemValues.put(Contract.WorkoutItemEntry.COLUMN_TYPE, getType().ordinal());
        workoutItemValues.put(Contract.WorkoutItemEntry.COLUMN_ORDER, index);

        db.insert(Contract.WorkoutItemEntry.TABLE_NAME, null, workoutItemValues);
    }

    public enum WorkoutItemType {
        REST,
        WORK
    }
}


