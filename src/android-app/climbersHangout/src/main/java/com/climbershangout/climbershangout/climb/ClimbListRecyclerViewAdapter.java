package com.climbershangout.climbershangout.climb;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.climbershangout.climbershangout.R;
import com.climbershangout.climbershangout.helpers.TimeFormatter;
import com.climbershangout.entities.Climb;
import com.climbershangout.entities.Training;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * TODO: Replace the implementation with code for your data type.
 */
public class ClimbListRecyclerViewAdapter extends RecyclerView.Adapter<ClimbListRecyclerViewAdapter.ViewHolder> {

    private final List<Climb> climbList;
    private final onClimbListItemClicked listener;
    private final Context context;

    public ClimbListRecyclerViewAdapter(List<Climb> items, Context context, onClimbListItemClicked listener) {
        climbList = items;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_climb, parent, false);
        return new ViewHolder(view, this, context);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Climb climb = climbList.get(position);

        holder.climb = climb;
        holder.idView.setText(climb.getId());
    }

    @Override
    public int getItemCount() {
        return climbList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView idView;

        public Climb climb;
        public ClimbListRecyclerViewAdapter adapter;
        public Context context;

        public ViewHolder(View view, ClimbListRecyclerViewAdapter adapter, Context context) {
            super(view);
            this.view = view;
            idView = (TextView) view.findViewById(R.id.training_list_id);
            this.adapter = adapter;
            this.context = context;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + idView.getText() + "'";
        }
    }

    public interface onClimbListItemClicked{
        void onItemClicked(Climb climb);
    }
}
