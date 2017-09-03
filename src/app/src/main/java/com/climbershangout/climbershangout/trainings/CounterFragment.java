package com.climbershangout.climbershangout.trainings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.climbershangout.climbershangout.helpers.NumberPickerHelper;
import com.climbershangout.climbershangout.R;
import com.climbershangout.climbershangout.helpers.TimeNumberPickerFormatter;
import com.climbershangout.entities.Exercise;
import com.climbershangout.entities.Training;
import com.climbershangout.entities.UniformTimedExercise;
import com.shawnlin.numberpicker.NumberPicker;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class CounterFragment extends Fragment {

    private static View view;
    private Training training;

    public CounterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_counter, container, false);
            initializePickers();
        } catch (InflateException e) {
            /* map is already there, just return view as it is */
            e.printStackTrace();
        }
        addButtonListeners();

        return view;
    }

    private void initializePickers() {
        NumberPickerHelper.initializePickerValues(getPrepTimePicker(), 0, 300, 10, new TimeNumberPickerFormatter(true));
        NumberPickerHelper.initializePickerValues(getRepsPicker(), 0, 300, 4, null);
        NumberPickerHelper.initializePickerValues(getRestTimePicker(), 0, 300, 4, new TimeNumberPickerFormatter(true));
        NumberPickerHelper.initializePickerValues(getWorkTimePicker(), 0, 300, 6, new TimeNumberPickerFormatter(true));
        NumberPickerHelper.initializePickerValues(getPauseTimePicker(), 0, 30, 2, new TimeNumberPickerFormatter(false));
    }

    private void addButtonListeners() {
        getStartButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startClicked();
            }
        });
    }

    private void startClicked() {
        training = new Training();
        training.setName("Counter");
        training.setPreparationDuration(getPrepTimePicker().getValue());
        UniformTimedExercise exercise = new UniformTimedExercise();
        exercise.setWorkDuration(getWorkTimePicker().getValue());
        exercise.setRestDuration(getRestTimePicker().getValue());
        exercise.setRepetitionCount(getRepsPicker().getValue());
        exercise.setPauseDuration(getPauseTimePicker().getValue() * 60);
        exercise.setInfiniteSets(true);
        training.setExercises(new Exercise[]{exercise});

        Intent intent = new Intent(getContext(), RunTrainingActivity.class);
        intent.putExtra(RunTrainingActivity.ARG_TRAINING, training);
        startActivity(intent);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private Button getStartButton() { return (Button) view.findViewById(R.id.btn_start_counter); }
    private NumberPicker getPrepTimePicker() { return (NumberPicker) view.findViewById(R.id.counter_prep_time); }
    private NumberPicker getRepsPicker() { return (NumberPicker) view.findViewById(R.id.counter_repetition_count); }
    private NumberPicker getRestTimePicker() { return (NumberPicker) view.findViewById(R.id.counter_rest_time); }
    private NumberPicker getWorkTimePicker() { return (NumberPicker) view.findViewById(R.id.counter_work_time); }
    private NumberPicker getPauseTimePicker() { return (NumberPicker) view.findViewById(R.id.counter_pause_time); }
}


