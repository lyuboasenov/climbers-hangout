package com.climbershangout.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

import com.climbershangout.db.Contract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyuboslav on 11/16/2016.
 */
public class UniformTimedExercise extends Exercise {
    //region Members
    private int repetitionCount;
    private int workDuration;
    private int restDuration;
    private int pauseDuration;
    //endregion

    public UniformTimedExercise() {}

    //region Parcelable
    UniformTimedExercise(Parcel source) {
        super(source);

        repetitionCount = source.readInt();
        workDuration = source.readInt();
        restDuration = source.readInt();
        pauseDuration = source.readInt();
    }

    public static final Parcelable.Creator<UniformTimedExercise> CREATOR
            = new Parcelable.Creator<UniformTimedExercise>() {
        public UniformTimedExercise createFromParcel(Parcel in) {
            return new UniformTimedExercise(in);
        }

        public UniformTimedExercise[] newArray(int size) {
            return new UniformTimedExercise[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);

        parcel.writeInt(repetitionCount);
        parcel.writeInt(workDuration);
        parcel.writeInt(restDuration);
        parcel.writeInt(pauseDuration);
    }
    //endregion

    //region Properties
    public int getWorkDuration() {
        return workDuration;
    }

    public void setWorkDuration(int workDuration) {
        this.workDuration = workDuration;
    }

    public int getRestDuration() {
        return restDuration;
    }

    public void setRestDuration(int restDuration) {
        this.restDuration = restDuration;
    }

    public int getRepetitionCount() {
        return repetitionCount;
    }

    public void setRepetitionCount(int repetitionCount) {
        this.repetitionCount = repetitionCount;
    }

    public int getPauseDuration() {
        return pauseDuration;
    }

    public void setPauseDuration(int pauseDuration) {
        this.pauseDuration = pauseDuration;
    }
    //endregion

    //region Actions
    @Override
    public Period[] getPeriods() {
        List<Period> periodList = new ArrayList<>();

        for (int set = 0; set < (isInfiniteSets() ? 1 : getSetCount()); set++) {
            for (int rep = 0; rep < getRepetitionCount(); rep++) {
                if(rep > 0) {
                    periodList.add(new Period(PeriodType.Rest, getRestDuration(), 0, this, set + 1, getSetCount(), rep + 1, getRepetitionCount()));
                }
                periodList.add(new Period(PeriodType.Work, getWorkDuration(), 0, this, set + 1, getSetCount(), rep + 1, getRepetitionCount()));
            }
            periodList.add(new Period(PeriodType.Pause, getPauseDuration(), 0, this, set + 1, getSetCount(), 0, getRepetitionCount()));
        }

        return periodList.toArray(new Period[periodList.size()]);
    }
    //endregion

    //region DB
    @Override
    public void save(SQLiteDatabase db, Training training, int order) {
        ContentValues exerciseValues = new ContentValues();
        exerciseValues.put(Contract.UniformTimedExerciseEntry.COLUMN_TRAINING_ID, training.getId());
        exerciseValues.put(Contract.UniformTimedExerciseEntry.COLUMN_NAME, getName());
        exerciseValues.put(Contract.UniformTimedExerciseEntry.COLUMN_DESCRIPTION, getDescription());
        exerciseValues.put(Contract.UniformTimedExerciseEntry.COLUMN_SET_COUNT, getSetCount());
        exerciseValues.put(Contract.UniformTimedExerciseEntry.COLUMN_INFINITE_SET, isInfiniteSets());
        exerciseValues.put(Contract.UniformTimedExerciseEntry.COLUMN_ORDER, order);
        exerciseValues.put(Contract.UniformTimedExerciseEntry.COLUMN_PAUSE, getPauseDuration());
        exerciseValues.put(Contract.UniformTimedExerciseEntry.COLUMN_REPS, getRepetitionCount());
        exerciseValues.put(Contract.UniformTimedExerciseEntry.COLUMN_WORK, getWorkDuration());
        exerciseValues.put(Contract.UniformTimedExerciseEntry.COLUMN_REST, getRestDuration());

        db.insert(Contract.UniformTimedExerciseEntry.TABLE_NAME, null, exerciseValues);
    }

    public static List<Exercise> getExercisesByTraining(SQLiteDatabase db, Training training) {
        String[] projection = {
            Contract.UniformTimedExerciseEntry.COLUMN_NAME,
            Contract.UniformTimedExerciseEntry.COLUMN_DESCRIPTION,
            Contract.UniformTimedExerciseEntry.COLUMN_SET_COUNT,
            Contract.UniformTimedExerciseEntry.COLUMN_INFINITE_SET,
            Contract.UniformTimedExerciseEntry.COLUMN_PAUSE,
            Contract.UniformTimedExerciseEntry.COLUMN_REPS,
            Contract.UniformTimedExerciseEntry.COLUMN_REST,
            Contract.UniformTimedExerciseEntry.COLUMN_WORK
        };

        String sortOrder =
                Contract.UniformTimedExerciseEntry.COLUMN_ORDER + " ASC";

        Cursor cursor = db.query(
                Contract.UniformTimedExerciseEntry.TABLE_NAME,
                projection,
                Contract.UniformTimedExerciseEntry.COLUMN_TRAINING_ID + " = ?",
                new String[] { training.getId() },
                null,
                null,
                sortOrder
        );

        List<Exercise> returnList = new ArrayList<>();
        while(cursor.moveToNext()){
            UniformTimedExercise exercise = new UniformTimedExercise();
            exercise.setName(cursor.getString(0));
            exercise.setDescription(cursor.getString(1));
            exercise.setSetCount(cursor.getInt(2));
            exercise.setInfiniteSets(cursor.getInt(3) == 1);
            exercise.setPauseDuration(cursor.getInt(4));
            exercise.setRepetitionCount(cursor.getInt(5));
            exercise.setRestDuration(cursor.getInt(6));
            exercise.setWorkDuration(cursor.getInt(7));

            returnList.add(exercise);
        }

        return returnList;
    }
    //endregion
}
