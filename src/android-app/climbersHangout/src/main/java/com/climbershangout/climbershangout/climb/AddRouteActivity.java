package com.climbershangout.climbershangout.climb;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.net.Uri;
import android.provider.MediaStore;
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
import android.util.Size;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.climbershangout.climbershangout.R;
import com.climbershangout.climbershangout.StorageHelper;
import com.climbershangout.climbershangout.common.Camera;
import com.climbershangout.climbershangout.common.CameraListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class AddRouteActivity extends AppCompatActivity implements Camera {

    //Members
    private String tempImageFile;
    public static final int RC_IMAGE_CAPTURE = 1264;
    public static final int RC_ADD_ROUTE = 1397;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private Menu menu;
    private List<CameraListener> cameraListenerList;

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
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);

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
            for (Iterator<CameraListener> i = cameraListenerList.iterator(); i.hasNext();) {
                CameraListener item = i.next();
                item.onCameraPhotoTaken(tempImageFile);
            }
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

    @Override
    public void takePhoto(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = StorageHelper.getStorageHelper().createTempFile();
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


    @Override
    public void addListener(CameraListener listener) {
        if(cameraListenerList == null) {
            cameraListenerList = new ArrayList<>();
        }
        cameraListenerList.add(listener);
    }

    @Override
    public void removeListener(CameraListener listener) {
        if(cameraListenerList != null) {
            cameraListenerList.remove(listener);
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

    public static class DetailedFragment extends Fragment implements CameraListener {

        //Members
        private ImageView overlayView;
        private ImageView backgroundView;
        private TextView lengthTextView;
        private Camera camera;
        private RouteSchemaCreator schemaCreator = new RouteSchemaCreator();
        private boolean isPhotoTaken = false;


        //Properties
        public ImageView getOverlayView() {
            if (overlayView == null) { overlayView = (ImageView) getActivity().findViewById(R.id.add_route_image_overlay); }
            return overlayView;
        }

        public ImageView getBackgroundView() {
            if (backgroundView == null) { backgroundView = (ImageView) getActivity().findViewById(R.id.add_route_image_background); }
            return backgroundView;
        }

        public TextView getLengthTextView() {
            if (lengthTextView == null) { lengthTextView = (TextView) getActivity().findViewById(R.id.add_route_length); }
            return lengthTextView;
        }

        public Camera getCamera() {
            return camera;
        }

        public void setCamera(Camera camera) {
            this.camera = camera;
        }

        public RouteSchemaCreator getSchemaCreator() {
            return schemaCreator;
        }

        //Constructor
        public DetailedFragment() {

        }

        public DetailedFragment(Camera camera) {
            setCamera(camera);
            getCamera().addListener(this);
        }

        //Methods
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_add_route_details, container, false);

            return rootView;
        }

        @Override
        public void onResume() {
            super.onResume();
            getOverlayView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageViewClicked();
                }
            });
            getOverlayView().setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    boolean handled = false;
                    if (isPhotoTaken) {
                        PointF point = new PointF(motionEvent.getX(), motionEvent.getY());
                        switch (motionEvent.getAction()) {
                            case android.view.MotionEvent.ACTION_DOWN:
                                imageViewDown(point);
                                handled = true;
                                break;
                            case MotionEvent.ACTION_MOVE:
                                imageViewMove(point);
                                handled = true;
                                break;
                            case android.view.MotionEvent.ACTION_UP:
                                imageViewUp(point, motionEvent.getDownTime());
                                handled = true;
                                break;
                        }
                    }
                    return handled;
                }
            });
        }

        Random rand = new Random(System.currentTimeMillis());
        private void imageViewUp(PointF point, long duration) {

            if(getSchemaCreator().finishAddHold(point, Color.BLUE, rand.nextInt(5))){
                getOverlayView().setImageBitmap(getSchemaCreator().getOverlayBitmap());
                getLengthTextView().setText("Length: " + getSchemaCreator().getLength());
            }
        }

        private void imageViewMove(PointF point) {
            if(getSchemaCreator().addHoldProgress(point)){
                getOverlayView().setImageBitmap(getSchemaCreator().getOverlayBitmap());
            } else {
                warnUserToClickInBounds();
            }

        }

        private void warnUserToClickInBounds() {
            Toast.makeText(getContext(), R.string.warning_click_in_bounds, Toast.LENGTH_SHORT).show();
        }

        private void imageViewDown(PointF point) {
            if(getSchemaCreator().startAddHold(point)){
                getOverlayView().setImageBitmap(getSchemaCreator().getOverlayBitmap());
            } else {
                warnUserToClickInBounds();
            }
        }

        private void imageViewClicked() {
            if(!isPhotoTaken) {
                isPhotoTaken = true;
                getCamera().takePhoto();
            }
        }

        @Override
        public void onCameraPhotoTaken(String photoPath) {
            getSchemaCreator().initialize(photoPath, new Size(getOverlayView().getWidth(), getOverlayView().getHeight()));
            getOverlayView().setImageBitmap(getSchemaCreator().getOverlayBitmap());
            getBackgroundView().setImageBitmap(getSchemaCreator().getBitmap());
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private Camera camera;

        public Camera getCamera() {
            return camera;
        }

        public void setCamera(Camera camera) {
            this.camera = camera;
        }

        //Constructor
        public SectionsPagerAdapter(FragmentManager fm, Camera camera) {
            super(fm); setCamera(camera);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            Fragment selectedFragment;
            switch (position){
                case 1:
                    selectedFragment = new DetailedFragment(getCamera());
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

