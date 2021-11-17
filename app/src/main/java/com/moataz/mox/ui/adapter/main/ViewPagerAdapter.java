package com.moataz.mox.ui.adapter.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.moataz.mox.R;
import com.moataz.mox.ui.view.fragment.home.RemotelyFragment;
import com.moataz.mox.ui.view.fragment.home.ProFragment;
import com.moataz.mox.ui.view.fragment.home.FrontEndFragment;
import com.moataz.mox.ui.view.fragment.home.UxFragment;
import com.moataz.mox.ui.view.fragment.main.HomeFragment;
import com.moataz.mox.ui.view.fragment.home.TechFragment;
import com.moataz.mox.ui.view.fragment.home.AndroidFragment;
import com.moataz.mox.ui.view.fragment.home.TopFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private final HomeFragment mContext;
    private static final int NUM_PAGES = 7;

    public ViewPagerAdapter(HomeFragment context, int behavior, @NonNull FragmentManager fragmentManager) {
        super(fragmentManager, behavior);
        this.mContext = context;
    }

    @NonNull
    public Fragment getItem(int position) {
        if (position == 0) {
            return new TopFragment();
        } else if (position == 1) {
            return new TechFragment();
        } else if (position == 2) {
            return new ProFragment();
        } else if (position == 3) {
            return new AndroidFragment();
        } else if (position == 4) {
            return new FrontEndFragment();
        } else if (position == 5) {
            return new UxFragment();
        } else {
            return new RemotelyFragment();
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
            return mContext.getString(R.string.ui_tab);
        } else if (position == 5) {
            return mContext.getString(R.string.ux_tab);
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