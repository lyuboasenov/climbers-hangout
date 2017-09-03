package com.climbershangout.climbershangout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.climbershangout.climbershangout.settings.SettingsKeys;

/**
 * Created by lyuboslav on 2/5/2017.
 */

public class BaseActivity extends AppCompatActivity {

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
}
