package org.example.androidsdk.letsgo.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.example.androidsdk.letsgo.R;
import org.example.androidsdk.letsgo.activities.HomeActivity;
import org.example.androidsdk.letsgo.adapters.TabsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_list, container, false);
//        initToolbar();
        initViewPagerAndTabs();
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fabButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity) getActivity()).openMap();
            }
        });
        return rootView;
    }
//    private void initToolbar() {
//        Toolbar mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
//        mToolbar.setTitle(getString(R.string.app_name));
//        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
//        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
//    }
    private void initViewPagerAndTabs() {
        final ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        TabsPagerAdapter pagerAdapter = new TabsPagerAdapter(getChildFragmentManager());
        pagerAdapter.addFragment(new PlacesTabFragment(), "places");
        pagerAdapter.addFragment(new EventsTabFragment(), "events");
        viewPager.setAdapter(pagerAdapter);
        final TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }
}
