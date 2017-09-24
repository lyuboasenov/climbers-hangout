package com.climbershangout.climbershangout.climb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.FileProvider;
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

import android.widget.ImageView;
import android.widget.TextView;

import com.climbershangout.climbershangout.R;
import com.climbershangout.climbershangout.StorageManager;

import java.io.File;
import java.io.IOException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

public class AddRouteActivity extends AppCompatActivity {

    private String tempImageFile;
    public static final int RC_IMAGE_CAPTURE = 1264;
    public static final int RC_ADD_ROUTE = 1397;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private Menu menu;

    //Properties
    private ViewPager getViewPager() { if (null == viewPager){ viewPager = (ViewPager) findViewById(R.id.add_route_viewpager); }return viewPager; }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap imageBitmap = BitmapFactory.decodeFile(tempImageFile, bmOptions);

            ((ImageView)findViewById(R.id.iv_taken_image)).setImageBitmap(imageBitmap);
        }
    }

    private void initializeActionBar() {
        setSupportActionBar(getToolbar());
        final ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle(getString(R.string.add_route_title));

        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void takePhoto(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = StorageManager.getStorageManager().createTempFile();
                tempImageFile = photoFile.getAbsolutePath();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.climbershangout.climbershangout.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, RC_IMAGE_CAPTURE);
            }
        }
    }

    private void cancel(){
        finish();
    }

    private void addRoute(){

        finish();
    }

    public static class SummaryFragment extends Fragment {

        public SummaryFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_add_route_summary, container, false);

            return rootView;
        }
    }

    public static class DetailedFragment extends Fragment {

        public DetailedFragment() {
        }

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
                    selectedFragment = new DetailedFragment();
                    break;
                default:
                    selectedFragment = new SummaryFragment();
            }

            return selectedFragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
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
