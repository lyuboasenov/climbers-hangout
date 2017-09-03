package com.climbershangout.climbershangout.trainings;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.climbershangout.climbershangout.R;
import com.climbershangout.climbershangout.helpers.TimeNumberPickerFormatter;
import com.climbershangout.entities.Training;

import java.util.Arrays;

public class ReviewTrainingDialog extends AppCompatDialog {

    private Training training;
    private View view;
    private OnClickListener onClickListener;

    public ReviewTrainingDialog(Context context, Training training) {
        super(context);
        setTraining(training);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.dialog_review_training, null);

        setContentView(view);
        addButtonListeners();

        getNameView().setText(getTraining().getName());
        getDescriptionView().setText(getTraining().getDescription());
        getPrepTimeView().setText(TimeNumberPickerFormatter.format(true, getTraining().getPreparationDuration()));

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.review_training_exercise_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        recyclerView.setAdapter(new ViewExerciseRecyclerViewAdapter(Arrays.asList(getTraining().getExercises())));
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private void addButtonListeners() {
        getStartButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClicked(R.id.review_training_btn_start);
            }
        });
        getCloseButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClicked(R.id.review_training_btn_close);
            }
        });
        getDeleteButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClicked(R.id.review_training_btn_delete);
            }
        });
    }

    private void onButtonClicked(int id){
        if(null != onClickListener) {
            onClickListener.onClick(this, id);
        }
        dismiss();
    }

    public TextView getNameView() { return (TextView) view.findViewById(R.id.review_training_name); }
    public TextView getDescriptionView() { return (TextView) view.findViewById(R.id.review_training_description); }
    public TextView getPrepTimeView() { return (TextView) view.findViewById(R.id.review_training_prep_time); }

    private Button getCloseButton() { return (Button) view.findViewById(R.id.review_training_btn_close); }
    private Button getStartButton() { return (Button) view.findViewById(R.id.review_training_btn_start); }
    private Button getDeleteButton() { return (Button) view.findViewById(R.id.review_training_btn_delete); }

    public Training getTraining() { return this.training; }
    private void setTraining(Training value) { this.training = value; }
}
