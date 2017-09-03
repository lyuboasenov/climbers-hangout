package com.climbershangout.climbershangout.helpers;

/**
 * Created by lyuboslav on 2/18/2017.
 */

public class TimeFormatter {
    public static String format(float time) {
        return format(time, false, true, true, false);
    }

    public static String format(float time, boolean showHours, boolean showMinutes, boolean showSeconds, boolean showMilliseconds) {

        String format = "%1$02d";
        StringBuilder formatedValue = new StringBuilder();
        int hours, minutes;

        if(showHours) {
            hours = (int)time / 3600;
            formatedValue.append(String.format(format, hours));
            formatedValue.append(":");
            time -= hours * 3600;
        }
        if (showMinutes || showHours) {
            minutes = (int)time / 60;
            formatedValue.append(String.format(format, minutes));
            formatedValue.append(":");
            time -= minutes * 60;
        }
        if (showSeconds || showMinutes || showHours) {
            formatedValue.append(String.format(format, (int)time));
        }
        if (showMilliseconds) {
            formatedValue.append(".");
            formatedValue.append(String.format("%1$03d", (int)(time - (int)time) * 1000));
        }

        return formatedValue.toString();
    }
}
