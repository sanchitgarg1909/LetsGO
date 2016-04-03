package org.example.androidsdk.letsgo.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.example.androidsdk.letsgo.R;


public class LoginScreenSlideShowImageAdapter extends PagerAdapter {
    private Activity _activity;
    private int[] _imagePaths;
    private LayoutInflater inflater;

    // constructor
    public LoginScreenSlideShowImageAdapter(Activity activity,
                                            int[] imagePaths) {
        this._activity = activity;
        this._imagePaths = imagePaths;
        inflater = (LayoutInflater) _activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this._imagePaths.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        //NetworkImageView imgDisplay;
        View viewLayout = inflater.inflate(R.layout.layout_slideshow_loginscreen, container, false);

        ImageView imgDisplay = (ImageView) viewLayout.findViewById(R.id.image);
//        Picasso.with(_activity).load(_imagePaths[position]).into(imgDisplay);

        imgDisplay.setImageResource(_imagePaths[position]);
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//        Bitmap bitmap = BitmapFactory.decodeFile(_imagePaths.get(position), options);
//        imgDisplay.setImageBitmap(bitmap);

        container.addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

}
