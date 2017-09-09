package com.climbershangout.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

import com.climbershangout.db.Contract;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lyuboslav on 11/16/2016.
 */

public class Training implements Parcelable {
    //region Members
    private String id;
    private String name;
    private String description;
    private Exercise[] exercises;
    private int preparationDuration;
    private List<ITimerListener> timerListenerList = new ArrayList<>();
    //private CountDownTimer timer;
    private Timer timer;
    private Period[] periods;
    private int currentPeriodIndex;
    private Date currentPeriodStart;
    private float currentTime;
    private float currentTimeOveral;
    private boolean isPaused = false;
    private Date pauseStart;
    private long pauseDuration;
    //endregion

    public Training() {

    }

    //region Parcelable
    private Training(Parcel source) {
        id = source.readString();
        name = source.readString();
        description = source.readString();
        preparationDuration = source.readInt();
        currentTime = source.readInt();
        currentPeriodIndex = source.readInt();
        isPaused = source.readByte() == 1;
        int exerciseCount = source.readInt();
        exercises = new Exercise[exerciseCount];
        source.readTypedArray(exercises, Exercise.CREATOR);
        periods = getPeriods();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeInt(preparationDuration);
        parcel.writeFloat(currentTime);
        parcel.writeInt(currentPeriodIndex);
        parcel.writeByte((byte) 0);
        parcel.writeInt(exercises.length);
        parcel.writeTypedArray(exercises, i);
    }

    public static final Parcelable.Creator<Training> CREATOR
            = new Parcelable.Creator<Training>() {
        public Training createFromParcel(Parcel in) {
            return new Training(in);
        }

        public Training[] newArray(int size) {
            return new Training[size];
        }
    };
    //endregion

    //region Properties
    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }

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

    public Exercise[] getExercises() {
        return exercises;
    }

    public void setExercises(Exercise[] exercises) {
        this.exercises = exercises;
    }

    public int getPreparationDuration() {
        return preparationDuration;
    }

    public void setPreparationDuration(int preparationDuration) {
        this.preparationDuration = preparationDuration;
    }

    public void addTimerListener(ITimerListener listener) { timerListenerList.add(listener); }

    private void invokeTimerListenersOnFinish(boolean isCompleted) {
        if(null != timerListenerList) {
            for (ITimerListener listener : timerListenerList) {
                listener.OnFinish(isCompleted);
            }
        }
    }

    private void invokeTimerListenersOnTick(Period period, float currentTime) {
        if(null != timerListenerList) {
            for (ITimerListener listener : timerListenerList) {
                listener.OnTick(period, currentTime);
            }
        }
    }
    //endregion

    //region Actions
    public void startTraining() {

        if(null == periods) {
            periods = getPeriods();
        }

        currentPeriodIndex = 0;
        currentTime = 0;
        currentPeriodStart = new Date();

        startTimer();
    }

    private void startTimer() {
        if(null == timer) {
            timer = new Timer(true);
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timerTick();
            }
        }, 1, 1);
    }

    public void stopTraining() {
        if(timer != null) {
            timer.cancel();
            timer = null;
            currentPeriodIndex = 0;
            currentTime = 0;
        }
        invokeTimerListenersOnFinish(false);
    }

    public void skipExercise() {
        pauseTraining();
        Exercise exercise = periods[currentPeriodIndex].getExercise();

        for (; currentPeriodIndex < periods.length; currentPeriodIndex++) {
            if(periods[currentPeriodIndex].getExercise() != exercise)
                break;
        }
        resumeTraining();
    }

    public void skipSet(){
        pauseTraining();
        Exercise exercise = periods[currentPeriodIndex].getExercise();
        int set = periods[currentPeriodIndex].getSet();

        if(exercise.isInfiniteSets()){
            while(periods[currentPeriodIndex].getSet() == set
                    && periods[currentPeriodIndex].getExercise() == exercise) {
                currentPeriodIndex++;
                if(currentPeriodIndex >= periods.length)
                    break;
            }
            currentPeriodIndex--;
        } else {
            for (; currentPeriodIndex < periods.length; currentPeriodIndex++) {
                if (periods[currentPeriodIndex].getSet() != set
                        || periods[currentPeriodIndex].getExercise() != exercise)
                    break;
            }
        }
        resumeTraining();
    }

    public void pauseTraining() {
        setPaused(true);
    }

    public void resumeTraining() { setPaused(false); }
    //endregion

    //region DB
    public void save(SQLiteDatabase db) {
        removeFromDB(db);

        ContentValues trainingValues = new ContentValues();
        trainingValues.put(Contract.TrainingEntry.COLUMN_ID, getId());
        trainingValues.put(Contract.TrainingEntry.COLUMN_NAME, getName());
        trainingValues.put(Contract.TrainingEntry.COLUMN_DESCRIPTION, getDescription());
        trainingValues.put(Contract.TrainingEntry.COLUMN_PREPARATION, getPreparationDuration());
        db.insert(Contract.TrainingEntry.TABLE_NAME, null, trainingValues);

        for(int i = 0; i < getExercises().length; i++) {
            getExercises()[i].save(db, this, i);
        }
    }

    public static List<Training> getAllTrainings(SQLiteDatabase db) {
        String[] projection = {
            Contract.TrainingEntry.COLUMN_ID,
            Contract.TrainingEntry.COLUMN_NAME,
            Contract.TrainingEntry.COLUMN_DESCRIPTION,
            Contract.TrainingEntry.COLUMN_PREPARATION
        };

        String sortOrder =
                Contract.TrainingEntry.COLUMN_NAME + " ASC";

        Cursor cursor = db.query(
                Contract.TrainingEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List<Training> returnList = new ArrayList<>();
        while(cursor.moveToNext()){
            Training training = new Training();
            training.setId(cursor.getString(0));
            training.setName(cursor.getString(1));
            training.setDescription(cursor.getString(2));
            training.setPreparationDuration(cursor.getInt(3));

            List<Exercise> exercises = UniformTimedExercise.getExercisesByTraining(db, training);
            training.setExercises(exercises.toArray(new Exercise[exercises.size()]));
            returnList.add(training);
        }

        return returnList;
    }

    public void removeFromDB(SQLiteDatabase db) {
        db.delete(Contract.UniformTimedExerciseEntry.TABLE_NAME, Contract.UniformTimedExerciseEntry.COLUMN_TRAINING_ID + " = ?", new String[] { getId() });
        db.delete(Contract.TrainingEntry.TABLE_NAME, Contract.TrainingEntry.COLUMN_ID + " = ?", new String[] { getId() });
    }
    //endregion

    //region Methods
    private void timerTick(){
        if(!isPaused()) {
            float interval = (float)(new Date().getTime() - currentPeriodStart.getTime() - pauseDuration) / 1000;
            currentTime = interval;
            float currentTimeOveralInternal = currentTimeOveral;

            if (isTrainingFinished()){
                invokeTimerListenersOnFinish(currentPeriodIndex == periods.length - 1);
                return;
            }

            int duration = periods[currentPeriodIndex].getDuration();
            if (currentTime >= duration) {
                Exercise exercise = periods[currentPeriodIndex].getExercise();
                if (null != exercise && exercise.isInfiniteSets()
                        && (periods.length <= currentPeriodIndex + 1
                        ||exercise != periods[currentPeriodIndex + 1].getExercise())) {
                    for(; currentPeriodIndex >= 0; currentPeriodIndex--){
                        if(currentPeriodIndex == 0) break;
                        if(exercise != periods[currentPeriodIndex].getExercise()) {
                            currentPeriodIndex++;
                            break;
                        }
                        periods[currentPeriodIndex].setSet(periods[currentPeriodIndex].getSet() + 1);
                    }
                }

                currentTimeOveral += interval;
                currentPeriodIndex++;
                currentTime = 0;
                currentPeriodStart = new Date();
                pauseStart = null;
                pauseDuration = 0;
                if (isTrainingFinished()) {
                    timer.cancel();
                    invokeTimerListenersOnFinish(currentPeriodIndex == periods.length - 1);
                    return;
                }
            }

            Period currentPeriod = periods[currentPeriodIndex];
            currentPeriod.setCurrentTime(currentTime);

            invokeTimerListenersOnTick(currentPeriod, currentTimeOveralInternal + interval);
        }
    }

    private boolean isTrainingFinished() {
        boolean returnValue = false;
        if(currentPeriodIndex == periods.length - 1
                && !periods[currentPeriodIndex].getExercise().isInfiniteSets()) {
            timer.cancel();
            returnValue = true;
        } else if(currentPeriodIndex >= periods.length) {
            timer.cancel();
            returnValue = true;
        }
        return returnValue;
    }

    private boolean isPaused() {
        return isPaused;
    }

    private void setPaused(boolean paused) {

        if(null == periods) {
            periods = getPeriods();
        }

        if(paused) {
            pauseStart = new Date();
        } else if (null != pauseStart) {
            pauseDuration += new Date().getTime() - pauseStart.getTime();
            pauseStart = null;
        }
        isPaused = paused;
    }

    private Period[] getPeriods(){
        List<Period> periodList = new ArrayList();

        periodList.add(new Period(PeriodType.Prep, getPreparationDuration(), 0, null, 1, 1, 1, 1));

        for (int i = 0; i < getExercises().length; i++) {
            Exercise exercise = getExercises()[i];
            Period[] exercisePeriods = exercise.getPeriods();
            if(!exercise.isInfiniteSets()
                    && i < getExercises().length - 1){
                exercisePeriods[exercisePeriods.length - 1].setExercise(getExercises()[i + 1]);
            }
            Collections.addAll(periodList, exercisePeriods);
        }

        Period[] periodArray = periodList.toArray(new Period[periodList.size()]);

        int remainingTime = 0;
        for(int i = periodArray.length - 1; i >= 0; i--) {
            if(remainingTime == -1 || (null != periodArray[i].getExercise() && periodArray[i].getExercise().isInfiniteSets())) {
                remainingTime = -1;
            } else {
                remainingTime += periodArray[i].getDuration();
            }
            periodArray[i].setRemainingTrainingTime(remainingTime);
        }

        return periodList.toArray(new Period[periodList.size()]);
    }

    public int getTotalDuration() {
        Period[] periods = getPeriods();
        int totalDuration = 0;

        if (null != periods && periods.length > 0)
            totalDuration = periods[0].getRemainingTrainingTime();

        return totalDuration;
    }
    //endregion

    //region JSON Serialization
    public static Training fromJson(JSONObject jsonObj) throws JSONException {
        Training training = new Training();
        training.setId(jsonObj.getString("id"));
        training.setName(jsonObj.getString("name"));
        training.setDescription(jsonObj.getString("description"));
        training.setPreparationDuration(jsonObj.getInt("preparationDuration"));
        training.setExercises(Exercise.fromJson(jsonObj.getJSONArray("exercises")));

        return training;
    }
    //endregion
}
