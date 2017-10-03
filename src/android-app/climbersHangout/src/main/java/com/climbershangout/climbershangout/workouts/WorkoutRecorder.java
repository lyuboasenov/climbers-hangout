package com.climbershangout.climbershangout.workouts;

import com.climbershangout.climbershangout.BoardManager;
import com.climbershangout.climbershangout.LogHelper;
import com.climbershangout.entities.ITimerListener;
import com.climbershangout.entities.Period;
import com.climbershangout.entities.PeriodType;
import com.climbershangout.entities.Training;
import com.climbershangout.entities.Workout;
import com.climbershangout.entities.WorkoutItem;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lyuboslav on 2/5/2017.
 */

public class WorkoutRecorder implements BoardManager.IBoardCallback, ITimerListener {

    private List<WorkoutItem> recordings = new ArrayList<>();
    private WorkoutItem.WorkoutItemType lastWorkoutItemType;
    private WorkoutItem currentWorkoutItem;
    private float currentWorkoutItemStart;
    private float currentTime;
    private float lastLoad;
    private Training training;

    public WorkoutRecorder(Training training) {
        this.training = training;
    }

    @Override
    public void onReceivedData(byte[] bytes) {
        String data = "";
        try {
            data = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        float value = 0f;

        if (!data.isEmpty() && !data.trim().isEmpty()) {
            try {
                value = Float.parseFloat(data);
            }catch (NumberFormatException ex){
                value = 0f;
            }
        }

        if(Math.abs(value - lastLoad) > 1000) {
            WorkoutItem.WorkoutItemType newWorkoutItemType = WorkoutItem.WorkoutItemType.REST;
            if(value > 40000f) {
                //work
                newWorkoutItemType = WorkoutItem.WorkoutItemType.WORK;
            }
            saveCurrentWorkoutItem(this.currentTime);

            currentWorkoutItem = new WorkoutItem();
            currentWorkoutItemStart = currentTime;
            lastWorkoutItemType = newWorkoutItemType;
            lastLoad = value;
        }
    }

    @Override
    public void onBoardAttachedDetached(boolean boardAttachedDetached) {

    }

    @Override
    public void OnTick(Period period, float currentTime) {
        this.currentTime = currentTime;

        WorkoutItem.WorkoutItemType currentWorkoutItemType = translatePeriodType(period.getPeriodType());
        if (currentWorkoutItemType != lastWorkoutItemType) {
            if (null != currentWorkoutItem) {
                saveCurrentWorkoutItem(currentTime);
            }

            currentWorkoutItem = new WorkoutItem();
            currentWorkoutItemStart = currentTime;
            lastWorkoutItemType = currentWorkoutItemType;
        }
    }

    @Override
    public void OnFinish(boolean isCompleted) {
        saveCurrentWorkoutItem(this.currentTime);
    }

    public Workout getWorkout() {
        return new Workout(new Date(), recordings.toArray(new WorkoutItem[recordings.size()]), training.getId(), training.getName());
    }

    private void saveCurrentWorkoutItem(float currentTime){
        currentWorkoutItem.setType(lastWorkoutItemType);
        currentWorkoutItem.setDuration(currentTime - currentWorkoutItemStart);
        currentWorkoutItem.setLoad(lastLoad);

        LogHelper.l("Workout.Item.Save", currentWorkoutItem.toString());

        recordings.add(currentWorkoutItem);
    }

    private WorkoutItem.WorkoutItemType translatePeriodType(PeriodType type) {
        WorkoutItem.WorkoutItemType translatedType = WorkoutItem.WorkoutItemType.REST;
        if(type == PeriodType.Work) {
            translatedType = WorkoutItem.WorkoutItemType.WORK;
        }

        return translatedType;
    }
}
