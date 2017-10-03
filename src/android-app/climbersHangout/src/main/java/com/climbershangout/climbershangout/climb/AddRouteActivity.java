package com.climbershangout.climbershangout.climb;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CheckBox;
import android.widget.EditText;

import com.climbershangout.climbershangout.R;

public class AddRouteActivity extends AppCompatActivity {

    //Members
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private Menu menu;
    private Uri imageUri;

    public static final String FILE_URI_ARG = "FILE_URI_ARG";

    //Properties
    private ViewPager getViewPager() { if (null == viewPager) { viewPager = (ViewPager) findViewById(R.id.add_route_viewpager); }return viewPager; }
    private TabLayout getTabLayout() { if (null == tabLayout) { tabLayout = (TabLayout) findViewById(R.id.add_route_tabs); } return tabLayout; }
    private Menu getMenu() { return menu; }
    private void setMenu(Menu menu) { this.menu = menu; }
    private SectionsPagerAdapter getSectionsPagerAdapter() { return sectionsPagerAdapter; }
    private Toolbar getToolbar() { return (Toolbar) findViewById(R.id.toolbar); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        initializeActionBar();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        getViewPager().setAdapter(getSectionsPagerAdapter());
        getTabLayout().setupWithViewPager(getViewPager());

        Intent intent = getIntent();
        imageUri = Uri.parse(intent.getStringExtra(FILE_URI_ARG));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_route_menu, menu);
        setMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.

        switch (item.getItemId()) {
            case R.id.action_add_route:
                addRoute();
                return true;
            case R.id.action_discard_route:
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

        actionBar.setTitle(getString(R.string.add_route_title));

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void cancel(){
        finish();
    }

    private void addRoute(){
        finish();
    }

    public static class SummaryFragment extends Fragment {

        //Members
        private EditText nameView;
        private EditText descriptionView;
        private CheckBox openFeetView;

        //Properties
        public EditText getNameView() {
            if (nameView == null) { nameView = (EditText)getActivity().findViewById(R.id.add_route_name); }
            return nameView;
        }

        public EditText getDescriptionView() {
            if (descriptionView == null) { descriptionView = (EditText)getActivity().findViewById(R.id.add_route_descr); }
            return descriptionView;
        }

        public CheckBox getOpenFeetView() {
            if (openFeetView == null) { openFeetView = (CheckBox) getActivity().findViewById(R.id.add_route_open_feet); }
            return openFeetView;
        }

        //Constructors
        public SummaryFragment() {
        }

        //Methods
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_add_route_summary, container, false);

            return rootView;
        }
    }
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        //Constructor
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            Fragment selectedFragment;
            switch (position){
                case 1:
                    selectedFragment = new AddRouteDetailedFragment(imageUri);
                    break;
                default:
                    selectedFragment = new SummaryFragment();
            }

            return selectedFragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.action_add_route_summary);
                case 1:
                    return getString(R.string.action_add_route_details);
            }
            return null;
        }
    }
}

