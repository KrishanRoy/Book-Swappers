package com.KrishanRoy.krishanroy.bookswappers.ui.controller;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.KrishanRoy.krishanroy.bookswappers.ui.TabUserBooksFragment;
import com.KrishanRoy.krishanroy.bookswappers.ui.TabUserProfileFragment;

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
        switch (position) {
            case 0:
                return new TabUserProfileFragment();
            case 1:
                return new TabUserBooksFragment();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
