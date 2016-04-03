package org.example.androidsdk.letsgo.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;

import org.example.androidsdk.letsgo.R;
import org.example.androidsdk.letsgo.activities.FullscreenImageActivity;
import org.example.androidsdk.letsgo.interfaces.MyClickListener;
import org.example.androidsdk.letsgo.model.Place;

import java.util.ArrayList;

public class GridviewImageAdapter extends RecyclerView.Adapter<GridviewImageAdapter.PhotoViewHolder> {

	private Context context;
	private ArrayList<String> imagePaths = new ArrayList<>();
    MyClickListener myClickListener;
	private int imageWidth;

	public GridviewImageAdapter(Context context, ArrayList<String> imagePaths,int imageWidth) {
		this.context = context;
		this.imagePaths = imagePaths;
		this.imageWidth = imageWidth;
	}

	public class PhotoViewHolder extends RecyclerView.ViewHolder {
		protected ImageView imageView;

		public PhotoViewHolder(View convertView) {
			super(convertView);
			imageView = (ImageView) convertView.findViewById(R.id.image);
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

	public void update(ArrayList<String> arrayList){
		this.imagePaths=arrayList;
	}

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_grid, parent, false);
        RelativeLayout layout = (RelativeLayout) itemView.findViewById(R.id.layout);
        layout.setLayoutParams(new RelativeLayout.LayoutParams(imageWidth,imageWidth));
        return new PhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        Picasso.with(context).load(imagePaths.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return this.imagePaths.size();
    }

//    @Override
//	public View getView(final int position, View convertView, ViewGroup parent) {
//		ImageView imageView;
//		if (convertView == null) {
//			imageView = new ImageView(_activity);
//			imageView.setLayoutParams(new AbsListView.LayoutParams(imageWidth, imageWidth));
//			imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
////			rootView.setDefaultImageResId(android.R.drawable.sym_def_app_icon);
////			rootView.setErrorImageResId(android.R.drawable.ic_dialog_alert);
//		} else {
//			imageView = (ImageView) convertView;
//		}
//
////		// get screen dimensions
////		Bitmap image = decodeimage(_imagePaths.get(position), imageWidth,
////				imageWidth);
////
////		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
////		imageView.setLayoutParams(batdroid GridView.LayoutParams(imageWidth,
////				imageWidth));
////		imageView.setImageBitmap(image);
//		Picasso.with(_activity).load(getItem(position)).into(imageView);
////		imageView.setImageUrl(getItem(position), imageLoader);
//
//		imageView.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent i = new Intent(_activity, FullscreenImageActivity.class);
//				i.putExtra("position", position);
//				i.putExtra("images",_imagePaths);
//				_activity.startActivity(i);
//			}
//		});
//		// image view click listener
//		//imageView.setOnClickListener(batdroid OnImageClickListener(position));
//
//		return imageView;
//	}

}
