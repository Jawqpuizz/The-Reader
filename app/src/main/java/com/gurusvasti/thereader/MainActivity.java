package com.gurusvasti.thereader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomnavonView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomnavonView = findViewById(R.id.bottom_navigation);

        bottomnavonView.setOnNavigationItemSelectedListener(bottomnavmethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_wrapper,new HomeFragment()).commit(); // start with the home fragment

    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomnavmethod = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()){
                case R.id.camera:
                    fragment = new CameraFragment();
                    break;
                case R.id.profile:
                    fragment = new ProfileFragment();
                    break;
                default:
                    fragment = new HomeFragment();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_wrapper,fragment).commit();

            return true;
        }
    };







}
