package com.climbershangout.climbershangout.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.climbershangout.climbershangout.R;

/**
 * Created by lyuboslav on 11/24/2016.
 */

public class RelativeSizeLinearLayout extends LinearLayout {
    //Members
    private float multiplier;
    private int sourceDimension;

    //Properties
    public float getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(float multiplier) {
        this.multiplier = multiplier;
    }

    public int getSourceDimension() {
        return sourceDimension;
    }

    public void setSourceDimension(int sourceDimension) {
        this.sourceDimension = sourceDimension;
    }

    //Constructors
    public RelativeSizeLinearLayout(Context context) {
        super(context);
    }

    public RelativeSizeLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttributeValues(context, attrs);
    }

    public RelativeSizeLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttributeValues(context, attrs);
    }

    public RelativeSizeLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        getAttributeValues(context, attrs);
    }

    //Methods
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        switch (getSourceDimension()){
            case 0:
                height = (int)(getMultiplier() * width);
                break;
            case 1:
                width = (int)(getMultiplier() * height);
                break;
        }

        setMeasuredDimension(width, height);
        invalidateRecursive(this);
    }

    private void getAttributeValues(Context context, AttributeSet attrs){
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.RelativeSizeLinearLayout,
                0, 0);

        try {
            setMultiplier(a.getFloat(R.styleable.RelativeSizeLinearLayout_multiplier, 1));
            setSourceDimension(a.getInteger(R.styleable.RelativeSizeLinearLayout_sourceDimension, -1));
        } finally {
            a.recycle();
        }
    }

    public void invalidateRecursive(ViewGroup layout) {
        int count = layout.getChildCount();
        View child;
        for (int i = 0; i < count; i++) {
            child = layout.getChildAt(i);
            if(child instanceof ViewGroup)
                invalidateRecursive((ViewGroup) child);
            else
                child.invalidate();
        }
    }
}
