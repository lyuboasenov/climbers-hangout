package com.climbershangout.climbershangout.climb;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.SizeF;

import com.climbershangout.climbershangout.Common;
import com.climbershangout.entities.Hold;

/**
 * Created by lyuboslav on 9/30/2017.
 */

public class HoldHelper {

    private static void drawCircle(Canvas canvas, SizeF canvasSize, PointF normalizedCenter, float radius, int color, int strokeSize) {

        int x = (int)(normalizedCenter.x * canvasSize.getWidth());
        int y = (int)(normalizedCenter.y * canvasSize.getHeight());

        Paint paint = new Paint();
        paint.setStrokeWidth(strokeSize);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);

        canvas.drawCircle(x, y, radius, paint);
    }

    private static void drawStartingHold(Canvas canvas, SizeF canvasSize, PointF normalizedCenter, float radius, int color, int strokeSize) {
        drawCircle(canvas, canvasSize, normalizedCenter, radius, color, strokeSize);
        drawCircle(canvas, canvasSize, normalizedCenter, radius - 10, color, strokeSize);
    }

    private static void drawHold(Canvas canvas, SizeF canvasSize, PointF normalizedCenter, float radius, int color, int strokeSize) {
        drawCircle(canvas, canvasSize, normalizedCenter, radius, color, strokeSize);
    }

    private static void drawFootHold(Canvas canvas, SizeF canvasSize, PointF normalizedCenter, float radius, int color, int strokeSize){
        int x = (int)(normalizedCenter.x * canvasSize.getWidth());
        int y = (int)(normalizedCenter.y * canvasSize.getHeight());

        Paint paint = new Paint();
        paint.setStrokeWidth(strokeSize);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);

        RectF rect = new RectF(x - radius, y - radius / 2, x + radius, y + radius / 2);
        canvas.drawArc(rect, 20, 140, false, paint);
    }

    private static void drawFinishingHold(Canvas canvas, SizeF canvasSize, PointF normalizedCenter, float radius, int color, int strokeSize){
        int x = (int)(normalizedCenter.x * canvasSize.getWidth());
        int y = (int)(normalizedCenter.y * canvasSize.getHeight());

        Paint paint = new Paint();
        paint.setStrokeWidth(strokeSize);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);

        RectF rect = new RectF(x - radius, y - radius / 2, x + radius, y + radius / 2);
        canvas.drawRect(rect, paint);
    }

    public static void drawHold(Canvas canvas, SizeF canvasSize, Hold hold){
        switch (hold.getType()){
            case Hold.START_HOLD:
                drawStartingHold(canvas, canvasSize, hold.getCenter(), hold.getRadius(), hold.getColor(), Hold.getStrokeSize(hold.getType()));
                break;
            case Hold.HOLD:
                drawHold(canvas, canvasSize, hold.getCenter(), hold.getRadius(), hold.getColor(), Hold.getStrokeSize(hold.getType()));
                break;
            case Hold.FINISH_HOLD:
                drawFinishingHold(canvas, canvasSize, hold.getCenter(), hold.getRadius(), hold.getColor(), Hold.getStrokeSize(hold.getType()));
                break;
            case Hold.FOOT_HOLD:
                drawFootHold(canvas, canvasSize, hold.getCenter(), hold.getRadius(), hold.getColor(), Hold.getStrokeSize(hold.getType()));
                break;
        }
    }


    public static Hold getHold(PointF center, PointF radius, SizeF size, int color, int type){
        float distance = Common.calculateDistance(center, radius, size.getWidth(), size.getHeight());
        int calculatedRadius = (int)(distance > 20 ? distance : 20);

        return new Hold(type, center, calculatedRadius, color);
    }

    public static Hold getHold(PointF center, float radius, int color, int type){
        int calculatedRadius = (int)(radius > 20 ? radius : 20);

        return new Hold(type, center, calculatedRadius, color);
    }
}
