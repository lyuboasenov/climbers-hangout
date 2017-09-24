package com.climbershangout.climbershangout.trainings;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.climbershangout.climbershangout.BaseActivity;
import com.climbershangout.climbershangout.helpers.NumberPickerHelper;
import com.climbershangout.climbershangout.R;
import com.climbershangout.climbershangout.helpers.TimeNumberPickerFormatter;
import com.climbershangout.climbershangout.workouts.ReviewWorkoutDialog;
import com.climbershangout.db.DbHelper;
import com.climbershangout.entities.Exercise;
import com.climbershangout.entities.Training;
import com.climbershangout.entities.UniformTimedExercise;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddTrainingActivity extends BaseActivity {

    //Members
    List<Exercise> exerciseItems = new ArrayList<>();
    ViewExerciseRecyclerViewAdapter exercisesAdapter;
    private Menu menu;

    //Property
    public ImageButton getAddExerciseButton() { return (ImageButton) findViewById(R.id.add_training_btn_add_exercise); }

    public EditText getNameView() { return (EditText) findViewById(R.id.add_training_name); }
    public EditText getDescriptionView() { return (EditText) findViewById(R.id.add_training_description); }
    public NumberPicker getPrepTimePicker() { return (NumberPicker) findViewById(R.id.add_training_prep_time); }
    public RecyclerView getExerciseListView() { return (RecyclerView) findViewById(R.id.add_training_exercise_list); }

    private Toolbar getToolbar() { return (Toolbar) findViewById(R.id.toolbar); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_training);
        final Context context = this;

        initializeActionBar();

        exercisesAdapter = new ViewExerciseRecyclerViewAdapter(exerciseItems);
        getExerciseListView().setAdapter(exercisesAdapter);
        getExerciseListView().setLayoutManager(new LinearLayoutManager(this));
        exercisesAdapter.setExerciseClickedListener(new ViewExerciseRecyclerViewAdapter.OnExerciseClickedListener() {
            @Override
            public void onClick(final Exercise exercise) {
                EditExerciseDialog editExerciseDialog = new EditExerciseDialog(context, (UniformTimedExercise) exercise);
                editExerciseDialog.setOnClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i == R.id.edit_exercise_btn_ok
                                && ReviewTrainingDialog.class.isInstance(dialogInterface)) {

                        } else if(i == R.id.edit_exercise_btn_cancel
                                && ReviewTrainingDialog.class.isInstance(dialogInterface)) {

                        } else if(i == R.id.edit_exercise_btn_delete
                                && ReviewTrainingDialog.class.isInstance(dialogInterface)) {
                            exerciseItems.remove(exercise);
                        }
                    }
                });

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(editExerciseDialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                editExerciseDialog.show();

                editExerciseDialog.getWindow().setAttributes(lp);
            }
        });

        addButtonListeners();
        initializePickerValues();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_training_menu, menu);
        this.menu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.

        switch (item.getItemId()) {
            case R.id.action_add_training:
                addTraining();
                return true;
            case R.id.action_discard_training:
                cancel();
                return true;
            default:
                cancel();
                return true;
        }
    }

    private void initializeActionBar() {
        setSupportActionBar(getToolbar());
        final ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle(getString(R.string.add_training_title));

        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initializePickerValues() {
        NumberPickerHelper.initializePickerValues(getPrepTimePicker(), 0, 300, 10, new TimeNumberPickerFormatter(true));
    }

    private void addButtonListeners() {
        getAddExerciseButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExerciseClicked();
            }
        });
    }

    private void addExerciseClicked() {
        UniformTimedExercise ex = new UniformTimedExercise();
        ex.setName("Exercise name");
        ex.setDescription("Description");
        ex.setPauseDuration(2 * 60);
        ex.setRepetitionCount(6);
        ex.setWorkDuration(6);
        ex.setRestDuration(4);
        ex.setSetCount(6);

        exerciseItems.add(ex);
        exercisesAdapter.notifyDataSetChanged();
    }

    private void addTraining() {
        Training training = new Training();

        training.setId(UUID.randomUUID().toString());
        training.setName(getNameView().getText().toString());
        training.setDescription(getDescriptionView().getText().toString());
        training.setPreparationDuration(getPrepTimePicker().getValue());
        Exercise[] exercises = exerciseItems.toArray(new Exercise[exerciseItems.size()]);
        training.setExercises(exercises);


        DbHelper helper = new DbHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        training.save(db);
        db.close();

        finish();
    }

    private void cancel() {
        finish();
    }
}
