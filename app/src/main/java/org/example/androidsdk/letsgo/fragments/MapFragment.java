package org.example.androidsdk.letsgo.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.example.androidsdk.letsgo.R;
import org.example.androidsdk.letsgo.activities.HomeActivity;
import org.example.androidsdk.letsgo.eventBusEvents.RequestCompleted;
import org.example.androidsdk.letsgo.model.Place;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class MapFragment extends Fragment implements OnMapReadyCallback{

    public GoogleMap googleMap;
    private EventBus bus = EventBus.getDefault();
    private SupportMapFragment mapFragment;
    private ArrayList<Place> placeList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        bus.register(this);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fabButton);
        mapFragment = new SupportMapFragment(){
            @Override
            public void onActivityCreated(Bundle savedInstanceState) {
                super.onActivityCreated(savedInstanceState);
                initialiseMap();
            }
        };
        getChildFragmentManager().beginTransaction().add(R.id.container_map,mapFragment).commit();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity) getActivity()).openList();
            }
        });
        return rootView;
    }
    public void initialiseMap(){
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
//        googleMap.setOnInfoWindowClickListener(this);
        googleMap.setMyLocationEnabled(true);
//        googleMap.setInfoWindowAdapter(new MyInfoWindowAdapter());
//        arrayList = ((ListingActivity)getActivity()).postEventBus();
        ((HomeActivity)getActivity()).postEventBus();
//        fixedLocation(arrayList);
    }

    @Override
    public void onDestroy() {
        // Unregister
        bus.unregister(this);
        super.onDestroy();
    }

    public void onEvent(RequestCompleted event){
        placeList = event.getArrayList();
        fixedLocation(placeList);
    }

    public void fixedLocation(ArrayList<Place> arrayList) {
        boolean flag = false;
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        googleMap.clear();
        for (int i = 0; i < arrayList.size(); i++) {
            Place p = arrayList.get(i);
            double latitude=p.getLatitude();
            double longitude=p.getLongitude();
            LatLng latlng=new LatLng(latitude,longitude);
            Marker marker = googleMap.addMarker(new MarkerOptions().draggable(false).position(latlng).title(p.getTitle()).snippet("{\"location\":\"" + p.getLocation() + "\", \"rate\":\"" + p.getPrice() + "\", \"icon\":\"" + p.getThumbnailUrl() + "\", \"id\":\"" + p.getEventId() + "\"}"));
            builder.include(marker.getPosition());
            flag = true;
        }
        if(flag) {
            LatLngBounds bounds = builder.build();
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20));
        }else
            Toast.makeText(getContext(), "No Places", Toast.LENGTH_SHORT).show();
    }

}
