package com.climbershangout.climbershangout.helpers;

import com.shawnlin.numberpicker.NumberPicker;

/**
 * Created by lyuboslav on 1/21/2017.
 */

public class TimeNumberPickerFormatter implements NumberPicker.Formatter {
    private boolean decimal;

    public TimeNumberPickerFormatter(boolean decimal) {
        this.decimal = decimal;
    }

    @Override
    public String format(int i) {
        if(decimal)
            return TimeFormatter.format(i);
        else
            return String.format("%1$02d:00", i);
    }

    public static String format(boolean decimal, int i) {
        if(decimal)
            return TimeFormatter.format(i);
        else
            return String.format("%1$02d:00", i);
    }
}