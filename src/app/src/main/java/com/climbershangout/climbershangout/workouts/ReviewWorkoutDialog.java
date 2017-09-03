package com.climbershangout.climbershangout.workouts;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.climbershangout.climbershangout.R;
import com.climbershangout.entities.Training;
import com.climbershangout.entities.Workout;
import com.climbershangout.entities.WorkoutItem;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class ReviewWorkoutDialog extends AppCompatDialog {

    private Workout workout;
    private View view;
    private OnClickListener onClickListener;

    public ReviewWorkoutDialog(Context context, Workout workout) {
        super(context);
        setWorkout(workout);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.dialog_review_workout, null);

        setContentView(view);
        addButtonListeners();

        getTrainingNameView().setText(getWorkout().getTrainingName());
        getDateView().setText(getWorkout().getDate().toString());
        getTotalTimeView().setText(Long.toString(getWorkout().getTotalTime()));

        initializeChart();
    }

    private void initializeChart() {
        getChartView().setBackgroundColor(Color.WHITE);
        //getChartView().setGridBackgroundColor(mFillColor);
        getChartView().setDrawGridBackground(true);

        getChartView().setDrawBorders(true);

        // no description text
        getChartView().getDescription().setEnabled(false);

        getChartView().setPinchZoom(false);
        getChartView().setDoubleTapToZoomEnabled(false);
        getChartView().setDragEnabled(true);
        getChartView().setScaleXEnabled(true);

        Legend l = getChartView().getLegend();
        l.setEnabled(false);

        XAxis xAxis = getChartView().getXAxis();
        xAxis.setEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftAxis = getChartView().getAxisLeft();
        leftAxis.setAxisMaximum(100f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawAxisLine(true);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawGridLines(true);

        getChartView().getAxisRight().setEnabled(false);

        // add data
        ArrayList<Entry> loadValues = new ArrayList<>();
        ArrayList<Entry> trainingValues = new ArrayList<>();

        int currentWorkoutItemIndex = 0;
        long totalTime = 0;
        WorkoutItem.WorkoutItemType lastWorkoutItemType = WorkoutItem.WorkoutItemType.REST;
        for (long i = 0; i <= getWorkout().getTotalTime(); i++) {

            if(totalTime + getWorkout().getItems()[currentWorkoutItemIndex].getDuration() < i) {
                totalTime += getWorkout().getItems()[currentWorkoutItemIndex].getDuration();
                currentWorkoutItemIndex++;
            }
            if (currentWorkoutItemIndex >= getWorkout().getItems().length) {
                break;
            }

            float load = getWorkout().getItems()[currentWorkoutItemIndex].getLoad();
            WorkoutItem.WorkoutItemType itemType = getWorkout().getItems()[currentWorkoutItemIndex].getType();

            loadValues.add(new Entry(i, load));

            if (itemType != lastWorkoutItemType){
                trainingValues.add(new Entry(i - 1, (itemType == WorkoutItem.WorkoutItemType.REST ? 0 : 90)));
            }
            trainingValues.add(new Entry(i, (itemType == WorkoutItem.WorkoutItemType.REST ? 0 : 90)));
            lastWorkoutItemType = itemType;
        }

        LineDataSet loadSet, trainingSet;

        // create a dataset and give it a type
        loadSet = new LineDataSet(loadValues, "Load");

        loadSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        loadSet.setColor(Color.GREEN);
        loadSet.setDrawCircles(false);
        loadSet.setLineWidth(2f);
        loadSet.setCircleRadius(3f);
        loadSet.setFillAlpha(255);
        loadSet.setDrawFilled(true);
        loadSet.setFillColor(Color.WHITE);
        loadSet.setHighLightColor(Color.rgb(244, 117, 117));
        loadSet.setDrawCircleHole(false);
        loadSet.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return getChartView().getAxisLeft().getAxisMinimum();
            }
        });

        // create a dataset and give it a type
        trainingSet = new LineDataSet(trainingValues, "DataSet 2");
        trainingSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        trainingSet.setColor(Color.YELLOW);
        trainingSet.setDrawCircles(false);
        trainingSet.setLineWidth(2f);
        trainingSet.setCircleRadius(3f);
        trainingSet.setFillAlpha(255);
        trainingSet.setDrawFilled(true);
        trainingSet.setFillColor(Color.WHITE);
        trainingSet.setDrawCircleHole(false);
        trainingSet.setHighLightColor(Color.rgb(244, 117, 117));
        trainingSet.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return getChartView().getAxisLeft().getAxisMaximum();
            }
        });

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(loadSet); // add the datasets
        dataSets.add(trainingSet);

        // create a data object with the datasets
        LineData data = new LineData(dataSets);
        data.setDrawValues(false);

        // set data
        getChartView().setData(data);
        getChartView().invalidate();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private void addButtonListeners() {
        getStartButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClicked(R.id.review_workout_btn_start);
            }
        });
        getCloseButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClicked(R.id.review_workout_btn_close);
            }
        });
        getDeleteButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClicked(R.id.review_workout_btn_delete);
            }
        });
    }

    private void onButtonClicked(int id){
        if(null != onClickListener) {
            onClickListener.onClick(this, id);
        }
        dismiss();
    }

    public TextView getDateView() { return (TextView) view.findViewById(R.id.review_workout_date); }
    public TextView getTrainingNameView() { return (TextView) view.findViewById(R.id.review_workout_training_name); }
    public TextView getTotalTimeView() { return (TextView) view.findViewById(R.id.review_workout_total_time); }

    public LineChart getChartView() { return (LineChart) view.findViewById(R.id.review_workout_chart); }

    private Button getCloseButton() { return (Button) view.findViewById(R.id.review_workout_btn_close); }
    private Button getStartButton() { return (Button) view.findViewById(R.id.review_workout_btn_start); }
    private Button getDeleteButton() { return (Button) view.findViewById(R.id.review_workout_btn_delete); }

    public Workout getWorkout() {
        return workout;
    }
    private void setWorkout(Workout workout) {
        this.workout = workout;
    }
}
