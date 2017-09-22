package com.climbershangout.climbershangout.trainings;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.climbershangout.climbershangout.R;
import com.climbershangout.climbershangout.helpers.TimeFormatter;
import com.climbershangout.entities.Training;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * TODO: Replace the implementation with code for your data type.
 */
public class TrainingListRecyclerViewAdapter extends RecyclerView.Adapter<TrainingListRecyclerViewAdapter.ViewHolder> {

    private final List<Training> trainingList;
    private final onTrainingListItemClicked listener;
    private final Context context;

    public TrainingListRecyclerViewAdapter(List<Training> items, Context context, onTrainingListItemClicked listener) {
        trainingList = items;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_training, parent, false);
        return new ViewHolder(view, this, context);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Training training = trainingList.get(position);

        holder.training = training;
        holder.idView.setText(training.getId());
        holder.nameView.setText(training.getName());
        holder.descriptionView.setText(training.getDescription());

        int trainingDuration = training.getTotalDuration();
        String formattedTrainingDuration;
        if (trainingDuration > -1) {
            formattedTrainingDuration = TimeFormatter.format(trainingDuration, true, true, true, false);
        } else {
            formattedTrainingDuration = context.getString(R.string.loop_training);
        }

        holder.durationView.setText(formattedTrainingDuration);

        holder.infoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    listener.onItemInfo(holder.training);
                }
            }
        });

        holder.startView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    listener.onItemStarted(holder.training);
                }
            }
        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    listener.onItemClicked(holder.training);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return trainingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView idView;
        public final TextView nameView;
        public final TextView descriptionView;
        public final TextView durationView;
        public final ImageView infoView;
        public final ImageView startView;

        public Training training;
        public TrainingListRecyclerViewAdapter adapter;
        public Context context;

        public ViewHolder(View view, TrainingListRecyclerViewAdapter adapter, Context context) {
            super(view);
            this.view = view;
            idView = (TextView) view.findViewById(R.id.training_list_id);
            nameView = (TextView) view.findViewById(R.id.training_list_name);
            descriptionView = (TextView) view.findViewById(R.id.training_list_description);
            durationView = (TextView) view.findViewById(R.id.training_list_duration);
            infoView = (ImageView) view.findViewById(R.id.training_list_info);
            startView = (ImageView) view.findViewById(R.id.training_list_start);
            this.adapter = adapter;
            this.context = context;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + nameView.getText() + "'";
        }
    }

    public interface onTrainingListItemClicked{
        void onItemClicked(Training training);
        void onItemInfo(Training training);
        void onItemStarted(Training training);
    }
}
