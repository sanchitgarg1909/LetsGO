package org.example.androidsdk.letsgo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;

import org.example.androidsdk.letsgo.R;
import org.example.androidsdk.letsgo.adapters.DetailScreenSlideShowImageAdapter;
import org.example.androidsdk.letsgo.adapters.TabsPagerAdapter;
import org.example.androidsdk.letsgo.eventBusEvents.RequestCompleted;
import org.example.androidsdk.letsgo.fragments.EventsTabFragment;
import org.example.androidsdk.letsgo.fragments.InfoTabFragment;
import org.example.androidsdk.letsgo.fragments.MapFragment;
import org.example.androidsdk.letsgo.fragments.PhotosTabFragment;
import org.example.androidsdk.letsgo.fragments.PlacesTabFragment;
import org.example.androidsdk.letsgo.fragments.ReviewsTabFragment;
import org.example.androidsdk.letsgo.helper.ServerRequest;
import org.example.androidsdk.letsgo.interfaces.GetStringCallback;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class PlacesDetailActivity extends AppCompatActivity {

    private EventBus bus = EventBus.getDefault();
    private String eventId,details;
    private ArrayList<String> imageUrl = new ArrayList<>();
//    private ArrayList<String> userImageUrl = new ArrayList<>();
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    DetailScreenSlideShowImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_places);
        eventId = getIntent().getStringExtra("eventid");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbarLayout.setTitle("LetsGoj");
//        toolbar.setTitle("letsgo");
        imageAdapter = new DetailScreenSlideShowImageAdapter(this, imageUrl);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(imageAdapter);
        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
        float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(3 * density);
//        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            int intColorCode = 0;
//
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                intColorCode = -(verticalOffset);
//                if (intColorCode > 255)
//                    intColorCode = 255;
//                toolbar.getBackground().setAlpha(intColorCode);
////                toolbar.setAlpha(intColorCode);
//            }
//        });
        makeServerRequest();
        initViewPagerAndTabs();
//        TextView photos = (TextView) findViewById(R.id.photos);
//        photos.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(PlacesDetailActivity.this,GridviewImageActivity.class);
//                intent.putExtra("userimageurl",userImageUrl);
//                startActivity(intent);
//            }
//        });
    }

    private void initViewPagerAndTabs() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        TabsPagerAdapter pagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new InfoTabFragment(), "Info");
        pagerAdapter.addFragment(new PhotosTabFragment(), "Photos");
        pagerAdapter.addFragment(new ReviewsTabFragment(),"Reviews");
        viewPager.setAdapter(pagerAdapter);
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }

    public void makeServerRequest(){
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.fetchDetails(eventId, new GetStringCallback() {
            @Override
            public void callback(String response) {
                details = response;
                Log.d("detailresponse", details);
                try {
                    JSONObject jsonObject = new JSONObject(details);
                    JSONArray imageArray = jsonObject.getJSONArray("imageurl");
                    for (int i = 0; i < imageArray.length(); i++)
                        imageUrl.add(imageArray.getString(i));
//                    JSONArray userImageArray = jsonObject.getJSONArray("userimageurl");
//                    for (int i = 0; i < imageArray.length(); i++)
//                        userImageUrl.add(userImageArray.getString(i));
                    collapsingToolbarLayout.setTitle(jsonObject.getString("name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                imageAdapter.update(imageUrl);
                imageAdapter.notifyDataSetChanged();
                postEventBus();
            }
        });
    }

    public void postEventBus(){
        bus.post(new RequestCompleted(details));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
