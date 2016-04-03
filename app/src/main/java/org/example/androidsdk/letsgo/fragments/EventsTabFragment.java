package org.example.androidsdk.letsgo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.example.androidsdk.letsgo.R;
import org.example.androidsdk.letsgo.activities.HomeActivity;
import org.example.androidsdk.letsgo.adapters.PlacesAdapter;
import org.example.androidsdk.letsgo.eventBusEvents.RequestCompleted;
import org.example.androidsdk.letsgo.interfaces.MyClickListener;
import org.example.androidsdk.letsgo.model.Place;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;


public class EventsTabFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private EventBus bus = EventBus.getDefault();
    private ArrayList<Place> placeList = new ArrayList<>();
    PlacesAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bus.register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_places, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recylerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new PlacesAdapter(getContext(),placeList,"explore");
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
//                Toast.makeText(getActivity(),position+"",Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getActivity(), DetailActivity.class);
//                getActivity().startActivity(intent);
            }
        });
        ((HomeActivity)getActivity()).postEventBus();
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        setHasOptionsMenu(true);
        return rootView;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem filter = menu.add("filter");
        filter.setIcon(R.drawable.filter);
        filter.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        filter.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getActivity(), "Eventsfilter", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public void onDestroy() {
        bus.unregister(this);
        super.onDestroy();
    }
    public void onEvent(RequestCompleted event){
        placeList = event.getArrayList();
        adapter.update(placeList);
        adapter.notifyDataSetChanged();
        if(swipeRefreshLayout!=null)
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        ((HomeActivity) getActivity()).makeServerRequest();
    }
}
