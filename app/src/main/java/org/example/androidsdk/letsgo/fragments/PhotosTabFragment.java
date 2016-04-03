package org.example.androidsdk.letsgo.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.example.androidsdk.letsgo.R;
import org.example.androidsdk.letsgo.activities.FullscreenImageActivity;
import org.example.androidsdk.letsgo.activities.PlacesDetailActivity;
import org.example.androidsdk.letsgo.adapters.GridviewImageAdapter;
import org.example.androidsdk.letsgo.adapters.PlacesAdapter;
import org.example.androidsdk.letsgo.eventBusEvents.RequestCompleted;
import org.example.androidsdk.letsgo.helper.Utils;
import org.example.androidsdk.letsgo.interfaces.MyClickListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class PhotosTabFragment extends Fragment {

    private EventBus bus = EventBus.getDefault();
    private ArrayList<String> imagePaths = new ArrayList<String>();
    private GridviewImageAdapter adapter;
    private int columnWidth;
    private final int PADDING = 8 , NUM_OF_COLUMNS = 3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bus.register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_photos, container, false);
//        imagePaths = getIntent().getStringArrayListExtra("userimageurl");
        initilizeGridLayout();
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recylerView);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new GridviewImageAdapter(getActivity(), imagePaths, columnWidth);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent i = new Intent(getActivity(), FullscreenImageActivity.class);
				i.putExtra("position", position);
				i.putExtra("images",imagePaths);
				startActivity(i);
            }
        });
//        adapter = new PlacesAdapter(getContext(),placeList,"explore");
//        gridView = (GridView) rootView.findViewById(R.id.grid_view);
//        gridView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

//        adapter = new GridviewImageAdapter(getActivity(), imagePaths, columnWidth);
//        gridView.setAdapter(adapter);
        ((PlacesDetailActivity)getActivity()).postEventBus();
        return rootView;
    }

    private void initilizeGridLayout() {
        Resources r = getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, PADDING, r.getDisplayMetrics());
        columnWidth = (int) ((Utils.getScreenWidth(getActivity()) - ((NUM_OF_COLUMNS + 1) * padding)) / NUM_OF_COLUMNS);
    }

    @Override
    public void onDestroy() {
        bus.unregister(this);
        super.onDestroy();
    }
    public void onEvent(RequestCompleted event){
//        Toast.makeText(getActivity(),event.getResponse(),Toast.LENGTH_SHORT).show();
        try {
            JSONObject object = new JSONObject(event.getResponse());
            JSONArray userImageArray = object.getJSONArray("userimageurl");
            for (int i = 0; i < userImageArray.length(); i++)
                imagePaths.add(userImageArray.getString(i));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.update(imagePaths);
        adapter.notifyDataSetChanged();
    }
}
