package com.climbershangout.climbershangout.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.SizeF;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.climbershangout.climbershangout.Common;
import com.climbershangout.climbershangout.R;
import com.climbershangout.climbershangout.climb.HoldHelper;
import com.climbershangout.climbershangout.LogHelper;

/**
 * TODO: document your custom view class.
 */
public class HoldView extends RelativeLayout {

    //Members
    private final int IMAGE_SIZE = 200;
    private final float HOLD_SIZE = getResources().getDimension(R.dimen.view_hold_hold_size);
    private int color;
    private int type;
    private float size = 20;
    private View view;
    private SeekBar slider;
    private ImageView placeHolder;
    private ImageView imageView;
    private Bitmap holdBoundaryImage;
    private Canvas canvas;
    private PointF centerLocation;
    private float dragXDiff;
    private float dragYDiff;
    private Mode mode;
    private OnLongClick onLongClick;

    //Properties
    public int getColor() {
        return color;
    }

    public HoldView setColor(int color) {
        this.color = color;
        invalidateView();
        return this;
    }

    public int getType() {
        return type;
    }

    public HoldView setType(int type) {
        this.type = type;
        invalidateView();
        return this;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
            this.size = size < 20 ? 20 : size;
    }

    public PointF getCenterLocation() {
        return centerLocation;
    }

    public HoldView setCenterLocation(PointF centerLocation) {
        this.centerLocation = centerLocation;
        LogHelper.l(this, "Hold set (%f, %f)", centerLocation.x, centerLocation.y);
        return this;
    }

    @Override
    public void setFocusable(boolean focusable) {
        if (!focusable)
            throw new IllegalArgumentException("This view is always focusable.");
    }

    public HoldView setOnLongClick(
            OnLongClick onLongClick) {
        this.onLongClick = onLongClick;
        return this;
    }

    //Constructor
    public HoldView(Context context) {
        super(context);
        init(null, 0);
    }

    public HoldView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public HoldView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    //Methods
    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.HoldView, defStyle, 0);

        a.recycle();

        super.setFocusable(true);
        this.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                onFocusChanged(b);
            }
        });

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.view_hold, this, true);

        slider = (SeekBar) view.findViewById(R.id.view_hold_slider);
        imageView = (ImageView) view.findViewById(R.id.view_hold_image);
        placeHolder = (ImageView) view.findViewById(R.id.view_hold_place_holder);

        holdBoundaryImage = Bitmap.createBitmap(IMAGE_SIZE, IMAGE_SIZE, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(holdBoundaryImage);

        imageView.setImageBitmap(holdBoundaryImage);
        placeHolder.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return onTouchEvent(motionEvent);
            }
        });

        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                changeSize(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

//    private boolean isPressed = false;
//    private final Handler handler = new Handler();
//    private final Runnable runnable = new Runnable() {
//        public void run() {
//            checkGlobalVariable();
//        }
//    };

//    private void checkGlobalVariable() {
//        if (isPressed && onLongClick != null){
//            onLongClick.onLongClick(this);
//        }
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handled = false;
        PointF point = new PointF(event.getX(), event.getY());
        long duration = event.getEventTime() - event.getDownTime();

        LogHelper.l(this, "Touch %d %b %s", duration, mode, toString(Common.FORMAT_VERBOSE));

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case android.view.MotionEvent.ACTION_DOWN:
                LogHelper.l(this, "Down (%f, %f)", point.x, point.y);
                dragXDiff = getCenterLocation().x - point.x;
                dragYDiff = getCenterLocation().y - point.y;
                placeHolder.setVisibility(VISIBLE);
                mode = Mode.NONE;
//                isPressed = true;
//                handler.postDelayed(runnable, 1000);
                handled = true;
                break;
            case MotionEvent.ACTION_MOVE:
                LogHelper.l(this, "Move (%f, %f) (%f, %f)", point.x, point.y, dragXDiff, dragYDiff);
                if(duration > 500) {
                    setCenterLocation(new PointF(point.x + dragXDiff, point.y + dragYDiff));
                    adjustView();
                    mode = Mode.DRAG;
                    handled = true;
                }
                break;
            case android.view.MotionEvent.ACTION_UP:
                LogHelper.l(this, "Up (%f, %f)", point.x, point.y);
                if(duration > 500 && mode == Mode.NONE && onLongClick != null) {
                    LogHelper.l(this, "Context menu %s", toString(Common.FORMAT_VERBOSE));
                    onLongClick.onLongClick(this);
                    handled = true;
                }
//                if(isPressed){
//                    isPressed = false;
//                    handler.removeCallbacks(runnable);
//                    handled = true;
//                }
                dragXDiff = 0;
                dragYDiff = 0;
                placeHolder.setVisibility(GONE);
                mode = Mode.NONE;
                break;
        }

        return handled;
    }

    private void changeSize(int progress){
        int size = 20 + 5 * progress;
        setSize(size);

        adjustView();

        LogHelper.l(this, "Size changed: %s", toString(Common.FORMAT_VERBOSE));
    }

    private void adjustView() {
        int left = (int)(getCenterLocation().x - HOLD_SIZE / 2);
        int top = (int)(getCenterLocation().y - HOLD_SIZE / 2);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.getLayoutParams();
        layoutParams.setMargins(left, top, 0, 0);
        setLayoutParams(layoutParams);

        RelativeLayout.LayoutParams imageViewLayoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        imageViewLayoutParams.height = (int)getSize();
        imageViewLayoutParams.width = (int)getSize();
        imageView.setLayoutParams(imageViewLayoutParams);

        LogHelper.l(this, "Adjust: %s", this.toString(Common.FORMAT_VERBOSE));
    }

    private void invalidateView(){
        holdBoundaryImage.eraseColor(Color.TRANSPARENT);
        HoldHelper.drawHold(canvas, new SizeF(IMAGE_SIZE, IMAGE_SIZE), HoldHelper.getHold(new PointF(0.5f, 0.5f), IMAGE_SIZE / 2, color, type));
        adjustView();
    }

    protected void onFocusChanged(boolean gainFocus) {
        LogHelper.l(this, "Focus: %b", gainFocus);

        if(gainFocus){
            slider.setVisibility(VISIBLE);
        } else {
            slider.setVisibility(INVISIBLE);
        }
    }

    @Override
    public String toString() {
        return toString(Common.FORMAT_DEBUG);
    }

    private String toString(int format){
        if(format < Common.FORMAT_QUIET) { format = Common.FORMAT_QUIET; }
        if(format > Common.FORMAT_DEBUG) { format = Common.FORMAT_DEBUG; }

        String string = null;
        switch (format){
            case Common.FORMAT_DEBUG:
                string = String.format("%s{center(%f,%f), size(%f), color(%d), type(%d)}",
                        this.getClass().getName(),
                        centerLocation.x,
                        centerLocation.y,
                        getSize(),
                        getColor(),
                        getType());
                break;
            case Common.FORMAT_VERBOSE:
                string = String.format("%s{center(%f,%f), size(%f), color(%d), type(%d)}",
                        this.getClass().getSimpleName(),
                        centerLocation.x,
                        centerLocation.y,
                        getSize(),
                        getColor(),
                        getType());
                break;
            case Common.FORMAT_INFO:
                string = String.format("{color(%d), type(%d)}",
                        getColor(),
                        getType());
                break;
            case Common.FORMAT_NORMAL:
                string = String.format("%s",
                        this.getClass().getName());
                break;
            case Common.FORMAT_QUIET:
                string = String.format("%s",
                        this.getClass().getSimpleName());
                break;
        }

        return string;
    }

    private enum Mode {
        NONE,
        DRAG
    }

    public interface OnLongClick{
        void onLongClick(View view);
    }
}
