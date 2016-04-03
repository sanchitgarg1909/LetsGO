package org.example.androidsdk.letsgo.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.example.androidsdk.letsgo.R;
import org.example.androidsdk.letsgo.activities.AddToListActivity;
import org.example.androidsdk.letsgo.helper.ServerRequest;
import org.example.androidsdk.letsgo.helper.Utils;
import org.example.androidsdk.letsgo.interfaces.GetStringCallback;
import org.example.androidsdk.letsgo.interfaces.MyClickListener;
import org.example.androidsdk.letsgo.model.Place;

import java.util.ArrayList;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder> {

    private ArrayList<Place> placeList;
    Context context;
    MyClickListener myClickListener;
    String variable;

    public PlacesAdapter(Context context,ArrayList<Place> placeList,String variable) {
        this.placeList = placeList;
        this.context = context;
        this.variable = variable;
    }

    public class PlacesViewHolder extends RecyclerView.ViewHolder {
        protected TextView title,location,rate;
        protected ImageView thumbNail;
        protected String id;
        protected ImageView favIcon;
        protected ImageView addToList;

        public PlacesViewHolder(View convertView) {
            super(convertView);
            title = (TextView) convertView.findViewById(R.id.title);
            rate = (TextView) convertView.findViewById(R.id.rate);
            location = (TextView) convertView.findViewById(R.id.location);
            thumbNail = (ImageView) convertView.findViewById(R.id.img);
            favIcon = (ImageView) convertView.findViewById(R.id.favourite);
            addToList = (ImageView) convertView.findViewById(R.id.add_to_list);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myClickListener.onItemClick(getAdapterPosition(),v);
                }
            });
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public void update(ArrayList<Place> arrayList){
        this.placeList=arrayList;
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    @Override
    public void onBindViewHolder(PlacesViewHolder viewHolder, int position) {
        final Place place = placeList.get(position);
        //Log.d("adapter2",w.getName());
        viewHolder.title.setText(place.getTitle());
        viewHolder.location.setText(place.getLocation());
        if(place.getVisibility()) {
            if (place.getFavourite()) {
                viewHolder.favIcon.setImageResource(R.drawable.heart_filled);
                viewHolder.favIcon.setTag("filled");
            } else {
                viewHolder.favIcon.setImageResource(R.drawable.heart_border);
                viewHolder.favIcon.setTag("empty");
            }
            viewHolder.favIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageView imageView = (ImageView) view;
                    if (imageView.getTag().toString().equals("empty")) {
                        place.setFavourite(true);
                        imageView.setImageResource(R.drawable.heart_filled);
                        imageView.setTag("filled");
                        addFavourite(place.getEventId());
                    } else {
                        place.setFavourite(false);
                        imageView.setImageResource(R.drawable.heart_border);
                        imageView.setTag("empty");
                        removeFavourite(place.getEventId());
                    }
                }
            });
            viewHolder.addToList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AddToListActivity.class);
                    intent.putExtra("name",place.getTitle());
                    intent.putExtra("photo",place.getThumbnailUrl());
                    intent.putExtra("eventid",place.getEventId());
                    context.startActivity(intent);
                }
            });
        }
//        viewHolder.id = w.getId();
//        Log.d("id",viewHolder.id);
        Picasso.with(context).load(place.getThumbnailUrl()).into(viewHolder.thumbNail);
    }

    @Override
    public PlacesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView;
        if(variable.equals("explore")) {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_vertical, viewGroup, false);
        }else {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_horizontal, viewGroup, false);
            final float scale = context.getResources().getDisplayMetrics().density;
            int width = Utils.getScreenWidth(context)-((int) (25 * scale + 0.5f));
            int height = (int) (200 * scale + 0.5f);
            RelativeLayout layout = (RelativeLayout) itemView.findViewById(R.id.layout);
            layout.setLayoutParams(new RelativeLayout.LayoutParams(width,height));
        }
        return new PlacesViewHolder(itemView);
    }

    public void addFavourite(String eventId){
        ServerRequest serverRequest = new ServerRequest(context);
        serverRequest.addFavourite("565fc3e7107e019459c7ef87", eventId, new GetStringCallback() {
            @Override
            public void callback(String response) {
                Toast.makeText(context,response,Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void removeFavourite(String eventId){
        ServerRequest serverRequest = new ServerRequest(context);
        serverRequest.removeFavourite("565fc3e7107e019459c7ef87", eventId, new GetStringCallback() {
            @Override
            public void callback(String response) {
                Toast.makeText(context,response,Toast.LENGTH_SHORT).show();
            }
        });
    }

}