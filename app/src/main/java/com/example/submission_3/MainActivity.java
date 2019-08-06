package com.example.submission_3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.submission_3.adapter.ViewAdapter;
import com.example.submission_3.favorite.FavoriteFragment;
import com.example.submission_3.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    BottomNavigationView bottomNavigationView;
    private ViewPager viewPagerBot;
    private MenuItem menuItem;

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    viewPagerBot.setCurrentItem(0);
                    return true;
                case R.id.navigation_favorite:
                    viewPagerBot.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPagerBot = findViewById(R.id.viewpager);
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        }
        viewPagerBot.addOnPageChangeListener(this);
        setViewPager(viewPagerBot);
        setTitle("Movie Catalogue");


    }

    private void setViewPager(ViewPager viewPager) {
        ViewAdapter adapter = new ViewAdapter(getSupportFragmentManager());
        adapter.addNavFragment(new HomeFragment());
        adapter.addNavFragment(new FavoriteFragment());

        viewPager.setAdapter(adapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_setting) {
            Intent mIntent = new Intent(this, SettingsActivity.class);
            startActivity(mIntent);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        if (menuItem != null) {
            menuItem.setChecked(false);
        } else {
            bottomNavigationView.getMenu().getItem(0).setChecked(false);
        }
        bottomNavigationView.getMenu().getItem(i).setChecked(true);
        menuItem = bottomNavigationView.getMenu().getItem(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }


}
