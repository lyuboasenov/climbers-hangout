package com.climbershangout.climbershangout.climb;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Size;


import com.climbershangout.climbershangout.Common;
import com.climbershangout.climbershangout.ImageHelper;
import com.climbershangout.entities.Hold;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyuboslav on 9/26/2017.
 */

public class RouteSchemaCreator {

    //Members
    private static final float STROKE_SIZE = 5;

    private Bitmap bitmap;
    private Bitmap overlayBitmap;
    private Bitmap tempOverlayBitmap;
    private Canvas canvas;
    private Canvas tempCanvas;


    private List<Hold> holds = new ArrayList<>();
    private Size viewSize;
    private Size bitmapSize;
    private Size bitmapFitSize;
    private float marginTop;
    private float marginLeft;
    private PointF currentHoldLocation;

    //Properties
    public Bitmap getBitmap() {
        return bitmap;
    }

    //public BitmapDrawable getOverlayDrawable() {
    //    return overlayDrawable;
    //}
    public Bitmap getOverlayBitmap() {
        return tempOverlayBitmap;
    }

    public int getLength(){
        return holds.size();
    }

    //Constructor
    public RouteSchemaCreator(){

    }

    //Methods
    public void initialize(String photoPath, Size viewSize) {

        this.viewSize = viewSize;

        bitmapSize = ImageHelper.getBitmapSizeFromFile(photoPath);
        bitmap = ImageHelper.decodeSampledBitmapFromFile(photoPath, viewSize.getWidth(), viewSize.getHeight());

        float bitmapAspect = bitmapSize.getWidth() / bitmap.getHeight();

        if (viewSize.getHeight() > bitmapAspect * viewSize.getWidth()){
            bitmapFitSize = new Size(viewSize.getWidth(), (int)(bitmapAspect * viewSize.getWidth()));
        } else {
            bitmapFitSize = new Size((int)(viewSize.getHeight() * bitmapAspect), viewSize.getHeight());
        }

        marginTop = (viewSize.getHeight() - bitmapFitSize.getHeight()) / 2;
        marginLeft = (viewSize.getWidth() - bitmapFitSize.getWidth()) / 2;

        overlayBitmap = Bitmap.createBitmap(
                getBitmap().getWidth(), getBitmap().getHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(overlayBitmap);

        restoreOverlayState();
    }

    private void restoreOverlayState(){
        tempOverlayBitmap = ImageHelper.duplicateBitmap(overlayBitmap);
        tempCanvas = new Canvas(tempOverlayBitmap);
    }

    private PointF normalizeCoordinates(PointF coordinates){
        return new PointF((coordinates.x - marginLeft) / bitmapFitSize.getWidth(), (coordinates.y - marginTop) / bitmapFitSize.getHeight());
    }

    private boolean verifyHoldInBounds(PointF holdCoordinates) {
        return holdCoordinates.x > marginLeft && holdCoordinates.x < marginLeft + bitmapFitSize.getWidth()
                && holdCoordinates.y > marginTop && holdCoordinates.y < marginTop + bitmapFitSize.getHeight();
    }

    public void removeHold(PointF holdCoordinates){
        PointF adjustedCoordinates = normalizeCoordinates(holdCoordinates);

        overlayBitmap = Bitmap.createBitmap(
                getBitmap().getWidth(), getBitmap().getHeight(), Bitmap.Config.ARGB_8888);

        //Todo: redraw holds
//        for (PointF hold : holds) {
//            //drawHold(hold);
//        }
    }

    private void drawCircle(Canvas canvas, PointF normalizedCenter, float radius, int color, int strokeSize) {

        int x = (int)(normalizedCenter.x * overlayBitmap.getWidth());
        int y = (int)(normalizedCenter.y * overlayBitmap.getHeight());

        Paint paint = new Paint();
        paint.setStrokeWidth(strokeSize);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);

        canvas.drawCircle(x, y, radius, paint);
    }

    private void drawStartingHold(Canvas canvas, PointF normalizedCenter, float radius, int color, int strokeSize) {
        drawCircle(canvas, normalizedCenter, radius, color, strokeSize);
        drawCircle(canvas, normalizedCenter, radius - 10, color, strokeSize);
    }

    private void drawHold(Canvas canvas, PointF normalizedCenter, float radius, int color, int strokeSize) {
        drawCircle(canvas, normalizedCenter, radius, color, strokeSize);
    }

    private void drawFootHold(Canvas canvas, PointF normalizedCenter, float radius, int color, int strokeSize){
        int x = (int)(normalizedCenter.x * overlayBitmap.getWidth());
        int y = (int)(normalizedCenter.y * overlayBitmap.getHeight());

        Paint paint = new Paint();
        paint.setStrokeWidth(strokeSize);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);

        RectF rect = new RectF(x - radius, y - radius / 2, x + radius, y + radius / 2);
        canvas.drawArc(rect, 20, 140, false, paint);
    }

    private void drawFinishingHold(Canvas canvas, PointF normalizedCenter, float radius, int color, int strokeSize){
        int x = (int)(normalizedCenter.x * overlayBitmap.getWidth());
        int y = (int)(normalizedCenter.y * overlayBitmap.getHeight());

        Paint paint = new Paint();
        paint.setStrokeWidth(strokeSize);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);

        RectF rect = new RectF(x - radius, y - radius / 2, x + radius, y + radius / 2);
        canvas.drawRect(rect, paint);
    }

    private void drawHold(Canvas canvas, Hold hold){
        switch (hold.getType()){
            case Hold.START_HOLD:
                drawStartingHold(canvas, hold.getCenter(), hold.getRadius(), hold.getColor(), Hold.getStrokeSize(hold.getType()));
                break;
            case Hold.HOLD:
                drawHold(canvas, hold.getCenter(), hold.getRadius(), hold.getColor(), Hold.getStrokeSize(hold.getType()));
                break;
            case Hold.FINISH_HOLD:
                drawFinishingHold(canvas, hold.getCenter(), hold.getRadius(), hold.getColor(), Hold.getStrokeSize(hold.getType()));
                break;
            case Hold.FOOT_HOLD:
                drawFootHold(canvas, hold.getCenter(), hold.getRadius(), hold.getColor(), Hold.getStrokeSize(hold.getType()));
                break;
        }
    }

    private Hold getHold(PointF center, PointF radius, int color, int type){
        float distance = Common.calculateDistance(center, radius, bitmapFitSize.getWidth(), bitmapFitSize.getHeight());
        int calculatedRadius = (int)(distance > 20 ? distance : 20);

        return new Hold(type, center, calculatedRadius, color);
    }

    public boolean finishAddHold(PointF point, int color, int type) {
        boolean success = false;
        if(currentHoldLocation != null){
            Hold hold = getHold(currentHoldLocation, normalizeCoordinates(point), color, type);
            holds.add(hold);
            drawHold(canvas, hold);
            restoreOverlayState();
            currentHoldLocation = null;
            success = true;
        }
        return success;
    }

    public boolean addHoldProgress(PointF point) {
        boolean success = false;
        if(currentHoldLocation != null){
            restoreOverlayState();
            drawHold(tempCanvas, getHold(currentHoldLocation, normalizeCoordinates(point), Color.RED, Hold.HOLD));
            success = true;
        }
        return success;
    }

    public boolean startAddHold(PointF point) {
        boolean success = false;
        if(verifyHoldInBounds(point)){
            this.currentHoldLocation = normalizeCoordinates(point);
            restoreOverlayState();
            drawHold(tempCanvas, getHold(currentHoldLocation, currentHoldLocation, Color.RED, Hold.HOLD));
            success = true;
        }
        return success;
    }
}
