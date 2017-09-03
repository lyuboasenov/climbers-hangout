package com.climbershangout.climbershangout.trainings;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.climbershangout.climbershangout.R;
import com.climbershangout.climbershangout.helpers.TimeNumberPickerFormatter;
import com.climbershangout.entities.Exercise;
import com.climbershangout.entities.UniformTimedExercise;

import java.util.List;

public class ViewExerciseRecyclerViewAdapter extends RecyclerView.Adapter<ViewExerciseRecyclerViewAdapter.ViewHolder> {

    private final List<Exercise> exerciseList;
    private OnExerciseClickedListener exerciseClickedListener;

    public ViewExerciseRecyclerViewAdapter(List<Exercise> items) {
        exerciseList = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_view_exercise, parent, false);

        ViewHolder viewHolder = new ViewHolder(view, this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        UniformTimedExercise currentExercise = (UniformTimedExercise) exerciseList.get(position);
        holder.exercise = currentExercise;

        holder.nameView.setText(currentExercise.getName());
        holder.descriptionView.setText(currentExercise.getDescription());

        if(currentExercise.isInfiniteSets()) {
            holder.setCountView.setText("Loop");
        } else {
            holder.setCountView.setText(String.format("%1$d", currentExercise.getSetCount()));
        }

        holder.repCountView.setText(String.format("%1$d", currentExercise.getRepetitionCount()));
        holder.workTimeView.setText(TimeNumberPickerFormatter.format(true, currentExercise.getWorkDuration()));
        holder.restTimeView.setText(TimeNumberPickerFormatter.format(true, currentExercise.getRestDuration()));
        holder.pauseTimeView.setText(TimeNumberPickerFormatter.format(false, currentExercise.getPauseDuration() / 60));
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public void setExerciseClickedListener(OnExerciseClickedListener exerciseClickedListener) {
        this.exerciseClickedListener = exerciseClickedListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View view;

        private final TextView nameView;
        private final TextView descriptionView;
        private final TextView repCountView;
        private final TextView workTimeView;
        private final TextView restTimeView;
        private final TextView pauseTimeView;
        private final TextView setCountView;
        private final ViewExerciseRecyclerViewAdapter adapter;

        public UniformTimedExercise exercise;

        public ViewHolder(View view, final ViewExerciseRecyclerViewAdapter adapter) {
            super(view);

            this.adapter = adapter;
            this.view = view;

            nameView = (TextView) view.findViewById(R.id.view_exercise_name);
            descriptionView = (TextView) view.findViewById(R.id.view_exercise_description);
            setCountView = (TextView) view.findViewById(R.id.view_exercise_sets);
            repCountView = (TextView) view.findViewById(R.id.view_exercise_reps);
            workTimeView = (TextView) view.findViewById(R.id.view_exercise_work);
            restTimeView = (TextView) view.findViewById(R.id.view_exercise_rest);
            pauseTimeView = (TextView) view.findViewById(R.id.view_exercise_pause);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapter.exerciseClickedListener.onClick(exercise);
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + nameView.getText() + "'";
        }
    }

    public interface OnExerciseClickedListener {
        void onClick(Exercise exercise);
    }
}
