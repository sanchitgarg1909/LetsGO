package org.example.androidsdk.letsgo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.example.androidsdk.letsgo.R;
import org.example.androidsdk.letsgo.activities.PlacesDetailActivity;
import org.example.androidsdk.letsgo.adapters.PlacesAdapter;
import org.example.androidsdk.letsgo.helper.ServerRequest;
import org.example.androidsdk.letsgo.interfaces.GetResponseCallback;
import org.example.androidsdk.letsgo.interfaces.MyClickListener;
import org.example.androidsdk.letsgo.model.Place;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    View rootView;
    private ArrayList<Place> favouriteList = new ArrayList<>();
    private ArrayList<Place> recentList = new ArrayList<>();
    PlacesAdapter adapter_fav,adapter_rec;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        RecyclerView recyclerView_fav = (RecyclerView) rootView.findViewById(R.id.recylerView);
        recyclerView_fav.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView_fav.setLayoutManager(linearLayoutManager);
        adapter_fav = new PlacesAdapter(getActivity(),favouriteList,"favourite");
        recyclerView_fav.setAdapter(adapter_fav);
        adapter_fav.setOnItemClickListener(new MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
//                Toast.makeText(getActivity(),position+"",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), PlacesDetailActivity.class);
                intent.putExtra("eventid", favouriteList.get(position).getEventId());
                getActivity().startActivity(intent);
            }
        });
        RecyclerView recyclerView_rec = (RecyclerView) rootView.findViewById(R.id.recylerView2);
        recyclerView_rec.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView_rec.setLayoutManager(linearLayoutManager2);
        adapter_rec = new PlacesAdapter(getActivity(),recentList,"favourite");
        recyclerView_rec.setAdapter(adapter_rec);
        adapter_rec.setOnItemClickListener(new MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
//                Toast.makeText(getActivity(),position+"",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), PlacesDetailActivity.class);
                intent.putExtra("eventid", recentList.get(position).getEventId());
                getActivity().startActivity(intent);
            }
        });
        makeServerRequest();
        return rootView;
    }
    public void makeServerRequest(){
        ServerRequest serverRequest = new ServerRequest(getActivity());
        serverRequest.getFavourites("565fc3e7107e019459c7ef87", new GetResponseCallback() {
            @Override
            public void callback(ArrayList<Place> arrayList) {
                favouriteList = arrayList;
                adapter_fav.update(favouriteList);
                adapter_fav.notifyDataSetChanged();
            }
        });
        serverRequest.getRecents("565fc3e7107e019459c7ef87", new GetResponseCallback() {
            @Override
            public void callback(ArrayList<Place> arrayList) {
                recentList = arrayList;
                adapter_rec.update(recentList);
                adapter_rec.notifyDataSetChanged();
            }
        });
    }
}
