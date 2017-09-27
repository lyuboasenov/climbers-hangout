package com.climbershangout.climbershangout.climb;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Size;


import com.climbershangout.climbershangout.ImageHelper;

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
    private Canvas canvas;
    private List<PointF> holds = new ArrayList<>();
    private Size viewSize;
    private Size bitmapSize;
    private Size bitmapFitSize;
    private float marginTop;
    private float marginLeft;

    //Properties
    public Bitmap getBitmap() {
        return bitmap;
    }

    //public BitmapDrawable getOverlayDrawable() {
    //    return overlayDrawable;
    //}
    public Bitmap getOverlayBitmap() {
        return overlayBitmap;
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
    }

    public void addHold(PointF holdCoordinates){
        if (verifyHoldInBounds(holdCoordinates)) {
            PointF adjustedCoordinates = adjustCoordinates(holdCoordinates);
            holds.add(adjustedCoordinates);
            drawHold(adjustedCoordinates);
        }
    }

    private PointF adjustCoordinates(PointF coordinates){
        return new PointF((coordinates.x - marginLeft) / bitmapFitSize.getWidth(), (coordinates.y - marginTop) / bitmapFitSize.getHeight());
    }

    private boolean verifyHoldInBounds(PointF holdCoordinates) {
        return holdCoordinates.x > marginLeft && holdCoordinates.x < marginLeft + bitmapFitSize.getWidth()
                && holdCoordinates.y > marginTop && holdCoordinates.y < marginTop + bitmapFitSize.getHeight();
    }

    public void removeHold(PointF holdCoordinates){
        PointF adjustedCoordinates = adjustCoordinates(holdCoordinates);

        overlayBitmap = Bitmap.createBitmap(
                getBitmap().getWidth(), getBitmap().getHeight(), Bitmap.Config.ARGB_8888);

        canvas = new Canvas(overlayBitmap);

        for (PointF hold : holds) {
            drawHold(hold);
        }
    }

    private void drawHold(PointF holdCoordinates) {

        int x = (int)(holdCoordinates.x * overlayBitmap.getWidth());
        int y = (int)(holdCoordinates.y * overlayBitmap.getHeight());

        Paint paint = new Paint();
        paint.setStrokeWidth(STROKE_SIZE);
        paint.setStyle(Paint.Style.STROKE);

        paint.setColor(Color.RED);
        canvas.drawCircle(x, y, 20, paint);
    }
}
