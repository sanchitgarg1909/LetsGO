package org.example.androidsdk.letsgo.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.facebook.FacebookSdk;
import com.viewpagerindicator.CirclePageIndicator;

import org.example.androidsdk.letsgo.R;
import org.example.androidsdk.letsgo.adapters.LoginScreenSlideShowImageAdapter;

public class MainActivity extends AppCompatActivity {
    private int [] imagePaths = {R.drawable.office1,R.drawable.office2,R.drawable.office3};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        LoginScreenSlideShowImageAdapter adapter = new LoginScreenSlideShowImageAdapter(this,imagePaths);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setPageTransformer(false, new FadePageTransformer());
        viewPager.setAdapter(adapter);
        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
        float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density);
    }

    public class FadePageTransformer implements ViewPager.PageTransformer {
        public void transformPage(View view, float position) {
            view.setTranslationX(view.getWidth() * -position);
            if(position <= -1.0F || position >= 1.0F) {
                view.setAlpha(0.0F);
            } else if( position == 0.0F ) {
                view.setAlpha(1.0F);
            } else {
                // position is between -1.0F & 0.0F OR 0.0F & 1.0F
                view.setAlpha(1.0F - Math.abs(position));
            }
        }
    }
}
