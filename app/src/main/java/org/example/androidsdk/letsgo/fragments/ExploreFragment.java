package org.example.androidsdk.letsgo.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.example.androidsdk.letsgo.R;
import org.example.androidsdk.letsgo.eventBusEvents.RequestCompleted;
import org.example.androidsdk.letsgo.helper.ServerRequest;
import org.example.androidsdk.letsgo.interfaces.GetResponseCallback;
import org.example.androidsdk.letsgo.model.Place;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class ExploreFragment extends Fragment {

    private EventBus bus = EventBus.getDefault();
    Fragment fragment_list, fragment_map;
    View rootView;
    ArrayList<Place> placeList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_explore, container, false);
        fragment_list = new ListFragment();
        fragment_map = new MapFragment();
        makeServerRequest();
        openListFragment();
        return rootView;
    }

    public void openMapFragment() {
        getChildFragmentManager().beginTransaction().replace(R.id.container_frame, fragment_map).commit();
    }

    public void openListFragment() {
        getChildFragmentManager().beginTransaction().replace(R.id.container_frame, fragment_list).commit();
    }
    public void makeServerRequest(){
        ServerRequest serverRequest = new ServerRequest(getActivity());
        serverRequest.fetchPlaces("565fc3e7107e019459c7ef87", new GetResponseCallback() {
            @Override
            public void callback(ArrayList<Place> arrayList) {
                placeList = arrayList;
                postEventBus();
            }
        });
    }
    public void postEventBus(){
        bus.post(new RequestCompleted(placeList));
    }

}
