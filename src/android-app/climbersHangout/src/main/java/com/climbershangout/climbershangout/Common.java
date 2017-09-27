package com.climbershangout.climbershangout;

import android.graphics.PointF;

/**
 * Created by lyuboslav on 9/27/2017.
 */

public class Common {
    public static float calculateDistance(PointF start, PointF end, float width, float height){
        return (float)Math.sqrt(
                Math.pow((end.y - start.y) * height, 2) +
                Math.pow((end.x - start.x) * width, 2));
    }
}
