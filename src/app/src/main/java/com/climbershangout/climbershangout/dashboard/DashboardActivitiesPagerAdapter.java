package com.climbershangout.climbershangout.dashboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by lyuboslav on 2/18/2017.
 */

public class DashboardActivitiesPagerAdapter extends FragmentStatePagerAdapter {

    private int[] values;

    public DashboardActivitiesPagerAdapter(FragmentManager fragmentManager, int[] values) {
        super(fragmentManager);
        this.values = values;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        DashboardActivitiesFragment fragment = new DashboardActivitiesFragment();

        Bundle args = new Bundle();
        args.putInt(DashboardActivitiesFragment.THIS, values[position * 2]);
        args.putInt(DashboardActivitiesFragment.LAST, values[position * 2 + 1]);
        args.putInt(DashboardActivitiesFragment.PERIOD, position);
        fragment.setArguments(args);

        return fragment;
    }
}
