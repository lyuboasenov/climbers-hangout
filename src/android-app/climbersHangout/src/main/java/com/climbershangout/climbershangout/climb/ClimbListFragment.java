package com.climbershangout.climbershangout.climb;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.climbershangout.climbershangout.R;
import com.climbershangout.climbershangout.StorageManager;
import com.climbershangout.db.DbHelper;
import com.climbershangout.entities.Climb;

import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class ClimbListFragment extends Fragment {

    //Members
    private static View view;


    //Properties
    private RecyclerView getRecyclerView() { return (RecyclerView) view.findViewById(R.id.climb_list); }
    private ImageButton getAddTrainingButton(){ return (ImageButton) view.findViewById(R.id.btn_add_climb); }

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
        getAddTrainingButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRoute();
            }
        });
    }

    private void setRoute() {
        Intent addRouteActivity = new Intent(getActivity(), AddRouteActivity.class);
        startActivityForResult(addRouteActivity, AddRouteActivity.RC_ADD_ROUTE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AddRouteActivity.RC_ADD_ROUTE && resultCode == RESULT_OK) {
            //RefreshRoutes
        }
    }
}
