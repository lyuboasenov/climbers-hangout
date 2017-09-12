package com.climbershangout.db;

import android.provider.BaseColumns;

/**
 * Created by lyuboslav on 11/28/2016.
 */

public final class Contract {
    public static class TrainingEntry implements BaseColumns {
        public static final String TABLE_NAME = "training";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_PREPARATION = "preparationDuration";

        public static final String CREATE_ENTRY = "CREATE TABLE " + TABLE_NAME + " ( " +
                COLUMN_ID + " TEXT PRIMARY KEY, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_PREPARATION + " INTEGER " +
                ")";

        public static final String DELETE_ENTRY = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static class UniformTimedExerciseEntry implements BaseColumns {
        public static final String TABLE_NAME = "uniform_timed_exercise";
        public static final String COLUMN_TRAINING_ID = "train_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_SET_COUNT = "set_count";
        public static final String COLUMN_INFINITE_SET = "infinite_sets";
        public static final String COLUMN_REPS = "reps";
        public static final String COLUMN_WORK = "work";
        public static final String COLUMN_REST = "rest";
        public static final String COLUMN_PAUSE = "pause";
        public static final String COLUMN_ORDER = "ordering";

        public static final String CREATE_ENTRY = "CREATE TABLE " + TABLE_NAME + " ( " +
                COLUMN_TRAINING_ID + " TEXT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_SET_COUNT + " INTEGER, " +
                COLUMN_INFINITE_SET + " INTEGER, " +
                COLUMN_REPS + " INTEGER, " +
                COLUMN_WORK + " INTEGER, " +
                COLUMN_REST + " INTEGER, " +
                COLUMN_PAUSE + " INTEGER, " +
                COLUMN_ORDER + " INTEGER " +
                ")";

        public static final String DELETE_ENTRY = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static class WorkoutEntry implements BaseColumns {
        public static final String TABLE_NAME = "workout";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TRAINING_ID = "training_id";
        public static final String COLUMN_TRAINING_NAME = "training_name";
        public static final String COLUMN_DATE = "date";

        public static final String CREATE_ENTRY = "CREATE TABLE " + TABLE_NAME + " ( " +
                COLUMN_ID + " TEXT PRIMARY KEY, " +
                COLUMN_TRAINING_ID + " TEXT, " +
                COLUMN_TRAINING_NAME + " TEXT, " +
                COLUMN_DATE + " INTEGER " +
                ")";

        public static final String DELETE_ENTRY = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static class WorkoutItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "workout_item";
        public static final String COLUMN_WORKOUT_ID = "workout_id";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_DURATION = "duration";
        public static final String COLUMN_LOAD = "load";
        public static final String COLUMN_ORDER = "ordering";

        public static final String CREATE_ENTRY = "CREATE TABLE " + TABLE_NAME + " ( " +
                COLUMN_WORKOUT_ID + " TEXT, " +
                COLUMN_TYPE + " INT, " +
                COLUMN_LOAD + " REAL, " +
                COLUMN_DURATION + " REAL, " +
                COLUMN_ORDER + " INT " +
                ")";

        public static final String DELETE_ENTRY = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
