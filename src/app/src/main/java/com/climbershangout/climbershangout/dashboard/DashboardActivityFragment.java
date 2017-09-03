package com.climbershangout.climbershangout.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.climbershangout.climbershangout.ClimbersHangoutApplication;
import com.climbershangout.climbershangout.R;

public class DashboardActivityFragment extends Fragment {

    private View view;
    private int activityCount;
    private ActivityType activityType;
    private ActivityPeriod activityPeriod;
    private onDashboardActivityClickListener clickListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard_activity, container, false);

        setActivityCount(activityCount);

        final DashboardActivityFragment fragment = this;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null != clickListener)
                    clickListener.onDashboardActivityClicked(fragment);
            }
        });

        getActivityTypeView().setText(getActivityTypeText(getActivityType()));

        return view;
    }

    public DashboardActivityFragment setActivityCount(int activityCount) {
        this.activityCount = activityCount;
        if(null != view && null != getActivityCountView())
            getActivityCountView().setText(Integer.toString(activityCount));

        return this;
    }

    public int getActivityCount() { return activityCount; }

    public DashboardActivityFragment setClickListener(onDashboardActivityClickListener clickListener) {
        this.clickListener = clickListener;

        return this;
    }

    private TextView getActivityCountView() { return (TextView)view.findViewById(R.id.activity_count); }
    private TextView getActivityTypeView() { return (TextView)view.findViewById(R.id.activity_type); }

    public static String getActivityTypeText(ActivityType activityType){
        String activityTypeText = "";
        if(null != activityType) {
            Context context = ClimbersHangoutApplication.getCurrent();

            switch (activityType) {
                case HANGBOARD:
                    activityTypeText = context.getString(R.string.activity_type_hangboard);
                    break;
                case CAMPUS:
                    activityTypeText = context.getString(R.string.activity_type_campus);
                    break;
                case INDOOR:
                    activityTypeText = context.getString(R.string.activity_type_indoor);
                    break;
                case OUTDOOR:
                    activityTypeText = context.getString(R.string.activity_type_outdoor);
            }
        }

        return activityTypeText;
    }

    public static String getActivityPeriodText(ActivityPeriod period) {
        String periodText = "";
        Context context = ClimbersHangoutApplication.getCurrent();

        switch (period){
            case WEEK:
                periodText = context.getString(R.string.activity_period_week);
                break;
            case MONTH:
                periodText = context.getString(R.string.activity_period_month);
                break;
            case YEAR:
                periodText = context.getString(R.string.activity_period_year);
                break;
            case TOTAL:
                periodText = context.getString(R.string.activity_period_total);
                break;
        }

        return periodText;
    }

    public ActivityPeriod getActivityPeriod() {
        return activityPeriod;
    }

    public DashboardActivityFragment setActivityPeriod(ActivityPeriod activityPeriod) {
        this.activityPeriod = activityPeriod;

        return this;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public DashboardActivityFragment setActivityType(ActivityType activityType) {
        this.activityType = activityType;

        return this;
    }

    public interface onDashboardActivityClickListener {
        void onDashboardActivityClicked(DashboardActivityFragment fragment);
    }

    public enum ActivityType {
        HANGBOARD,
        CAMPUS,
        INDOOR,
        OUTDOOR
    }

    public enum ActivityPeriod {
        WEEK,
        MONTH,
        YEAR,
        TOTAL
    }
}