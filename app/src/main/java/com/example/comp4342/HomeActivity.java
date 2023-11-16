package com.example.comp4342;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.comp4342.adapter.AdapterViewPager;
import com.example.comp4342.fragment.FragmentHome;
import com.example.comp4342.fragment.FragmentOrder;
import com.example.comp4342.fragment.FragmentProfile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    ViewPager2 pagerMain;
    BottomNavigationView bottomNav;
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        pagerMain = findViewById(R.id.pagerMain);
        bottomNav = findViewById(R.id.bottomNav);
        fragmentArrayList.add(new FragmentHome());
        fragmentArrayList.add(new FragmentOrder());
        fragmentArrayList.add(new FragmentProfile());

        AdapterViewPager adapterViewPager = new AdapterViewPager(this, fragmentArrayList);

        pagerMain.setAdapter(adapterViewPager);
        pagerMain.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        bottomNav.setSelectedItemId(R.id.itHome);
                        break;
                    case 1:
                        bottomNav.setSelectedItemId(R.id.itOrder);
                        break;
                    case 2:
                        bottomNav.setSelectedItemId(R.id.itProfile);
                        break;
                }
                super.onPageSelected(position);
            }
        });
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                System.out.println(item.getItemId());
                if(item.getItemId() == R.id.itHome){
                    pagerMain.setCurrentItem(0);
                }
                if(item.getItemId() == R.id.itOrder){
                    pagerMain.setCurrentItem(1);
                }
                if(item.getItemId() == R.id.itProfile){
                    pagerMain.setCurrentItem(2);
                }

                return true;
            }
        });
    }
}