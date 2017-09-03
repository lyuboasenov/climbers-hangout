package com.climbershangout.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.climbershangout.db.Contract;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by lyuboslav on 2/8/2017.
 */

public class Workout {
    private String id;
    private Date date;
    private WorkoutItem[] items;
    private String trainingId;
    private String trainingName;

    private Workout() {

    }

    public Workout(String id, Date date, WorkoutItem[] items, String trainingId, String trainingName) {
        setId(id);
        setDate(date);
        setItems(items);
        setTrainingId(trainingId);
        setTrainingName(trainingName);
    }

    public Workout(Date date, WorkoutItem[] items, String trainingId, String trainingName) {
        this(UUID.randomUUID().toString(), date, items, trainingId, trainingName);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public WorkoutItem[] getItems() {
        return items;
    }

    public void setItems(WorkoutItem[] items) {
        this.items = items;
    }

    public String getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(String trainingId) {
        this.trainingId = trainingId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public long getTotalTime() { long totalTime = 0; for(WorkoutItem item : getItems()) { totalTime += item.getDuration(); } return totalTime; }

    //region DB
    public void save(SQLiteDatabase db) {
        removeFromDB(db);

        ContentValues workoutContent = new ContentValues();
        workoutContent.put(Contract.WorkoutEntry.COLUMN_ID, getId());
        workoutContent.put(Contract.WorkoutEntry.COLUMN_TRAINING_ID, getTrainingId());
        workoutContent.put(Contract.WorkoutEntry.COLUMN_TRAINING_NAME, getTrainingName());
        workoutContent.put(Contract.WorkoutEntry.COLUMN_DATE, getDate().getTime());
        db.insert(Contract.WorkoutEntry.TABLE_NAME, null, workoutContent);

        for(int i = 0; i < getItems().length; i++) {
            getItems()[i].save(db, this, i);
        }
    }

    public static List<Workout> getAllWorkouts(SQLiteDatabase db) {
        String[] projection = {
                Contract.WorkoutEntry.COLUMN_ID,
                Contract.WorkoutEntry.COLUMN_TRAINING_ID,
                Contract.WorkoutEntry.COLUMN_TRAINING_NAME,
                Contract.WorkoutEntry.COLUMN_DATE
        };

        String sortOrder =
                Contract.WorkoutEntry.COLUMN_DATE + " DESC";

        Cursor cursor = db.query(
                Contract.WorkoutEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List<Workout> returnList = new ArrayList<>();
        while(cursor.moveToNext()){
            Workout workout = new Workout();
            workout.setId(cursor.getString(0));
            workout.setTrainingId(cursor.getString(1));
            workout.setTrainingName(cursor.getString(2));
            workout.setDate(new Date(cursor.getLong(3)));

            WorkoutItem[] items = WorkoutItem.getWorkoutItemsByWorkoutId(db, workout);
            workout.setItems(items);
            returnList.add(workout);
        }

        return returnList;
    }

    public void removeFromDB(SQLiteDatabase db) {
        db.delete(Contract.WorkoutItemEntry.TABLE_NAME, Contract.WorkoutItemEntry.COLUMN_WORKOUT_ID + " = ?", new String[] { getId() });
        db.delete(Contract.WorkoutEntry.TABLE_NAME, Contract.WorkoutEntry.COLUMN_ID + " = ?", new String[] { getId() });
    }

    public static int getWorkoutCountForPeriod(SQLiteDatabase db, Date from, Date to) {
        Cursor countCursor = db.rawQuery("SELECT COUNT(*) FROM " +Contract.WorkoutEntry.TABLE_NAME + " WHERE " + Contract.WorkoutEntry.COLUMN_DATE + " BETWEEN ? AND ?", new String[] { Long.toString(from.getTime()), Long.toString(to.getTime()) });
        countCursor.moveToFirst();
        return countCursor.getInt(0);
    }
    //endregion
}
