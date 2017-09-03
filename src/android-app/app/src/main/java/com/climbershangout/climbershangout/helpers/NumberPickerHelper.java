package com.climbershangout.climbershangout.helpers;

import com.shawnlin.numberpicker.NumberPicker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by lyuboslav on 2/5/2017.
 */

public class NumberPickerHelper {

    public static NumberPicker initializePickerValues(
            NumberPicker picker,
            int minValue,
            int maxValue,
            int value,
            NumberPicker.Formatter formatter) {
        picker.setMinValue(minValue);
        picker.setMaxValue(maxValue);
        picker.setValue(value);
        picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        picker.setDividerDistance(70);

        if (null != formatter)
            picker.setFormatter(formatter);

        try {
            Method method = picker.getClass().getDeclaredMethod("changeValueByOne", boolean.class);
            method.setAccessible(true);
            method.invoke(picker, true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return picker;
    }
}
