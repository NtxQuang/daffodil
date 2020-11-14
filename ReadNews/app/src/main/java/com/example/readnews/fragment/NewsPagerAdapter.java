package com.example.readnews.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class NewsPagerAdapter extends FragmentPagerAdapter {

    Fragment[] fragments;

    public NewsPagerAdapter(@NonNull FragmentManager fm, Fragment...fragments) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragments= fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    public CharSequence getPageTitle(int position){
        switch (position){
            case 0: return "News";
            case 1: return "Saved";
            case 2: return "Favorite";
        }
        return super.getPageTitle(position);
    }
}
