package org.example.androidsdk.letsgo.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.example.androidsdk.letsgo.R;

import java.util.ArrayList;

public class DetailScreenSlideShowImageAdapter extends PagerAdapter {
    private Activity _activity;
    private ArrayList<String> _imagePaths;
    private LayoutInflater inflater;

    // constructor
    public DetailScreenSlideShowImageAdapter(Activity activity,
                                             ArrayList<String> imagePaths) {
        this._activity = activity;
        this._imagePaths = imagePaths;
        inflater = (LayoutInflater) _activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void update(ArrayList<String> _imagePaths){
        this._imagePaths = _imagePaths;
    }

    @Override
    public int getCount() {
        return this._imagePaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        //NetworkImageView imgDisplay;
        View viewLayout = inflater.inflate(R.layout.layout_slideshow_detailscreen, container, false);
        ImageView imgDisplay = (ImageView) viewLayout.findViewById(R.id.image);
        Picasso.with(_activity).load(_imagePaths.get(position)).into(imgDisplay);
//        BitmapFactory.Options options = batdroid BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//        Bitmap bitmap = BitmapFactory.decodeFile(_imagePaths.get(position), options);
//        imgDisplay.setImageBitmap(bitmap);

        container.addView(viewLayout);

//        imgDisplay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(_activity, FullScreenViewActivity.class);
//                i.putExtra("position", position);
//                i.putExtra("images",_imagePaths);
////                i.putExtra("eventId", eventid);
//                _activity.startActivity(i);
//            }
//        });

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

}
