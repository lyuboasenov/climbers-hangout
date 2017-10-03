package com.climbershangout.climbershangout;

import android.database.Cursor;
import android.graphics.PointF;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.SizeF;

/**
 * Created by lyuboslav on 9/27/2017.
 */

public class Common {

    public static final int FORMAT_DEBUG = 4;
    public static final int FORMAT_VERBOSE = 3;
    public static final int FORMAT_INFO = 2;
    public static final int FORMAT_NORMAL = 1;
    public static final int FORMAT_QUIET = 0;


    public static float calculateDistance(PointF start, PointF end){
        return calculateDistance(start, end, 1, 1);
    }

    public static float calculateDistance(PointF start, PointF end, float width, float height){
        return (float)Math.sqrt(
                Math.pow((end.y - start.y) * height, 2) +
                Math.pow((end.x - start.x) * width, 2));
    }

    public static PointF calculateMidPoint(PointF start, PointF end) {
        float x = start.x + end.x;
        float y = start.y + end.y;
        return new PointF(x / 2, y / 2);
    }

    public static PointF normalizeCoordinates(PointF coordinates, float width, float height){
        return new PointF(coordinates.x / width, coordinates.y / height);
    }

    public static PointF normalizeCoordinates(PointF coordinates, SizeF size){
        return normalizeCoordinates(coordinates, size.getWidth(), size.getHeight());
    }

    public static boolean verifyHoldInBounds(PointF holdCoordinates, SizeF size, float marginTop, float marginLeft) {
        return holdCoordinates.x > marginLeft && holdCoordinates.x < marginLeft + size.getWidth()
                && holdCoordinates.y > marginTop && holdCoordinates.y < marginTop + size.getHeight();
    }

    public static SizeF calculateSizeToFit(SizeF containerSize, SizeF sizeF) {
        float aspect = sizeF.getWidth() / sizeF.getHeight();
        float containerAspect = containerSize.getWidth() / containerSize.getHeight();
        SizeF sizeToFit = null;

        if(aspect > containerAspect){
            sizeF = new SizeF(containerSize.getWidth(), containerSize.getWidth() * aspect);
        } else {
            sizeF = new SizeF(containerSize.getHeight() * aspect, containerSize.getHeight());
        }

        return sizeF;
    }
}
