package com.example.krishanroy.bookswappers.ui.controller;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ProfileAdapter extends FragmentPagerAdapter {
    private Context context;
    private int totalTabs;

    public ProfileAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:

        }
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
