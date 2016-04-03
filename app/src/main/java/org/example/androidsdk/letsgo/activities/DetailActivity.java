package org.example.androidsdk.letsgo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
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
import org.example.androidsdk.letsgo.helper.ServerRequest;
import org.example.androidsdk.letsgo.interfaces.GetStringCallback;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private String eventId;
    private ArrayList<String> imageUrl = new ArrayList<>();
    private ArrayList<String> userImageUrl = new ArrayList<>();
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    DetailScreenSlideShowImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        eventId = getIntent().getStringExtra("eventid");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("LetsGobjjjjjjjj");
//        toolbar.setTitle("letsgo");
        imageAdapter = new DetailScreenSlideShowImageAdapter(this, imageUrl);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(imageAdapter);
        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
        float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(3 * density);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            int intColorCode = 0;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                intColorCode = -(verticalOffset);
                if (intColorCode > 255)
                    intColorCode = 255;
                toolbar.getBackground().setAlpha(intColorCode);
//                toolbar.setAlpha(intColorCode);
            }
        });
        makeServerRequest();
        TextView photos = (TextView) findViewById(R.id.photos);
//        photos.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DetailActivity.this,GridviewImageActivity.class);
//                intent.putExtra("userimageurl",userImageUrl);
//                startActivity(intent);
//            }
//        });
    }

    public void makeServerRequest(){
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.fetchDetails(eventId, new GetStringCallback() {
            @Override
            public void callback(String response){
                Log.d("detailresponse", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray imageArray = jsonObject.getJSONArray("imageurl");
                    for(int i=0;i<imageArray.length();i++)
                        imageUrl.add(imageArray.getString(i));
                    JSONArray userImageArray = jsonObject.getJSONArray("userimageurl");
                    for(int i=0;i<imageArray.length();i++)
                        userImageUrl.add(userImageArray.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                imageAdapter.update(imageUrl);
                imageAdapter.notifyDataSetChanged();
            }
        });
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
