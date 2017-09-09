package com.climbershangout.climbershangout.trainings;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.climbershangout.climbershangout.helpers.NumberPickerHelper;
import com.climbershangout.climbershangout.R;
import com.climbershangout.climbershangout.helpers.TimeNumberPickerFormatter;
import com.climbershangout.entities.UniformTimedExercise;
import com.shawnlin.numberpicker.NumberPicker;

public class EditExerciseDialog extends AppCompatDialog {

    private UniformTimedExercise exercise;
    private View view;
    private DialogInterface.OnClickListener onClickListener;

    public EditExerciseDialog(Context context, UniformTimedExercise exercise) {
        super(context);
        setExercise(exercise);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.dialog_edit_exercise, null);

        setContentView(view);

        addButtonListeners();

        initializePickers();

        getNameView().setText(getExercise().getName());
        getDescriptionView().setText(getExercise().getDescription());

        getInfiniteSetsCheckBox().setChecked(getExercise().isInfiniteSets());
        getInfiniteSetsCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getSetsPicker().setEnabled(!b);
                getSetsPicker().setWrapSelectorWheel(!b);
            }
        });

        getSetsPicker().setValue(getExercise().getSetCount());
        getRepsPicker().setValue(getExercise().getRepetitionCount());
        getWorkTimePicker().setValue(getExercise().getWorkDuration());
        getRestTimePicker().setValue(getExercise().getRestDuration());
        getPauseTimePicker().setValue(getExercise().getPauseDuration() / 60);
    }

    public void setOnClickListener(DialogInterface.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private void addButtonListeners() {
        getOkButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getExercise().setName(getNameView().getText().toString());
                getExercise().setDescription(getDescriptionView().getText().toString());

                getExercise().setSetCount(getInfiniteSetsCheckBox().isChecked() ? 0 : getSetsPicker().getValue());
                getExercise().setInfiniteSets(getInfiniteSetsCheckBox().isChecked());
                getExercise().setRepetitionCount(getRepsPicker().getValue());
                getExercise().setWorkDuration(getWorkTimePicker().getValue());
                getExercise().setRestDuration(getRestTimePicker().getValue());
                getExercise().setPauseDuration(getPauseTimePicker().getValue() * 60);

                onButtonClicked(R.id.edit_exercise_btn_ok);
            }
        });
        getCancelButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClicked(R.id.edit_exercise_btn_cancel);
            }
        });
        getDeleteButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClicked(R.id.edit_exercise_btn_delete);
            }
        });
    }

    private void onButtonClicked(int id){
        if(null != onClickListener) {
            onClickListener.onClick(this, id);
        }
        dismiss();
    }


    private void initializePickers() {
        NumberPickerHelper.initializePickerValues(getSetsPicker(), 0, 300, 4, null);
        NumberPickerHelper.initializePickerValues(getRepsPicker(), 0, 300, 4, null);
        NumberPickerHelper.initializePickerValues(getRestTimePicker(), 0, 300, 4, new TimeNumberPickerFormatter(true));
        NumberPickerHelper.initializePickerValues(getWorkTimePicker(), 0, 300, 6, new TimeNumberPickerFormatter(true));
        NumberPickerHelper.initializePickerValues(getPauseTimePicker(), 0, 30, 2, new TimeNumberPickerFormatter(false));
    }

    public EditText getNameView() { return (EditText) view.findViewById(R.id.edit_exercise_name); }
    public EditText getDescriptionView() { return (EditText) view.findViewById(R.id.edit_exercise_description); }

    private CheckBox getInfiniteSetsCheckBox() { return (CheckBox) view.findViewById(R.id.edit_exercise_infinite_sets); }

    private NumberPicker getSetsPicker() { return (NumberPicker) view.findViewById(R.id.edit_exercise_sets); }
    private NumberPicker getRepsPicker() { return (NumberPicker) view.findViewById(R.id.edit_exercise_reps); }
    private NumberPicker getRestTimePicker() { return (NumberPicker) view.findViewById(R.id.edit_exercise_rest_time); }
    private NumberPicker getWorkTimePicker() { return (NumberPicker) view.findViewById(R.id.edit_exercise_work_time); }
    private NumberPicker getPauseTimePicker() { return (NumberPicker) view.findViewById(R.id.edit_exercise_pause_time); }

    private Button getOkButton() { return (Button) view.findViewById(R.id.edit_exercise_btn_ok); }
    private Button getCancelButton() { return (Button) view.findViewById(R.id.edit_exercise_btn_cancel); }
    private Button getDeleteButton() { return (Button) view.findViewById(R.id.edit_exercise_btn_delete); }

    public UniformTimedExercise getExercise() { return this.exercise; }
    private void setExercise(UniformTimedExercise exercise) { this.exercise = exercise; }
}
