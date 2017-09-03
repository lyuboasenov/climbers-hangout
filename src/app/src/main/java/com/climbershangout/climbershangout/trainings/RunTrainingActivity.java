package com.climbershangout.climbershangout.trainings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.climbershangout.climbershangout.BaseActivity;
import com.climbershangout.climbershangout.BoardManager;
import com.climbershangout.climbershangout.R;
import com.climbershangout.climbershangout.settings.SettingsKeys;
import com.climbershangout.climbershangout.workouts.WorkoutRecorder;
import com.climbershangout.db.DbHelper;
import com.climbershangout.entities.Exercise;
import com.climbershangout.entities.ITimerListener;
import com.climbershangout.entities.Period;
import com.climbershangout.entities.PeriodType;
import com.climbershangout.entities.Training;
import com.climbershangout.entities.Workout;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import az.plainpie.PieView;

public class RunTrainingActivity extends BaseActivity {

    public static final String ARG_TRAINING = "training";

    private Training training;

    private WorkingStatus workingStatus = WorkingStatus.Stopped;

    private int lastTime = 0;
    private boolean periodFinished = false;
    private int prepTime = 0;
    private int workTime = 0;
    private int restTime = 0;
    private int pauseTime = 0;
    private boolean vibrate = false;
    private int toneVolume = ToneGenerator.MAX_VOLUME;
    private ToneGenerator toneGenerator;
    private WorkoutRecorder workoutRecorder;

    private BoardManager.IBoardCallback boardDataReceivedCallback;

    public RunTrainingActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_training);
        boardDataReceivedCallback = new BoardManager.IBoardCallback() {
            @Override
            public void onReceivedData(byte[] bytes) {
                onBoardDataReceived(bytes);
            }

            @Override
            public void onBoardAttachedDetached(final boolean boardAttachedDetached) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        boardAttachedDetached(boardAttachedDetached);
                    }
                });
            }
        };

        SharedPreferences preferences = getSharedPreferences(SettingsKeys.SHARED_PREF_FILE, Context.MODE_PRIVATE);
        vibrate = preferences.getBoolean(SettingsKeys.Notifications.VIBRATE, false);
        prepTime = preferences.getInt(SettingsKeys.Notifications.PREP_TIME, 10);
        workTime = preferences.getInt(SettingsKeys.Notifications.WORK_TIME, 3);
        restTime = preferences.getInt(SettingsKeys.Notifications.REST_TIME, 3);
        pauseTime = preferences.getInt(SettingsKeys.Notifications.PAUSE_TIME, 10);
        toneVolume = preferences.getInt(SettingsKeys.Notifications.TONE_V0LUME, ToneGenerator.MAX_VOLUME);
        toneGenerator = new ToneGenerator(AudioManager.STREAM_ALARM, toneVolume);

        BoardManager.getBoardManager().addBoardCallback(boardDataReceivedCallback);
        boardAttachedDetached(BoardManager.getBoardManager().isBoardAttached());

        if(null != savedInstanceState) {
            training = savedInstanceState.getParcelable(ARG_TRAINING);
        } else {
            training = getIntent().getParcelableExtra(ARG_TRAINING);
        }

        if (training != null) {
            workoutRecorder = new WorkoutRecorder(training);
            BoardManager.getBoardManager().addBoardCallback(workoutRecorder);
            training.addTimerListener(new ITimerListener() {
                @Override
                public void OnTick(final Period period, final float currentTime) {
                    trainingTick(period);
                }

                @Override
                public void OnFinish(boolean isCompleted) {
                    finishTraining(isCompleted);
                }
            });
            training.addTimerListener(workoutRecorder);
            initializeLoadChart();

        } else {
            finish();
        }

        addButtonListeners();
        setWorkingStatus();

        if(null != training) {
            initializeFragment();
            startTraining();
            training.pauseTraining();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if(null != training && workingStatus == WorkingStatus.Running) {
            training.pauseTraining();
            workingStatus = WorkingStatus.Paused;
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if(null != training && workingStatus == WorkingStatus.Running) {
            training.pauseTraining();
            workingStatus = WorkingStatus.Paused;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if(null != training) {
            training.resumeTraining();
            workingStatus = WorkingStatus.Running;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BoardManager.getBoardManager().removeBoardCallback(boardDataReceivedCallback);
    }

    private void boardAttachedDetached(boolean attachedDetached) {
        if(attachedDetached) {
            getBoardValueView().setVisibility(View.VISIBLE);
            getLoadChartContainer().setVisibility(View.VISIBLE);
        } else {
            getBoardValueView().setVisibility(View.INVISIBLE);
            getLoadChartContainer().setVisibility(View.INVISIBLE);
        }
    }

    private void onBoardDataReceived(byte[] bytes) {
        String data = "";
        try {
            data = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (!data.isEmpty() && !data.trim().isEmpty()) {
            float value;

            try {
                value = Float.parseFloat(data);
            }catch (NumberFormatException ex){
                value = 0f;
            }

            updateGraph(value);
        }
    }

    private void trainingTick(Period period) {
        float time = period.getCurrentTime();
        int duration = period.getDuration();
        PeriodType type = period.getPeriodType();

        notifyOnTimeRemaining(type, time, duration);
        setCounterSettings(type, time, duration, period.getRep(), period.getTotalReps());
        setTextInfo(type, period);
    }

    private BarDataSet createBarSet() {
        BarDataSet set = new BarDataSet(new ArrayList<BarEntry>(), "");
        set.setDrawValues(true);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(Color.RED);
        set.setValueTextColor(Color.BLACK);
        set.setValueTextSize(10f);

        return set;
    }

    private void initializeLoadChart() {
        getLoadChart().getDescription().setText("");
        getLoadChart().setDrawBarShadow(true);
        getLoadChart().setDrawValueAboveBar(true);
        getLoadChart().setMaxVisibleValueCount(1);
        getLoadChart().setPinchZoom(false);
        getLoadChart().setDoubleTapToZoomEnabled(false);
        getLoadChart().setDrawGridBackground(false);

        XAxis x = getLoadChart().getXAxis();
        x.setDrawAxisLine(false);
        x.setDrawLabels(false);

        YAxis y = getLoadChart().getAxisLeft();
        y.setDrawAxisLine(true);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setAxisMinimum(0f);
        y.setAxisMaximum(120f);

        y = getLoadChart().getAxisRight();
        y.setDrawAxisLine(false);
        y.setDrawLabels(false);

        getLoadChart().setFitBars(true);
        getLoadChart().animateY(200);
    }

    private void updateGraph(final float value) {
        float barWidth = 30f;
        BarDataSet set;

        if (getLoadChart().getData() != null &&
                getLoadChart().getData().getDataSetCount() > 0) {
            set = (BarDataSet) getLoadChart().getData().getDataSetByIndex(0);
            set.getValues().get(0).setY(value);
        } else {
            set = createBarSet();
            set.addEntry(new BarEntry(0, value));

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(barWidth);
            getLoadChart().setData(data);
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getBoardValueView().setText(String.valueOf(value));
                getLoadChart().getData().notifyDataChanged();
                getLoadChart().notifyDataSetChanged();
                getLoadChart().invalidate();
            }
        });
    }

    private void finishTraining(final boolean isCompleted) {
        workingStatus = WorkingStatus.Stopped;


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PieView pie = getPieView();
                pie.setPercentageBackgroundColor(getResources().getColor(isCompleted ? R.color.color_work : R.color.color_rest, getTheme()));
                pie.setPercentage((float)100);

                pie.setInnerText("");

                getExerciseView().setText(getResources().getString(isCompleted ? R.string.training_completed_successfully : R.string.training_interrupted));
                getExerciseDescriptionView().setText("");
                getSetRepetitionView().setText("");

                getPauseButton().setVisibility(View.GONE);
                getStopButton().setVisibility(View.GONE);
                getSkipSetButton().setVisibility(View.GONE);
                getSkipExButton().setVisibility(View.GONE);
                getCloseButton().setVisibility(View.VISIBLE);

                setWorkingStatus();
            }
        });
    }

    private void setTextInfo(PeriodType type, Period period) {

        String name = "";
        String description = "";
        String reps = "";

        if(type != PeriodType.Prep) {
            Exercise exercise = period.getExercise();

            name = exercise.getName();
            description = exercise.getDescription();

            if(type == PeriodType.Pause) {
                if(exercise.isInfiniteSets()) {
                    reps = String.format("Sets %1$d", period.getSet());
                } else {
                    reps = String.format("Sets %1$d of %2$d", period.getSet(), period.getTotalSets());
                }
            } else {
                if(exercise.isInfiniteSets()) {
                    reps = String.format("Sets %1$d. Reps %2$d of %3$d",
                            period.getSet(), period.getRep(), period.getTotalReps());
                } else {
                    reps = String.format("Sets %1$d of %2$d. Reps %3$d of %4$d",
                            period.getSet(), period.getTotalSets(),
                            period.getRep(), period.getTotalReps());
                }
            }
        }
        else {
            name = "Prepare!!!";
        }

        final boolean showRemainingTime = period.getRemainingTrainingTime() > 0;
        final int remainingTimeVisibility = showRemainingTime ? View.VISIBLE : View.INVISIBLE;
        String remainingTime = "00:00:00";
        if(showRemainingTime){
            int time = period.getRemainingTrainingTime() - (int)period.getCurrentTime();
            int hours = time / 3600;
            int minutes = (time % 3600) / 60;
            int seconds = (time % 3600) % 60;

            remainingTime = String.format("%1$02d:%2$02d:%3$02d", hours, minutes, seconds);
        }

        final String finalName = name;
        final String finalDescription = description;
        final String finalReps = reps;
        final String finalRemainingTime = remainingTime;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getRemainingTimeView().setText(finalRemainingTime);
                getRemainingTimeView().setVisibility(remainingTimeVisibility);
                getExerciseView().setText(finalName);
                getExerciseDescriptionView().setText(finalDescription);
                getSetRepetitionView().setText(finalReps);
            }
        });
    }

    private void setCounterSettings(PeriodType type, float time, int duration, int repetiion, int totalRepetiion) {
        final PieView pie = getPieView();
        pie.setPercentageBackgroundColor(getResources().getColor(getPieBackgroundColor(type), getTheme()));
        final float percentage = (float) (((time + 0) * 1.0 / duration) * 100);
        String innerText = "";
        int remainingTime = duration - (int)time;

        if(type == PeriodType.Prep)
            innerText = String.format("%1$d\n%2$s", remainingTime, String.valueOf(type));
        else if(type != PeriodType.Pause)
            innerText = String.format("%1$d / %2$d\n%3$s", remainingTime, totalRepetiion - repetiion, String.valueOf(type));
        else {
            int minutes = remainingTime / 60;
            int seconds = remainingTime % 60;
            innerText = String.format("%1$02d:%2$02d\n%3$s", minutes, seconds, String.valueOf(type));
        }

        final String finalInnerText = innerText;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pie.setPercentage(percentage);
                pie.setInnerText(finalInnerText);
            }
        });

    }

    private void notifyOnTimeRemaining(PeriodType type, float time, int duration) {
        long vibrateDuration = 0;
        if (time > 0 && time < 0.5f) { lastTime = 0; periodFinished = false; }

        if (!periodFinished && time + 0.15f >= duration) {
            vibrateDuration = 250;
            periodFinished = true;
        }

        if((int)time - lastTime > 0) {
            lastTime = (int) time;
            if (vibrateDuration == 0 && (type == PeriodType.Prep && duration - time <= prepTime
                    || type == PeriodType.Pause && duration - time <= pauseTime
                    || type == PeriodType.Rest && duration - time <= restTime
                    || type == PeriodType.Work && duration - time <= workTime)) {
                vibrateDuration = 125;
            }
        }

        if (vibrateDuration > 0) {
            if(vibrate) {
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(vibrateDuration);
            }
            if (vibrateDuration == 125)
                playIntermediateSound();
            else
                playFinalSound();
        }
    }

    private void playIntermediateSound() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP);
            }
        }, "playSound").start();
    }

    private void playFinalSound() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP);
                try {
                    Thread.currentThread().sleep(35);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP);
            }
        }, "playSound").start();
    }

    private int getPieBackgroundColor(PeriodType type) {
        switch (type){
            case Pause:
                return R.color.color_pause;
            case Prep:
                return R.color.color_prepare;
            case Rest:
                return R.color.color_rest;
            case Work:
                return R.color.color_work;
            default:
                return R.color.color_pause;
        }
    }

    private void initializeFragment() {
        getTrainingView().setText(training.getName());
        getExerciseView().setText(String.format("Exercises: %1d", training.getExercises().length));
        getExerciseDescriptionView().setText("");
        getPieView().setPercentage((float) 0.0);
        getPieView().setInnerText(" ");
        getSetRepetitionView().setText("");
    }

    private void addButtonListeners() {
        getPauseButton().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                pauseClicked();
            }
        });
        getStopButton().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                stopClicked();
            }
        });
        getSkipSetButton().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                skipSetClicked();
            }
        });
        getSkipExButton().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                skipExClicked();
            }
        });
        getCloseButton().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                closeClicked();
            }
        });
    }

    private void closeClicked() {
        final Context context = this;
        new AlertDialog.Builder(this)
            .setTitle(R.string.save_completed_workout_title)
            .setMessage(R.string.save_completed_workout)
            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Workout completedWorkout = workoutRecorder.getWorkout();

                    DbHelper helper = new DbHelper(context);
                    SQLiteDatabase db = helper.getWritableDatabase();

                    completedWorkout.save(db);

                    db.close();
                    helper.close();

                    if (null != workoutRecorder) {
                        BoardManager.getBoardManager().removeBoardCallback(workoutRecorder);
                    }
                    finish();
                }
            })
            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            })
            .show();
    }

    private void skipSetClicked() {
        training.skipSet();
        setWorkingStatus();
    }

    private void skipExClicked() {
        training.skipExercise();
        setWorkingStatus();
    }

    private void stopClicked() {
        workingStatus = WorkingStatus.Stopped;
        training.stopTraining();
        setWorkingStatus();
        initializeFragment();
    }

    private void pauseClicked() {
        if(workingStatus == WorkingStatus.Paused) {
            workingStatus = WorkingStatus.Running;
            training.resumeTraining();
            setWorkingStatus();
        } else {
            workingStatus = WorkingStatus.Paused;
            training.pauseTraining();
            setWorkingStatus();
        }
    }

    private void startTraining() {
        workingStatus = WorkingStatus.Running;
        training.startTraining();
        setWorkingStatus();
    }

    private void setWorkingStatus() {
        getPauseButton().setEnabled(workingStatus != workingStatus.Stopped);
        getStopButton().setEnabled(workingStatus != WorkingStatus.Stopped);
        getSkipSetButton().setEnabled(workingStatus == WorkingStatus.Running);
        getSkipExButton().setEnabled(workingStatus == WorkingStatus.Running);
    }

    private Button getPauseButton(){ return (Button) findViewById(R.id.btn_pause); }
    private Button getStopButton(){ return (Button) findViewById(R.id.btn_stop); }
    private Button getSkipSetButton(){ return (Button) findViewById(R.id.btn_skip_set); }
    private Button getSkipExButton(){ return (Button) findViewById(R.id.btn_skip_ex); }
    private Button getCloseButton(){ return (Button) findViewById(R.id.btn_training_close); }

    private PieView getPieView() { return (PieView) findViewById(R.id.player_counter); }
    private TextView getExerciseView() { return (TextView) findViewById(R.id.player_exercise); }
    private TextView getExerciseDescriptionView() { return (TextView) findViewById(R.id.player_exercise_description); }
    private TextView getTrainingView() { return (TextView) findViewById(R.id.player_training); }
    private TextView getSetRepetitionView() { return (TextView) findViewById(R.id.player_set_repetition); }

    private TextView getRemainingTimeView() { return (TextView) findViewById(R.id.player_remaining_time); }
    private TextView getBoardValueView() { return (TextView) findViewById(R.id.player_board_value); }

    private RelativeLayout getLoadChartContainer() { return (RelativeLayout) findViewById(R.id.player_load_chart_container); }
    private HorizontalBarChart getLoadChart() { return (HorizontalBarChart) findViewById(R.id.player_load_chart); }

    private enum WorkingStatus {
        Running,
        Paused,
        Stopped
    }
}
