package com.climbershangout.climbershangout.trainings;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.climbershangout.climbershangout.R;
import com.climbershangout.db.DbHelper;
import com.climbershangout.entities.Training;

import java.util.List;

public class TrainingListFragment extends Fragment {
    private static View view;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TrainingListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_training_list, container, false);
        } catch (InflateException e) {
            /* map is already there, just return view as it is */
            e.printStackTrace();
        }
        getRecyclerView().setLayoutManager(new LinearLayoutManager(this.getContext()));

        addButtonListeners();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        DbHelper helper = new DbHelper(getContext());
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Training> trainings = Training.getAllTrainings(db);
        db.close();
        getRecyclerView().setAdapter(new TrainingListRecyclerViewAdapter(trainings, this.getActivity(), new TrainingListRecyclerViewAdapter.onTrainingListItemClicked() {
            @Override
            public void onItemClicked(Training training) {
                showTrainingSummaryDialog(training);
            }
        }));
    }

    private void addButtonListeners(){
        getAddTrainingButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTraining();
            }
        });
    }

    private void addTraining() {
        Intent intent = new Intent(getContext(), AddTrainingActivity.class);
        startActivity(intent);
    }

    private void showTrainingSummaryDialog(final Training training) {
        ReviewTrainingDialog reviewTrainingDialog = new ReviewTrainingDialog(getContext(), training);
        reviewTrainingDialog.setOnClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == R.id.review_training_btn_start
                        && ReviewTrainingDialog.class.isInstance(dialogInterface)) {
                    startTraining(training);
                } else if(i == R.id.review_training_btn_delete
                        && ReviewTrainingDialog.class.isInstance(dialogInterface)) {
                    deleteTraining(training);
                }
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(reviewTrainingDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        reviewTrainingDialog.show();

        reviewTrainingDialog.getWindow().setAttributes(lp);
    }

    private void deleteTraining(final Training training) {
        new AlertDialog.Builder(getContext())
                .setTitle("DELETE")
                .setMessage("Are you sure you want to delete " + training.getName() + " training.")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DbHelper helper = new DbHelper(getContext());
                        SQLiteDatabase db = helper.getWritableDatabase();
                        training.removeFromDB(db);
                        db.close();
                        helper.close();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_menu_manage)
                .show();
    }

    private void startTraining(Training training) {
        Intent intent = new Intent(getContext(), RunTrainingActivity.class);
        intent.putExtra(RunTrainingActivity.ARG_TRAINING, training);
        startActivity(intent);
    }

    private RecyclerView getRecyclerView() { return (RecyclerView) view.findViewById(R.id.training_list); }
    private ImageButton getAddTrainingButton(){ return (ImageButton) view.findViewById(R.id.btn_add_training); }
}
