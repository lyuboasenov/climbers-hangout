package com.climbershangout.climbershangout.climb;

import android.graphics.Color;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SizeF;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.climbershangout.climbershangout.R;
import com.climbershangout.climbershangout.views.HoldView;
import com.climbershangout.climbershangout.LogHelper;
import com.climbershangout.climbershangout.views.ZoomableRelativeLayout;
import com.climbershangout.entities.Hold;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyuboslav on 9/28/2017.
 */


public class AddRouteDetailedFragment extends Fragment {

    //Members
    private Uri imageFileUri;

    private ZoomableRelativeLayout holdContainerLayout;
    private ImageView backgroundView;
    private TextView lengthTextView;
    private ToggleButton footHoldButton;
    private ToggleButton holdButton;
    private ToggleButton finishHoldButton;
    private ToggleButton startHoldButton;
    private ToggleButton colorWhiteButton;
    private ToggleButton colorGreenButton;
    private ToggleButton colorRedButton;
    private ToggleButton colorBlueButton;
    private List<HoldView> holds = new ArrayList<>();
    private HoldView selectedHold;

    private ToggleButtonListCheckedManager colorClickListener;
    private int selectedColor;
    private ToggleButtonListCheckedManager holdClickListener;
    private int selectedHoldType;

    private RouteSchemaCreator schemaCreator = new RouteSchemaCreator();

    //Properties
    private ZoomableRelativeLayout getHoldContainerLayout(){
        if(holdContainerLayout == null) {
            holdContainerLayout = (ZoomableRelativeLayout) getActivity().findViewById(R.id.add_route_schema_container);
        }
        return holdContainerLayout;
    }

    private AddRouteDetailedFragment getThis(){ return this; }
    public int getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
    }

    public int getSelectedHoldType() {
        return selectedHoldType;
    }

    public void setSelectedHoldType(int selectedHoldType) {
        this.selectedHoldType = selectedHoldType;
    }

    public ImageView getBackgroundView() {
        if (backgroundView == null) { backgroundView = (ImageView) getActivity().findViewById(R.id.add_route_image_background); }
        return backgroundView;
    }

    public ToggleButton getFootHoldButton() {
        if (footHoldButton == null) {
            footHoldButton = (ToggleButton) getActivity().findViewById(R.id.action_add_route_foot_hold);
            footHoldButton.setBackgroundTintList(getResources().getColorStateList(R.color.add_route_hold_type_color_tint));
        }
        return footHoldButton;
    }

    public ToggleButton getHoldButton() {
        if (holdButton == null) {
            holdButton = (ToggleButton) getActivity().findViewById(R.id.action_add_route_hold);
            holdButton.setBackgroundTintList(getResources().getColorStateList(R.color.add_route_hold_type_color_tint));
        }
        return holdButton;
    }

    public ToggleButton getFinishHoldButton() {
        if (finishHoldButton == null) {
            finishHoldButton = (ToggleButton) getActivity().findViewById(R.id.action_add_route_finishing_hold);
            finishHoldButton.setBackgroundTintList(getResources().getColorStateList(R.color.add_route_hold_type_color_tint));
        }
        return finishHoldButton;
    }

    public ToggleButton getStartHoldButton() {
        if (startHoldButton == null) {
            startHoldButton = (ToggleButton) getActivity().findViewById(R.id.action_add_route_start_hold);
            startHoldButton.setBackgroundTintList(getResources().getColorStateList(R.color.add_route_hold_type_color_tint));
        }
        return startHoldButton;
    }

    public ToggleButton getColorWhiteButton() {
        if (colorWhiteButton == null) {
            colorWhiteButton = (ToggleButton) getActivity().findViewById(R.id.action_add_route_color_white);
        }

        return colorWhiteButton;
    }

    public ToggleButton getColorGreenButton() {
        if (colorGreenButton == null) {
            colorGreenButton = (ToggleButton) getActivity().findViewById(R.id.action_add_route_color_green);
        }
        return colorGreenButton;
    }

    public ToggleButton getColorRedButton() {
        if (colorRedButton == null) {
            colorRedButton = (ToggleButton) getActivity().findViewById(R.id.action_add_route_color_red);
        }
        return colorRedButton;
    }

    public ToggleButton getColorBlueButton() {
        if (colorBlueButton == null) {
            colorBlueButton = (ToggleButton) getActivity().findViewById(R.id.action_add_route_color_blue);
        }
        return colorBlueButton;
    }

    public TextView getLengthTextView() {
        if (lengthTextView == null) { lengthTextView = (TextView) getActivity().findViewById(R.id.add_route_length); }
        return lengthTextView;
    }

    public RouteSchemaCreator getSchemaCreator() {
        return schemaCreator;
    }

    //Constructor
    public AddRouteDetailedFragment() {

    }

    public AddRouteDetailedFragment(Uri imageFileUri) {
        this.imageFileUri = imageFileUri;
    }

    //Methods
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_route_details, container, false);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        getSchemaCreator().initialize(imageFileUri);
        getBackgroundView().setImageBitmap(getSchemaCreator().getBitmap());

        initializeEvents();
    }

    private void initializeEvents() {
        getHoldContainerLayout().setOnClickListener(new ZoomableRelativeLayout.OnClickListener() {
            @Override
            public void onClick(PointF coordinates) {
                addHold(coordinates);
            }
        });

        holdClickListener = new ToggleButtonListCheckedManager();
        holdClickListener.add(getHoldButton());
        holdClickListener.add(getStartHoldButton());
        holdClickListener.add(getFinishHoldButton());
        holdClickListener.add(getFootHoldButton());
        holdClickListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.action_add_route_finishing_hold:
                        setSelectedHoldType(Hold.FINISH_HOLD);
                        break;
                    case R.id.action_add_route_foot_hold:
                        setSelectedHoldType(Hold.FOOT_HOLD);
                        break;
                    case R.id.action_add_route_start_hold:
                        setSelectedHoldType(Hold.START_HOLD);
                        break;
                    default:
                        setSelectedHoldType(Hold.HOLD);
                        break;
                }
            }
        });

        colorClickListener = new ToggleButtonListCheckedManager();
        colorClickListener.add(getColorBlueButton());
        colorClickListener.add(getColorGreenButton());
        colorClickListener.add(getColorRedButton());
        colorClickListener.add(getColorWhiteButton());
        colorClickListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.action_add_route_color_blue:
                        setSelectedColor(Color.BLUE);
                        break;
                    case R.id.action_add_route_color_green:
                        setSelectedColor(Color.GREEN);
                        break;
                    case R.id.action_add_route_color_red:
                        setSelectedColor(Color.RED);
                        break;
                    case R.id.action_add_route_color_white:
                        setSelectedColor(Color.WHITE);
                        break;
                }
            }
        });

        getColorWhiteButton().setChecked(true);
        getStartHoldButton().setChecked(true);
    }

    private void addHold(PointF point) {
        HoldView holdView = new HoldView(this.getContext())
                .setCenterLocation(point)
                .setColor(getSelectedColor())
                .setType(getSelectedHoldType())
                .setOnLongClick(new HoldView.OnLongClick() {
                    @Override
                    public void onLongClick(View view) {
                        holds.remove(view);
                        getHoldContainerLayout().removeView(view);
                    }
                });
        holds.add(holdView);
        getHoldContainerLayout().addView(holdView);
    }

    public class ToggleButtonListCheckedManager implements CompoundButton.OnCheckedChangeListener{
        //Member
        List<CompoundButton> buttonList = new ArrayList<>();
        private View.OnClickListener onClickListener;

        //Properties
        public void setOnClickListener(View.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        //Methods
        public void add(CompoundButton button){
            button.setOnCheckedChangeListener(this);
            buttonList.add(button);
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(b){
                for(CompoundButton button : buttonList){
                    if(button != compoundButton){
                        button.setChecked(false);
                    }
                }

                if(null != onClickListener){
                    onClickListener.onClick(compoundButton);
                }
            }
        }
    }
}

