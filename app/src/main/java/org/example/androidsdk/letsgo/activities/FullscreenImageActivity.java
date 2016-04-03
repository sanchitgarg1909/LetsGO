package org.example.androidsdk.letsgo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import org.example.androidsdk.letsgo.R;
import org.example.androidsdk.letsgo.adapters.FullscreenImageAdapter;
import org.example.androidsdk.letsgo.helper.HackyViewPager;

import java.util.ArrayList;

public class FullscreenImageActivity extends Activity {

	private FullscreenImageAdapter adapter;
	private HackyViewPager viewPager;
	private ArrayList<String> imagePaths = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen);
		viewPager = (HackyViewPager) findViewById(R.id.pager);
		Intent i = getIntent();
		int position = i.getIntExtra("position", 0);
		imagePaths = i.getStringArrayListExtra("images");
		adapter = new FullscreenImageAdapter(this, imagePaths);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(position);
	}
}
