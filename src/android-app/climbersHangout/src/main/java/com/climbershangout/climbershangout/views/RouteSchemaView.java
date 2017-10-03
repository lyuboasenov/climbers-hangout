package com.climbershangout.climbershangout.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.RelativeLayout;

import com.climbershangout.climbershangout.LogHelper;

/**
 * Created by lyuboslav on 10/1/2017.
 */

public class RouteSchemaView extends RelativeLayout implements ScaleGestureDetector.OnScaleGestureListener {

    //Mermbers
    private Bitmap image;

    private static final float MIN_ZOOM = 1.0f;
    private static final float MAX_ZOOM = 4.0f;

    private ZoomableRelativeLayout.OnClickListener onClickListener;

    private Mode mode = Mode.NONE;
    private float scale = 1.0f;
    private float lastScaleFactor = 0f;

    // Where the finger first  touches the screen
    private float startX = 0f;
    private float startY = 0f;

    // How much to translate the canvas
    private float dx = 0f;
    private float dy = 0f;
    private float prevDx = 0f;
    private float prevDy = 0f;
    private ScaleGestureDetector scaleDetector;

    //Properties
    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    //Constructors
    public RouteSchemaView(Context context) {
        super(context);
        init(context);
    }

    public RouteSchemaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RouteSchemaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public RouteSchemaView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    //Methods
    private void init(Context context) {
        scaleDetector = new ScaleGestureDetector(context, this);
        this.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        LogHelper.l(view, "DOWN %s", mode);
                        if (scale > MIN_ZOOM) {
                            mode = Mode.DRAG;
                            startX = motionEvent.getX() - prevDx;
                            startY = motionEvent.getY() - prevDy;
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (mode == Mode.DRAG) {
                            dx = motionEvent.getX() - startX;
                            dy = motionEvent.getY() - startY;
                        }
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        LogHelper.l(view, "ZOOM");
                        mode = Mode.ZOOM;
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        LogHelper.l(view, "DRAG");
                        mode = Mode.DRAG;
                        break;
                    case MotionEvent.ACTION_UP:
                        LogHelper.l(view, "UP %s", mode);
                        if (mode == Mode.NONE && onClickListener != null
                                && motionEvent.getEventTime() - motionEvent.getDownTime() > 300){
                            onClickListener.onClick(new PointF(motionEvent.getX(), motionEvent.getY()));
                        }
                        mode = Mode.NONE;
                        prevDx = dx;
                        prevDy = dy;
                        break;
                }
                scaleDetector.onTouchEvent(motionEvent);

                if ((mode == Mode.DRAG && scale >= MIN_ZOOM) || mode == Mode.ZOOM) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    float maxDx = (child().getWidth() - (child().getWidth() / scale)) / 2 * scale;
                    float maxDy = (child().getHeight() - (child().getHeight() / scale))/ 2 * scale;
                    dx = Math.min(Math.max(dx, -maxDx), maxDx);
                    dy = Math.min(Math.max(dy, -maxDy), maxDy);
                    LogHelper.l(view, "Width: " + child().getWidth() + ", scale " + scale + ", dx " + dx
                            + ", max " + maxDx);
                    applyScaleAndTranslation();
                }

                return true;
            }
        });
    }

    @Override
    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        float scaleFactor = scaleDetector.getScaleFactor();
        LogHelper.l(this, "onScale" + scaleFactor);
        if (lastScaleFactor == 0 || (Math.signum(scaleFactor) == Math.signum(lastScaleFactor))) {
            scale *= scaleFactor;
            scale = Math.max(MIN_ZOOM, Math.min(scale, MAX_ZOOM));
            lastScaleFactor = scaleFactor;
        } else {
            lastScaleFactor = 0;
        }
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        LogHelper.l(this, "onScaleBegin");
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        LogHelper.l(this, "onScaleEnd");
    }

    private void applyScaleAndTranslation() {
        child().setScaleX(scale);
        child().setScaleY(scale);
        child().setTranslationX(dx);
        child().setTranslationY(dy);
    }

    private View child() {
        return getChildAt(0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private enum Mode {
        NONE,
        DRAG,
        ZOOM
    }
}
