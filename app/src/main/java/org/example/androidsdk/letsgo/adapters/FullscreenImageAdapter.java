package org.example.androidsdk.letsgo.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import org.example.androidsdk.letsgo.R;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;


public class FullscreenImageAdapter extends PagerAdapter {

    private Activity _activity;
    private ArrayList<String> _imagePaths = new ArrayList<>();
    private LayoutInflater inflater;
    private PhotoViewAttacher photoViewAttacher;

    // constructor
    public FullscreenImageAdapter(Activity activity,
                                  ArrayList<String> imagePaths) {
        this._activity = activity;
        this._imagePaths = imagePaths;
    }

    @Override
    public int getCount() {
        return this._imagePaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imgDisplay;

        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container,
                false);
        imgDisplay = (ImageView) viewLayout.findViewById(R.id.imgDisplay);
//        Toast.makeText(_activity,_imagePaths[position],Toast.LENGTH_SHORT).show();
        Picasso.with(_activity).load(_imagePaths.get(position)).into(imgDisplay);
        photoViewAttacher = new PhotoViewAttacher(imgDisplay);
//        btnClose = (Button) viewLayout.findViewById(R.id.btnClose);
//        BitmapFactory.Options options = batdroid BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//        Bitmap bitmap = BitmapFactory.decodeFile(_imagePaths.get(position), options);
//        imgDisplay.setImageBitmap(bitmap);

        // close button_unselected click event
//        btnClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                _activity.finish();
//            }
//        });

                ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

}
