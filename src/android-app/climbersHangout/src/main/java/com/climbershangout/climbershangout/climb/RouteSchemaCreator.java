package com.climbershangout.climbershangout.climb;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.net.Uri;
import android.util.Size;
import android.util.SizeF;

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
    private Bitmap bitmap;
    private Bitmap overlayBitmap;
    private Bitmap tempOverlayBitmap;
    private Canvas canvas;
    private Canvas tempCanvas;


    private List<Hold> holds = new ArrayList<>();
    private PointF currentHoldLocation;

    //Properties
    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getLength(){
        return holds.size();
    }

    //Constructor
    public RouteSchemaCreator(){

    }

    //Methods
    public void initialize(Uri photoUri) {

        Size bitmapSize = ImageHelper.getBitmapSizeFromUri(photoUri);
        bitmap = ImageHelper.decodeSampledBitmapFromUri(photoUri, 2048, 2048);

        overlayBitmap = Bitmap.createBitmap(
                getBitmap().getWidth(), getBitmap().getHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(overlayBitmap);

        restoreOverlayState();
    }

    private void restoreOverlayState(){
        tempOverlayBitmap = ImageHelper.duplicateBitmap(overlayBitmap);
        tempCanvas = new Canvas(tempOverlayBitmap);
    }

    public void removeHold(PointF holdCoordinates){

        overlayBitmap = Bitmap.createBitmap(
                getBitmap().getWidth(), getBitmap().getHeight(), Bitmap.Config.ARGB_8888);

        //Todo: redraw holds
//        for (PointF hold : holds) {
//            //drawHold(hold);
//        }
    }

    public boolean finishAddHold(PointF point, SizeF containerSize, int color, int type) {
        boolean success = false;
        if(currentHoldLocation != null){
            SizeF sizeToFit = Common.calculateSizeToFit(containerSize, new SizeF(bitmap.getWidth(), bitmap.getHeight()));
            Hold hold = HoldHelper.getHold(currentHoldLocation, Common.normalizeCoordinates(point, containerSize), sizeToFit, color, type);
            holds.add(hold);
            SizeF canvasSize = new SizeF(overlayBitmap.getWidth(), overlayBitmap.getHeight());
            HoldHelper.drawHold(canvas, canvasSize, hold);
            restoreOverlayState();
            currentHoldLocation = null;
            success = true;
        }
        return success;
    }

    public boolean addHoldProgress(PointF point, SizeF containerSize, int color, int type) {
        boolean success = false;
        if(currentHoldLocation != null){
            restoreOverlayState();
            SizeF sizeToFit = Common.calculateSizeToFit(containerSize, new SizeF(bitmap.getWidth(), bitmap.getHeight()));
            SizeF canvasSize = new SizeF(overlayBitmap.getWidth(), overlayBitmap.getHeight());
            HoldHelper.drawHold(tempCanvas, canvasSize, HoldHelper.getHold(currentHoldLocation, Common.normalizeCoordinates(point, containerSize), sizeToFit, color, type));
            success = true;
        }
        return success;
    }

    public boolean startAddHold(PointF point, SizeF containerSize, int color, int type) {
        boolean success = false;

        //if(verifyHoldInBounds(point)){
        if(true){
            this.currentHoldLocation = Common.normalizeCoordinates(point, containerSize);
            restoreOverlayState();
            SizeF sizeToFit = Common.calculateSizeToFit(containerSize, new SizeF(bitmap.getWidth(), bitmap.getHeight()));
            SizeF canvasSize = new SizeF(overlayBitmap.getWidth(), overlayBitmap.getHeight());
            HoldHelper.drawHold(tempCanvas, canvasSize, HoldHelper.getHold(currentHoldLocation, currentHoldLocation, sizeToFit, color, type));
            success = true;
        }
        return success;
    }

    public void cancelAddHold(){
        restoreOverlayState();
        currentHoldLocation = null;
    }
}
