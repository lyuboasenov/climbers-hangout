package com.climbershangout.climbershangout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.climbershangout.climbershangout.dashboard.DashboardFragment;
import com.climbershangout.climbershangout.debug.DebugFragment;
import com.climbershangout.climbershangout.settings.SettingsFragment;
import com.climbershangout.climbershangout.settings.SettingsKeys;
import com.climbershangout.climbershangout.trainings.CounterFragment;
import com.climbershangout.climbershangout.trainings.TrainingListFragment;
import com.google.android.gms.auth.api.Auth;

public class MainActivity extends BaseActivity {

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private NavigationView drawerView;
    private ActionBarDrawerToggle drawerToggle;
    private Menu menu;
    private TextView drawerHeaderTitleView;
    private int optionMenu = R.menu.main_menu;

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

        if(resultCode == RESULT_OK){
            drawerHeaderTitleView.setText(User.getUser().getUsername());
        } else {
            //not login exit application
            finish();
            System.exit(0);
        }
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
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass = DashboardFragment.class;
        optionMenu = R.menu.main_menu;

        if (BuildConfig.DEBUG) {

        }

        switch(menuItem.getItemId()) {
            case R.id.nav_dashboard_fragment:
                fragmentClass = DashboardFragment.class;
                break;
            case R.id.nav_trainings_fragment:
                fragmentClass = TrainingListFragment.class;
                optionMenu = R.menu.training_menu;
                break;
            case R.id.nav_counter_fragment:
                fragmentClass = CounterFragment.class;
                break;
            case R.id.nav_debug_fragment:
                fragmentClass = DebugFragment.class;
                break;
            /*case R.id.nav_goals_fragment:
                fragmentClass = GoalsFragment.class;
                break;
            case R.id.nav_friends_fragment:
                fragmentClass = FriendsFragment.class;
                break;*/
            case R.id.nav_settings_fragment:
                fragmentClass = SettingsFragment.class;
                break;
            case R.id.action_counter:
                fragmentClass = CounterFragment.class;
                break;
            case R.id.action_settings:
                fragmentClass = SettingsFragment.class;
                break;
            case R.id.nav_sign_out:
                signOut();
                return;
            default:
                fragmentClass = DashboardFragment.class;
        }

        try {
            if(null == fragment)
                fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        fragmentManager.executePendingTransactions();

        for(int i = 0; i < drawerView.getMenu().size(); i++){
            drawerView.getMenu().getItem(i).setChecked(false);
        }

        if(menuItem.getItemId() == R.id.action_settings) {
            // Highlight the selected item has been done by NavigationView
            drawerView.setCheckedItem(R.id.nav_settings_fragment);
            MenuItem item = (MenuItem) drawerView.findViewById(R.id.nav_settings_fragment);
            if(null != item)
                item.setChecked(true);
        } else {
            // Highlight the selected item has been done by NavigationView
            menuItem.setChecked(true);
            drawerView.setCheckedItem(menuItem.getItemId());
        }

        // Set action bar title
        setTitle(menuItem.getTitle());

        // Close the navigation drawer
        drawer.closeDrawers();

        //Invalidate option menu so it can be updated.
        invalidateOptionsMenu();
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