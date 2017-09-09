package com.climbershangout.climbershangout.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.climbershangout.climbershangout.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class SettingsMainFragment extends Fragment {

    private static View view;

    public SettingsMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_settings_main, container, false);
        } catch (InflateException e) {
            /* map is already there, just return view as it is */
            e.printStackTrace();
        }

        initializeThemes();

        return view;
    }

    private void initializeThemes() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
            R.array.available_theme_list, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        getThemeSpinner().setAdapter(adapter);

        SharedPreferences preferences = this.getActivity().getSharedPreferences(SettingsKeys.SHARED_PREF_FILE, Context.MODE_PRIVATE);

        getThemeSpinner().setSelection(preferences.getInt(SettingsKeys.Main.THEME, 0));
        getThemeSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SharedPreferences preferences = getActivity().getSharedPreferences(SettingsKeys.SHARED_PREF_FILE, Context.MODE_PRIVATE);

                if(preferences.getInt(SettingsKeys.Main.THEME, 0) != i) {
                    preferences
                        .edit()
                        .putInt(SettingsKeys.Main.THEME, i)
                        .apply();

                    changeTheme(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void changeTheme(int themeIndex) {
        int theme = R.style.LightTheme;

        switch (themeIndex){
            case 1:
                theme = R.style.DarkTheme;
        }

        getActivity().setTheme(theme);
        getActivity().recreate();
    }


    private Spinner getThemeSpinner() { return (Spinner)view.findViewById(R.id.settings_theme); }
}
