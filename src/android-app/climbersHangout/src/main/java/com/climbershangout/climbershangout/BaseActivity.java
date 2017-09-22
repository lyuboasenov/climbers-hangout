package com.climbershangout.climbershangout;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.climbershangout.climbershangout.settings.SettingsKeys;


/**
 * Created by lyuboslav on 2/5/2017.
 */

public class BaseActivity extends AppCompatActivity {

    //Members
    private Boolean exit = false;

    //Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences(SettingsKeys.SHARED_PREF_FILE, Context.MODE_PRIVATE);
        int themeIndex = preferences.getInt(SettingsKeys.Main.THEME, 0);
        int theme = R.style.LightTheme;

        switch (themeIndex) {
            case 1:
                theme = R.style.DarkTheme;
                break;
            case 2:
                theme = R.style.LightGreenTheme;
                break;
            case 3:
                theme = R.style.LightBlueTheme;
                break;
        }

        setTheme(theme);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }
}
