package org.example.androidsdk.letsgo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.example.androidsdk.letsgo.R;
import org.example.androidsdk.letsgo.activities.PlacesDetailActivity;
import org.example.androidsdk.letsgo.adapters.ReviewsAdapter;
import org.example.androidsdk.letsgo.eventBusEvents.RequestCompleted;
import org.example.androidsdk.letsgo.model.Review;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class ReviewsTabFragment extends Fragment {

    private EventBus bus = EventBus.getDefault();
    ArrayList<Review> reviewsList = new ArrayList<>();
    ReviewsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bus.register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_reviews, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recylerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ReviewsAdapter(getActivity(),reviewsList);
        recyclerView.setAdapter(adapter);
        ((PlacesDetailActivity)getActivity()).postEventBus();
        return rootView;
    }
    @Override
    public void onDestroy() {
        bus.unregister(this);
        super.onDestroy();
    }
    public void onEvent(RequestCompleted event){
//        Toast.makeText(getActivity(),event.getResponse(),Toast.LENGTH_SHORT).show();
        reviewsList.clear();
        try {
            JSONObject object = new JSONObject(event.getResponse());
            JSONArray array = object.getJSONArray("reviews");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                Review review = new Review();
                review.setUserId(jsonObject.getString("userid"));
                review.setUsername(jsonObject.getString("username"));
                review.setComment(jsonObject.getString("comment"));
                review.setPicUrl(jsonObject.getString("picurl"));
                reviewsList.add(review);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.update(reviewsList);
        adapter.notifyDataSetChanged();
    }
}
