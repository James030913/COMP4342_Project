package com.example.comp4342.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.comp4342.fragment.FragmentOrder;

import java.util.ArrayList;

public class AdapterViewPager extends FragmentStateAdapter {

    ArrayList<Fragment> arr;

    public AdapterViewPager(@NonNull FragmentActivity fragmentActivity, ArrayList<Fragment> arr) {
        super(fragmentActivity);
        this.arr = arr;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            // Always return a new instance for the Order fragment
            return new FragmentOrder();
        }

        return arr.get(position);
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }
}
