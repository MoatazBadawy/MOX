package com.moataz.mox.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.moataz.mox.R;
import com.moataz.mox.ui.view.fragment.CleanFragment;
import com.moataz.mox.ui.view.fragment.RemotelyFragment;
import com.moataz.mox.ui.view.fragment.ProFragment;
import com.moataz.mox.ui.view.fragment.UIFragment;
import com.moataz.mox.ui.view.fragment.UxFragment;
import com.moataz.mox.ui.view.fragment.HomeFragment;
import com.moataz.mox.ui.view.fragment.TechFragment;
import com.moataz.mox.ui.view.fragment.AndroidFragment;
import com.moataz.mox.ui.view.fragment.TopFragment;
import com.moataz.mox.ui.view.fragment.WebFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private final HomeFragment mContext;
    private static final int NUM_PAGES = 9;

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
            return new ProFragment();
        } else if (position == 3) {
            return new AndroidFragment();
        } else if (position == 4) {
            return new WebFragment();
        }  else if (position == 5) {
            return new UIFragment();
        } else if (position == 6){
            return new UxFragment();
        } else if (position == 7){
            return new RemotelyFragment();
        }
        else {
            return new CleanFragment();
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
            return mContext.getString(R.string.web_tab);
        } else if (position == 5) {
            return mContext.getString(R.string.ui_tab);
        } else if (position == 6){
            return mContext.getString(R.string.ux_tab);
        } else if (position == 7) {
            return mContext.getString(R.string.agile_tab);
        } else {
            return mContext.getString(R.string.clean_tab);
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