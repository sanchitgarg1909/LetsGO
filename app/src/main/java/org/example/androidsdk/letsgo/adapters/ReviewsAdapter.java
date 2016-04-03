package org.example.androidsdk.letsgo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.example.androidsdk.letsgo.R;
import org.example.androidsdk.letsgo.interfaces.MyClickListener;
import org.example.androidsdk.letsgo.model.Place;
import org.example.androidsdk.letsgo.model.Review;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {
    private ArrayList<Review> reviewsList;
    Context context;
    MyClickListener myClickListener;

    public ReviewsAdapter(Context context, ArrayList<Review> reviewsList) {
        this.reviewsList = reviewsList;
        this.context = context;
    }

    public class ReviewsViewHolder extends RecyclerView.ViewHolder {
        protected TextView name,comment;
        protected ImageView image;
        protected String id;

        public ReviewsViewHolder(View convertView) {
            super(convertView);
            name = (TextView) convertView.findViewById(R.id.username);
            comment = (TextView) convertView.findViewById(R.id.comment);
            image = (ImageView) convertView.findViewById(R.id.picimage);
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

    public void update(ArrayList<Review> arrayList){
        this.reviewsList=arrayList;
    }

    @Override
    public ReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_reviews, parent, false);
        return new ReviewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReviewsViewHolder holder, int position) {
        Review review = reviewsList.get(position);
        holder.name.setText(review.getUsername());
        holder.comment.setText(review.getComment());
        holder.id = review.getUserId();
        Picasso.with(context).load(review.getPicUrl()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return this.reviewsList.size();
    }

}
