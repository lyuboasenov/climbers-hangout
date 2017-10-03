package com.climbershangout.climbershangout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.climbershangout.climbershangout.climb.AddRouteActivity;
import com.climbershangout.climbershangout.climb.ChooseImageSourceDialog;
import com.climbershangout.climbershangout.climb.ClimbListFragment;
import com.climbershangout.climbershangout.climb.RouteCreationHelper;
import com.climbershangout.climbershangout.dashboard.DashboardFragment;
import com.climbershangout.climbershangout.debug.DebugFragment;
import com.climbershangout.climbershangout.settings.SettingsFragment;
import com.climbershangout.climbershangout.trainings.CounterFragment;
import com.climbershangout.climbershangout.trainings.TrainingListFragment;

import java.util.Dictionary;
import java.util.Hashtable;

public class MainActivity extends BaseActivity {

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private NavigationView drawerView;
    private ActionBarDrawerToggle drawerToggle;
    private Menu menu;
    private TextView drawerHeaderTitleView;
    private int optionMenu = R.menu.main_menu;
    private RouteCreationHelper routeCreationHelper;

    private Dictionary<Integer, Class> navigationMap;
    private Dictionary<Integer, Class> jumpToNavigationMap;
    private Dictionary<Integer, Integer> optionMenuMap;
    private Dictionary<Integer, CharSequence> navigationTitle;

    private static final int RC_SIGN_IN = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //check for login
        if(!User.getUser().isLoggedIn()) {
            //need login
            Intent loginIntent = new Intent(this,LoginActivity.class);
            startActivityForResult(loginIntent, RC_SIGN_IN);
        }

        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        drawerView = (NavigationView) findViewById(R.id.main_navigation);

        // Setup drawer view
        setupDrawerContent(drawerView);

        drawerHeaderTitleView = (TextView) drawerView.getHeaderView(0).findViewById(R.id.header_title);
        drawerHeaderTitleView.setText(User.getUser().getUsername());

        selectDrawerItem(drawerView.getMenu().getItem(0));

        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            drawerToggle = new ActionBarDrawerToggle(this, drawer, R.string.drawer_open, R.string.drawer_close) {
                public void onDrawerClosed(View view) {
                    supportInvalidateOptionsMenu();
                }

                public void onDrawerOpened(View drawerView) {
                    supportInvalidateOptionsMenu();
                }
            };
            drawerToggle.setDrawerIndicatorEnabled(true);
            drawer.setDrawerListener(drawerToggle);
            drawerToggle.syncState();
        }

        //Initialize BoardManager
        BoardManager.getBoardManager().addBoardCallback(
                new BoardManager.IBoardCallback() {
                    @Override
                    public void onReceivedData(byte[] bytes) {

                    }

                    @Override
                    public void onBoardAttachedDetached(final boolean boardAttachedDetached) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            updateBoardMenuItem(boardAttachedDetached);
                            }
                        });
                    }
        });
    }

    private void updateBoardMenuItem(boolean boardAttachedDetached) {
        MenuItem boardMenuItem = menu.findItem(R.id.action_board);
        boardMenuItem.setVisible(boardAttachedDetached);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(optionMenu, menu);
        this.menu = menu;
        updateBoardMenuItem(BoardManager.getBoardManager().isBoardAttached());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.

        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
            default:
                selectDrawerItem(item);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            if (resultCode == RESULT_OK) {
                drawerHeaderTitleView.setText(User.getUser().getUsername());
            } else {
                //not login exit application
                finish();
                System.exit(0);
            }
        } else if(requestCode == RouteCreationHelper.RC_NEW_ROUTE_REQUEST_IMAGE && resultCode == RESULT_OK) {
            startActivityForResult(routeCreationHelper.getCreateRouteActivityIntent(data), RouteCreationHelper.RC_CREATE_ROUTE);
        } else if(requestCode == RouteCreationHelper.RC_CREATE_ROUTE && resultCode == RESULT_OK) {
            saveRoute(data);
        }
    }

    private void saveRoute(Intent data) {
        //TODO:Save route
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        int navigationId = menuItem.getItemId();

        if (navigationId == R.id.nav_sign_out) {
            signOut();
            return;
        } else if (navigationId == R.id.action_add_route) {
            new ChooseImageSourceDialog(this)
                    .setFromCameraOnClickListener(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            routeCreationHelper = new RouteCreationHelper(getBaseContext(), RouteCreationHelper.FROM_CAMERA);
                            startActivityForResult(routeCreationHelper.getRequestImageIntent(), RouteCreationHelper.RC_NEW_ROUTE_REQUEST_IMAGE);
                        }
                    })
                    .setFromGalleryOnClickListener(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            routeCreationHelper = new RouteCreationHelper(getBaseContext(), RouteCreationHelper.FROM_GALLERY);
                            startActivityForResult(routeCreationHelper.getRequestImageIntent(), RouteCreationHelper.RC_NEW_ROUTE_REQUEST_IMAGE);
                        }
                    })
                    .show();
            //return;
        }

        // Create a new fragment and specify the fragment to show based on nav item clicked
        Class fragmentClass = getNavigationFragmentClass(navigationId, DashboardFragment.class);

        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        fragmentManager.executePendingTransactions();

        markDrawerItemSelected(navigationId);

        // Set action bar title
        setTitle(getNavigationTitle(navigationId, menuItem.getTitle()));

        // Close the navigation drawer
        drawer.closeDrawers();

        //Invalidate option menu so it can be updated.
        optionMenu = getFragmentOptionMenu(navigationId, R.menu.main_menu);
        invalidateOptionsMenu();

        Class jumpToActivity = getJumpToActivity(navigationId);
        if (null != jumpToActivity) {
            startActivityForResult(new Intent(this, jumpToActivity), RequestCode.RC_ADD_ROUTE);
        }
    }

    private void markDrawerItemSelected(int navigationId) {
        uncheckMenu(drawerView.getMenu());

        if(navigationId == R.id.action_settings) {
            // Highlight the selected item has been done by NavigationView
            navigationId = R.id.nav_settings_fragment;
        }

        drawerView.setCheckedItem(navigationId);
        MenuItem item = (MenuItem) drawerView.findViewById(navigationId);
        if(null != item)
            item.setChecked(true);
    }

    private void uncheckMenu(Menu menu) {
        for(int i = 0; i < menu.size(); i++){
            MenuItem item = menu.getItem(i).setChecked(false);
            if(item.hasSubMenu()){
                uncheckMenu(item.getSubMenu());
            }
        }
    }

    private int getFragmentOptionMenu(int navigationId, int defaultOptionMenu) {
        if (null == optionMenuMap) {
            optionMenuMap = new Hashtable<>();
            optionMenuMap.put(R.id.nav_trainings_fragment, R.menu.training_menu);
        }

        Integer optionMenuId = optionMenuMap.get(navigationId);

        return optionMenuId == null ? defaultOptionMenu : optionMenuId;
    }

    private Class getNavigationFragmentClass(int navigationId, Class defaultNavigationFragment) {
        if (navigationMap == null) {
            navigationMap = new Hashtable<>();
            navigationMap.put(R.id.nav_dashboard_fragment, DashboardFragment.class);
            navigationMap.put(R.id.nav_route_list_fragment, ClimbListFragment.class);
            navigationMap.put(R.id.nav_trainings_fragment, TrainingListFragment.class);
            navigationMap.put(R.id.nav_debug_fragment, DebugFragment.class);
            navigationMap.put(R.id.nav_settings_fragment, SettingsFragment.class);
            navigationMap.put(R.id.action_add_route, ClimbListFragment.class);
            navigationMap.put(R.id.action_counter, CounterFragment.class);
            navigationMap.put(R.id.action_settings, SettingsFragment.class);
        }

        Class fragmentClass = navigationMap.get(navigationId);

        return fragmentClass == null ? defaultNavigationFragment : fragmentClass;
    }

    private Class getJumpToActivity(int navigationId) {
        if (jumpToNavigationMap == null) {
            jumpToNavigationMap = new Hashtable<>();
            //jumpToNavigationMap.put(R.id.action_add_route, AddRouteActivity.class);
        }

        return jumpToNavigationMap.get(navigationId);
    }

    private CharSequence getNavigationTitle(int navigationId, CharSequence defaultTitle) {
        if(navigationTitle == null){
            navigationTitle = new Hashtable<>();
            navigationTitle.put(R.id.action_add_route, getString(R.string.action_route_list));
        }

        CharSequence title = navigationTitle.get(navigationId);

        return title == null ? defaultTitle : title;
    }

    private void signOut() {
        User.getUser().signOut();
        recreate();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
        return toggle;
    }
}