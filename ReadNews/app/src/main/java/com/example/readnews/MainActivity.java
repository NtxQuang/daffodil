package com.example.readnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;

import com.example.readnews.fragment.FavoriteFragment;
import com.example.readnews.fragment.NewsFragment;
import com.example.readnews.fragment.NewsPagerAdapter;
import com.example.readnews.fragment.SavedFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, ViewPager.OnPageChangeListener {
    private FragmentPagerAdapter pagerAdapter;
    private ViewPager pager;
    private TabLayout tab;

    private NewsFragment fmNews = new NewsFragment();
    private SavedFragment fmSaved = new SavedFragment();
    private FavoriteFragment fmFavorite = new FavoriteFragment();

    private String[] PERMISSION = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE
    };

    private boolean checkPermission(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            for (String p : PERMISSION) {
                int status = 0;
                status = checkSelfPermission(p);
                if (status == PackageManager.PERMISSION_DENIED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void requestPermission(){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            requestPermissions(PERMISSION,0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkPermission()){
            initView();
        } else {
            requestPermission();
        }
    }

    private void initView() {
        pager = findViewById(R.id.pager);
        tab = findViewById(R.id.tab);
        pagerAdapter = new NewsPagerAdapter(getSupportFragmentManager(),fmNews, fmSaved, fmFavorite);
        pager.setAdapter(pagerAdapter);
        tab.setupWithViewPager(pager);
        pager.addOnPageChangeListener(this);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (checkPermission()){
            initView();
        } else {
            finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        fmNews.search(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        return true;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public SavedFragment getFmSaved() {
        return fmSaved;
    }

    public FavoriteFragment getFmFavorite() {
        return fmFavorite;
    }
}
