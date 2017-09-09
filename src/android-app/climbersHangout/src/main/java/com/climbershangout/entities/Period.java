package com.climbershangout.entities;

import java.io.Serializable;

/**
 * Created by lyuboslav on 11/16/2016.
 */

public class Period implements Serializable {
    //region Members
    private PeriodType periodType;
    private int duration;
    private float currentTime;
    private Exercise exercise;
    private int set;
    private int totalSets;
    private int rep;
    private int totalReps;
    private int remainingTrainingTime;
    //endregion

    public Period(PeriodType type, int duration, int currentTime, Exercise exercise, int set, int totalSets, int rep, int totalReps) {
        setPeriodType(type);
        setDuration(duration);
        setCurrentTime(currentTime);
        setExercise(exercise);
        setSet(set);
        setTotalSets(totalSets);
        setRep(rep);
        setTotalReps(totalReps);
    }

    //region Properties
    public PeriodType getPeriodType() {
        return periodType;
    }

    private void setPeriodType(PeriodType periodType) {
        this.periodType = periodType;
    }

    public int getDuration() {
        return duration;
    }

    private void setDuration(int duration) {
        this.duration = duration;
    }

    public float getCurrentTime() { return currentTime; }

    public void setCurrentTime(float currentTime) { this.currentTime = currentTime; }

    public Exercise getExercise() {
        return exercise;
    }

    void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public int getSet() {
        return set;
    }

    public void setSet(int set) {
        this.set = set;
    }

    public int getTotalSets() {
        return totalSets;
    }

    private void setTotalSets(int totalSets) {
        this.totalSets = totalSets;
    }

    public int getRep() {
        return rep;
    }

    private void setRep(int rep) {
        this.rep = rep;
    }

    public int getTotalReps() {
        return totalReps;
    }

    private void setTotalReps(int totalReps) {
        this.totalReps = totalReps;
    }

    public int getRemainingTrainingTime() {
        return remainingTrainingTime;
    }

    public void setRemainingTrainingTime(int remainingTrainingTime) {
        this.remainingTrainingTime = remainingTrainingTime;
    }
    //endregion
}
