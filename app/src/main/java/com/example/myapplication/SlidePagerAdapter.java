package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class SlidePagerAdapter extends FragmentStateAdapter {
    private ArrayList<String> class_names;
    private ArrayList<Integer> imageResIds;

    public SlidePagerAdapter(@NonNull FragmentActivity fa, ArrayList<String> class_names) {
        super(fa);
        this.class_names = class_names;
//        this.imageResIds = imageResIds
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return SlidePageFragment.newInstance(class_names.get(position));
    }

    @Override
    public int getItemCount() {
        return class_names.size();
    }
}
