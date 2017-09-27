package com.climbershangout.entities;

import android.graphics.PointF;

/**
 * Created by lyuboslav on 9/27/2017.
 */

public class Hold {
    //Members
    public static final int START_HOLD = 1;
    public static final int HOLD = 2;
    public static final int FINISH_HOLD = 3;
    public static final int FOOT_HOLD = 4;

    private int type;
    private PointF center;
    private int radius;
    private int color;

    //Properties
    public int getColor() {
        return color;
    }

    public int getType() {
        return type;
    }

    public PointF getCenter() {
        return center;
    }

    public int getRadius() {
        return radius;
    }

    //Constructor
    public Hold(int type, PointF center, int radius, int color){
        this.type = type;
        this.center = center;
        this.radius = radius;
        this.color = color;
    }

    //Methods
    public static int getStrokeSize(int type){
        int strokeSize = 3;
        switch (type){
            case START_HOLD:
                strokeSize = 2;
                break;
            case FINISH_HOLD:
                strokeSize = 5;
                break;
            case FOOT_HOLD:
                strokeSize = 10;
                break;
        }

        return strokeSize;
    }

}
