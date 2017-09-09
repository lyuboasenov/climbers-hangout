package com.climbershangout.climbershangout.dashboard;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.climbershangout.climbershangout.R;

public class DashboardActivitiesFragment extends Fragment {

    public static final String THIS = "this";
    public static final String LAST = "last";
    public static final String PERIOD = "period";

    private View view;
    private Period period;

    public DashboardActivitiesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dashboard_activities, container, false);

        initializeFragment();

        return view;
    }

    private void initializeFragment() {
        Bundle arguments = getArguments();
        int thisValue = arguments.getInt(THIS);
        int lastValue = arguments.getInt(LAST);
        Period period = Period.values()[arguments.getInt(PERIOD)];

        String periodText = getPeriodText(period);

        getThisValueView().setText(Integer.toString(thisValue));
        getLastValueView().setText(Integer.toString(lastValue));

        getThisPeriodView().setText(getString(R.string.current) + " " + periodText);
        getLastPeriodView().setText(getString(R.string.last) + " " + periodText);
    }

    private String getPeriodText(Period period) {
        String value = "";

        switch (period) {
            case MONTH:
                value = getString(R.string.activity_period_month);
                break;
            case TOTAL:
                value = getString(R.string.activity_period_total);
                break;
            case WEEK:
                value = getString(R.string.activity_period_week);
                break;
            case YEAR:
                value = getString(R.string.activity_period_year);
                break;
        }

        return value;
    }


    private TextView getThisValueView() { return (TextView)view.findViewById(R.id.dashboard_activities_this); }
    private TextView getThisPeriodView() { return (TextView)view.findViewById(R.id.dashboard_activities_this_period); }
    private TextView getLastValueView() { return (TextView)view.findViewById(R.id.dashboard_activities_last); }
    private TextView getLastPeriodView() { return (TextView)view.findViewById(R.id.dashboard_activities_last_period); }

    public enum Period {
        WEEK,
        MONTH,
        YEAR,
        TOTAL
    }

}
