package com.climbershangout.climbershangout.dashboard;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.climbershangout.climbershangout.R;
import com.climbershangout.climbershangout.helpers.DateHelper;
import com.climbershangout.climbershangout.views.WrapContentViewPager;
import com.climbershangout.climbershangout.workouts.ReviewWorkoutDialog;
import com.climbershangout.climbershangout.trainings.ReviewTrainingDialog;
import com.climbershangout.db.DbHelper;
import com.climbershangout.entities.Workout;

import java.util.Date;
import java.util.List;

/**
 * Created by lyuboslav on 11/16/2016.
 */

public class DashboardFragment extends Fragment implements DashboardActivityFragment.onDashboardActivityClickListener {

    private static View view;

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
            view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }

        return view;
    }

    private void initializeFragments() {

        getAvatarView().setImageDrawable(getResources().getDrawable(R.drawable.ic_default_icon, getActivity().getTheme()));

        DateHelper.Period thisWeek = DateHelper.getThisWeekPeriod();
        DateHelper.Period lastWeek = DateHelper.getPreviousWeekPeriod();
        DateHelper.Period thisMonth = DateHelper.getThisMonthPeriod();
        DateHelper.Period lastMonth = DateHelper.getPreviousMonthPeriod();
        DateHelper.Period thisYear = DateHelper.getThisYearPeriod();
        DateHelper.Period lastYear = DateHelper.getPreviousYearPeriod();

        DbHelper helper = new DbHelper(getContext());
        SQLiteDatabase db = helper.getReadableDatabase();

        int totalActivityCount = Workout.getWorkoutCountForPeriod(db, DateHelper.getDate(1984, 9, 16), new Date());
        int thisWeekWorkouts = Workout.getWorkoutCountForPeriod(db, thisWeek.getStart(), thisWeek.getEnd());
        int lastWeekWorkouts = Workout.getWorkoutCountForPeriod(db, lastWeek.getStart(), lastWeek.getEnd());
        int thisMonthWorkouts = Workout.getWorkoutCountForPeriod(db, thisMonth.getStart(), thisMonth.getEnd());
        int lastMonthWorkouts = Workout.getWorkoutCountForPeriod(db, lastMonth.getStart(), lastMonth.getEnd());
        int thisYearWorkouts = Workout.getWorkoutCountForPeriod(db, thisYear.getStart(), thisYear.getEnd());
        int lastYearWorkouts = Workout.getWorkoutCountForPeriod(db, lastYear.getStart(), lastYear.getEnd());

        db.close();
        helper.close();

        int[] workouts = new int[] { thisWeekWorkouts, lastWeekWorkouts, thisMonthWorkouts, lastMonthWorkouts, thisYearWorkouts, lastYearWorkouts };

        getViewPager().setAdapter(new DashboardActivitiesPagerAdapter(getChildFragmentManager(), workouts));
        getViewPagerTabs().setupWithViewPager(getViewPager());

        getTotalActivityView().setText(Integer.toString(totalActivityCount));

        getTotalActivitiesContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "total clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        initializeFragments();
    }

    private List<Workout> workouts;

    private ImageView getAvatarView() { return (ImageView)view.findViewById(R.id.dashboard_avatar); }
    private WrapContentViewPager getViewPager() { return (WrapContentViewPager)view.findViewById(R.id.dashboard_activity_pager); }
    private TabLayout getViewPagerTabs() { return (TabLayout)view.findViewById(R.id.dashboard_activity_tabs); }
    private TextView getTotalActivityView() { return (TextView)view.findViewById(R.id.dashboard_total_activities); }
    private LinearLayout getTotalActivitiesContainer() { return (LinearLayout)view.findViewById(R.id.dashboard_total_Activities_container); }

    @Override
    public void onDashboardActivityClicked(DashboardActivityFragment fragment) {

        if(null != workouts && workouts.size() > 0) {
            ReviewWorkoutDialog reviewWorkoutDialog = new ReviewWorkoutDialog(getContext(), workouts.get(0));
            reviewWorkoutDialog.setOnClickListener(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(i == R.id.review_training_btn_start
                            && ReviewTrainingDialog.class.isInstance(dialogInterface)) {

                    } else if(i == R.id.review_training_btn_delete
                            && ReviewTrainingDialog.class.isInstance(dialogInterface)) {

                    }
                }
            });

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(reviewWorkoutDialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;

            reviewWorkoutDialog.show();

            reviewWorkoutDialog.getWindow().setAttributes(lp);
        } else {
            String type = DashboardActivityFragment.getActivityTypeText(fragment.getActivityType());
            String period = DashboardActivityFragment.getActivityPeriodText(fragment.getActivityPeriod());
            int count = fragment.getActivityCount();

            new AlertDialog.Builder(getContext())
                    .setTitle("Activity Clicked")
                    .setMessage(String.format("This %1$s you've have %2$d %3$s workout(s).", period.toLowerCase(), count, type.toLowerCase()))
                    .setNeutralButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    })
                    .show();
        }
    }
}
