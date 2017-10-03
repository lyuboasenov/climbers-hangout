package com.climbershangout.climbershangout.climb;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.climbershangout.climbershangout.MessageHelper;
import com.climbershangout.climbershangout.R;
import com.climbershangout.climbershangout.RequestCode;
import com.climbershangout.db.DbHelper;
import com.climbershangout.entities.Climb;

import java.util.ArrayList;
import java.util.List;

public class ClimbListFragment extends Fragment {

    //Members
    private static View view;
    private boolean isFABOpen = false;
    private RouteCreationHelper routeCreationHelper;

    //Properties
    private RecyclerView getRecyclerView() { return (RecyclerView) view.findViewById(R.id.climb_list); }
    private ImageButton getAddRouteButton(){ return (ImageButton) view.findViewById(R.id.btn_add_climb); }
    private ImageButton getAddRouteFromImageButton(){ return (ImageButton) view.findViewById(R.id.btn_add_climb_from_gallery); }
    private ImageButton getAddRouteFromCameraButton(){ return (ImageButton) view.findViewById(R.id.btn_add_climb_from_camera); }

    //Constructor
    public ClimbListFragment() {

    }

    //Methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_climb_list, container, false);
        } catch (InflateException e) {
            /* map is already there, just return view as it is */
            e.printStackTrace();
        }

        getRecyclerView().setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        addButtonListeners();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        DbHelper helper = new DbHelper(getContext());
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Climb> climbs = new ArrayList<>();
        db.close();
        getRecyclerView().setAdapter(new ClimbListRecyclerViewAdapter(climbs, this.getActivity(), new ClimbListRecyclerViewAdapter.onClimbListItemClicked() {
            @Override
            public void onItemClicked(Climb climb) {

            }
        }));
    }

    private void addButtonListeners() {
        getAddRouteButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFabMenu();
                }else{
                    closeFabMenu();
                }
            }
        });
        getAddRouteFromImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRouteFromImage();
            }
        });
        getAddRouteFromCameraButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRouteFromCamera();
            }
        });
    }

    private void addRouteFromImage() {
        addRoute(RouteCreationHelper.FROM_GALLERY);
    }

    private void addRouteFromCamera() {
        addRoute(RouteCreationHelper.FROM_CAMERA);
    }

    private void addRoute(int source) {
        closeFabMenu();
        routeCreationHelper = new RouteCreationHelper(getContext(), source);

        Intent requestImageIntent = routeCreationHelper.getRequestImageIntent();
        if (requestImageIntent == null) {
            MessageHelper.showError(R.string.error_unable_to_create_request_image_intent);
        } else {
            startActivityForResult(requestImageIntent,
                    RouteCreationHelper.RC_NEW_ROUTE_REQUEST_IMAGE);
        }
    }

    private void closeFabMenu() {
        isFABOpen=false;
        getAddRouteButton().animate().translationY(0);
        getAddRouteFromCameraButton().animate().translationY(0);
        getAddRouteFromImageButton().animate().translationY(0);
    }

    private void showFabMenu() {
        isFABOpen=true;
        //getAddRouteButton().animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        getAddRouteFromCameraButton().animate().translationY(-getResources().getDimension(R.dimen.standard_60));
        getAddRouteFromImageButton().animate().translationY(-getResources().getDimension(R.dimen.standard_110));
    }

    private void setRoute(Intent requestImageIntent) {
        Intent createRouteIntent = routeCreationHelper.getCreateRouteActivityIntent(requestImageIntent);
        startActivityForResult(createRouteIntent, RequestCode.RC_ADD_ROUTE);
    }

    private void saveRoute(Intent data) {
        //TODO:Save route
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RequestCode.RC_ADD_ROUTE && resultCode == RESULT_OK) {
            //TODO:RefreshRoutes
        } else if (requestCode == RouteCreationHelper.RC_NEW_ROUTE_REQUEST_IMAGE && resultCode == RESULT_OK) {
            setRoute(data);
        }else if (requestCode == RouteCreationHelper.RC_CREATE_ROUTE && resultCode == RESULT_OK) {
            saveRoute(data);
        }
    }
}
