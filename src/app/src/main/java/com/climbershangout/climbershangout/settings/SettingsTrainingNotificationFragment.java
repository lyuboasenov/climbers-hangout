package com.climbershangout.climbershangout.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import com.climbershangout.climbershangout.helpers.NumberPickerHelper;
import com.climbershangout.climbershangout.R;
import com.shawnlin.numberpicker.NumberPicker;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class SettingsTrainingNotificationFragment extends Fragment {

    private static View view;

    public SettingsTrainingNotificationFragment() {
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
            view = inflater.inflate(R.layout.fragment_settings_training_notification, container, false);
        } catch (InflateException e) {
            /* map is already there, just return view as it is */
            e.printStackTrace();
        }

        initializeCheckBoxes();
        initializePickers();
        initializeOther();

        return view;
    }

    private void initializeOther() {
        SharedPreferences preferences = getActivity().getSharedPreferences(SettingsKeys.SHARED_PREF_FILE, Context.MODE_PRIVATE);

        getVolumeBar().setMax(ToneGenerator.MAX_VOLUME);
        getVolumeBar().setProgress(preferences.getInt(SettingsKeys.Notifications.TONE_V0LUME, ToneGenerator.MAX_VOLUME));
        getVolumeBar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                getActivity()
                        .getSharedPreferences(SettingsKeys.SHARED_PREF_FILE, Context.MODE_PRIVATE)
                        .edit()
                        .putInt(SettingsKeys.Notifications.TONE_V0LUME, i)
                        .apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initializeCheckBoxes() {
        SharedPreferences preferences = getActivity().getSharedPreferences(SettingsKeys.SHARED_PREF_FILE, Context.MODE_PRIVATE);

        getVibrateCheckBox().setChecked(preferences.getBoolean(SettingsKeys.Notifications.VIBRATE, false));
        getVibrateCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getActivity()
                        .getSharedPreferences(SettingsKeys.SHARED_PREF_FILE, Context.MODE_PRIVATE)
                        .edit()
                        .putBoolean(SettingsKeys.Notifications.VIBRATE, b)
                        .apply();
            }
        });

        getPrepCheckBox().setChecked(preferences.getBoolean(SettingsKeys.Notifications.PREP, true));
        getPrepCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getPrepTimePicker().setVisibility(b ? View.VISIBLE : View.INVISIBLE);
                getActivity()
                        .getSharedPreferences(SettingsKeys.SHARED_PREF_FILE, Context.MODE_PRIVATE)
                        .edit()
                        .putBoolean(SettingsKeys.Notifications.PREP, b)
                        .apply();
            }
        });

        getWorkCheckBox().setChecked(preferences.getBoolean(SettingsKeys.Notifications.WORK, true));
        getWorkCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getWorkTimePicker().setVisibility(b ? View.VISIBLE : View.INVISIBLE);
                getActivity()
                        .getSharedPreferences(SettingsKeys.SHARED_PREF_FILE, Context.MODE_PRIVATE)
                        .edit()
                        .putBoolean(SettingsKeys.Notifications.WORK, b)
                        .apply();
            }
        });

        getRestCheckBox().setChecked(preferences.getBoolean(SettingsKeys.Notifications.REST, true));
        getRestCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getRestTimePicker().setVisibility(b ? View.VISIBLE : View.INVISIBLE);
                getActivity()
                        .getSharedPreferences(SettingsKeys.SHARED_PREF_FILE, Context.MODE_PRIVATE)
                        .edit()
                        .putBoolean(SettingsKeys.Notifications.REST, b)
                        .apply();
            }
        });

        getPauseCheckBox().setChecked(preferences.getBoolean(SettingsKeys.Notifications.PAUSE, true));
        getPauseCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getPauseTimePicker().setVisibility(b ? View.VISIBLE : View.INVISIBLE);
                getActivity()
                        .getSharedPreferences(SettingsKeys.SHARED_PREF_FILE, Context.MODE_PRIVATE)
                        .edit()
                        .putBoolean(SettingsKeys.Notifications.PAUSE, b)
                        .apply();
            }
        });
    }

    private void initializePickers() {
        SharedPreferences preferences = this.getActivity().getSharedPreferences(SettingsKeys.SHARED_PREF_FILE, Context.MODE_PRIVATE);

        NumberPickerHelper
                .initializePickerValues(getPrepTimePicker(), 1, 60, preferences.getInt(SettingsKeys.Notifications.PREP_TIME, 10), null)
                .setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                getActivity()
                        .getSharedPreferences(SettingsKeys.SHARED_PREF_FILE, Context.MODE_PRIVATE)
                        .edit()
                        .putInt(SettingsKeys.Notifications.PREP_TIME, newVal)
                        .apply();
            }
        });

        NumberPickerHelper
                .initializePickerValues(getWorkTimePicker(), 1, 60, preferences.getInt(SettingsKeys.Notifications.WORK_TIME, 3), null)
                .setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                getActivity()
                        .getSharedPreferences(SettingsKeys.SHARED_PREF_FILE, Context.MODE_PRIVATE)
                        .edit()
                        .putInt(SettingsKeys.Notifications.WORK_TIME, newVal)
                        .apply();
            }
        });

        NumberPickerHelper
                .initializePickerValues(getRestTimePicker(), 1, 60, preferences.getInt(SettingsKeys.Notifications.REST_TIME, 3), null)
                .setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                getActivity()
                        .getSharedPreferences(SettingsKeys.SHARED_PREF_FILE, Context.MODE_PRIVATE)
                        .edit()
                        .putInt(SettingsKeys.Notifications.REST_TIME, newVal)
                        .apply();
            }
        });

        NumberPickerHelper
                .initializePickerValues(getPauseTimePicker(), 1, 60, preferences.getInt(SettingsKeys.Notifications.PAUSE_TIME, 10), null)
                .setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                getActivity()
                        .getSharedPreferences(SettingsKeys.SHARED_PREF_FILE, Context.MODE_PRIVATE)
                        .edit()
                        .putInt(SettingsKeys.Notifications.PAUSE_TIME, newVal)
                        .apply();
            }
        });
    }

    private SeekBar getVolumeBar() { return (SeekBar)view.findViewById(R.id.settings_notification_tone_volume); }

    private NumberPicker getPrepTimePicker() { return (NumberPicker)view.findViewById(R.id.settings_notification_prep_time); }
    private NumberPicker getWorkTimePicker() { return (NumberPicker)view.findViewById(R.id.settings_notification_work_time); }
    private NumberPicker getRestTimePicker() { return (NumberPicker)view.findViewById(R.id.settings_notification_rest_time); }
    private NumberPicker getPauseTimePicker() { return (NumberPicker)view.findViewById(R.id.settings_notification_pause_time); }

    private CheckBox getVibrateCheckBox() { return (CheckBox)view.findViewById(R.id.settings_notification_vibrate); }
    private CheckBox getPrepCheckBox() { return (CheckBox)view.findViewById(R.id.settings_notification_prep); }
    private CheckBox getWorkCheckBox() { return (CheckBox)view.findViewById(R.id.settings_notification_work); }
    private CheckBox getRestCheckBox() { return (CheckBox)view.findViewById(R.id.settings_notification_rest); }
    private CheckBox getPauseCheckBox() { return (CheckBox)view.findViewById(R.id.settings_notification_pause); }

}
