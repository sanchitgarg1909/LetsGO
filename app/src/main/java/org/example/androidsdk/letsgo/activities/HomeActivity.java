package org.example.androidsdk.letsgo.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.example.androidsdk.letsgo.R;
import org.example.androidsdk.letsgo.fragments.ExploreFragment;
import org.example.androidsdk.letsgo.fragments.GroupFragment;
import org.example.androidsdk.letsgo.fragments.ProfileFragment;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment fragment;
    private MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        exploreFragment();
//        newFragment();
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setEllipsize(true);
//        searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(HomeActivity.this,query,Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            exploreFragment();
        } else if (id == R.id.nav_gallery) {
            profileFragment();
        } else if (id == R.id.nav_slideshow) {
            groupFragment();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void exploreFragment(){
        fragment = new ExploreFragment();
        Bundle bundle = new Bundle();
//        bundle.putString("loginToken",loginToken);
//        bundle.putString("userId", userId);
        fragment.setArguments(bundle);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_body, fragment).commit();
        }
    }
    public void profileFragment(){
        fragment = new ProfileFragment();
        Bundle bundle = new Bundle();
//        bundle.putString("loginToken",loginToken);
//        bundle.putString("userId", userId);
        fragment.setArguments(bundle);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_body, fragment).commit();
        }
    }
    public void groupFragment(){
        fragment = new GroupFragment();
        Bundle bundle = new Bundle();
//        bundle.putString("loginToken",loginToken);
//        bundle.putString("userId", userId);
        fragment.setArguments(bundle);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_body, fragment).commit();
        }
    }
    public void openMap(){
        ExploreFragment fragment = (ExploreFragment) getSupportFragmentManager().findFragmentById(R.id.container_body);
        fragment.openMapFragment();
    }
    public void openList(){
        ExploreFragment fragment = (ExploreFragment) getSupportFragmentManager().findFragmentById(R.id.container_body);
        fragment.openListFragment();
    }
    public void postEventBus(){
        ExploreFragment fragment = (ExploreFragment) getSupportFragmentManager().findFragmentById(R.id.container_body);
        fragment.postEventBus();
    }
    public void makeServerRequest(){
        ExploreFragment fragment = (ExploreFragment) getSupportFragmentManager().findFragmentById(R.id.container_body);
        fragment.makeServerRequest();
    }
}
