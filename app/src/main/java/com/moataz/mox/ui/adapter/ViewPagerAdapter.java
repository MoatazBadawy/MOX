package com.moataz.mox.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.moataz.mox.R;
import com.moataz.mox.ui.view.fragment.AgileFragment;
import com.moataz.mox.ui.view.fragment.DevFragment;
import com.moataz.mox.ui.view.fragment.UIFragment;
import com.moataz.mox.ui.view.fragment.UxFragment;
import com.moataz.mox.ui.view.fragment.HomeFragment;
import com.moataz.mox.ui.view.fragment.TechFragment;
import com.moataz.mox.ui.view.fragment.AndroidFragment;
import com.moataz.mox.ui.view.fragment.TopFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private final HomeFragment mContext;
    private static final int NUM_PAGES = 7;

    public ViewPagerAdapter(HomeFragment mContext, int behavior, @NonNull FragmentManager fm) {
        super(fm, behavior);
        this.mContext = mContext;
    }

    @NonNull
    public Fragment getItem(int position) {
        if (position == 0) {
            return new TopFragment();
        } else if (position == 1) {
            return new TechFragment();
        } else if (position == 2) {
            return new DevFragment();
        } else if (position == 3) {
            return new AndroidFragment();
        } else if (position == 4) {
            return new UxFragment();
        } else if (position == 5){
            return new UIFragment();
        } else {
            return new AgileFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.top_tab);
        } else if (position == 1) {
            return mContext.getString(R.string.tech_tab);
        } else if (position == 2) {
            return mContext.getString(R.string.pro_tab);
        } else if (position == 3) {
            return mContext.getString(R.string.android_tab);
        } else if (position == 4) {
            return mContext.getString(R.string.ux_tab);
        } else if (position == 5){
            return mContext.getString(R.string.ui_tab);
        } else {
            return mContext.getString(R.string.agile_tab);
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE; // POSITION_NONE makes it possible to reload the PagerAdapter
    }
}