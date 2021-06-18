package com.moataz.mox.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.moataz.mox.R;
import com.moataz.mox.ui.view.fragment.BusinessFragment;
import com.moataz.mox.ui.view.fragment.HealthFragment;
import com.moataz.mox.ui.view.fragment.MainFragment;
import com.moataz.mox.ui.view.fragment.ScienceFragment;
import com.moataz.mox.ui.view.fragment.SportsFragment;
import com.moataz.mox.ui.view.fragment.TechnologyFragment;
import com.moataz.mox.ui.view.fragment.TopFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private final MainFragment mContext;
    private static final int NUM_PAGES = 6;
    public ViewPagerAdapter(MainFragment context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    public Fragment getItem(int position) {
        if (position == 0) {
            return new TopFragment();
        } else if (position == 1) {
            return new TechnologyFragment();
        } else if (position == 2) {
            return new BusinessFragment();
        } else if (position == 3) {
            return new SportsFragment();
        } else if (position == 4) {
            return new HealthFragment();
        } else {
            return new ScienceFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.tob_tabe);
        } else if (position == 1) {
            return mContext.getString(R.string.technology_tabe);
        } else if (position == 2) {
            return mContext.getString(R.string.business_tabe);
        } else if (position == 3) {
            return mContext.getString(R.string.sports_tabe);
        } else if (position == 4) {
            return mContext.getString(R.string.health_tabe);
        } else {
            return mContext.getString(R.string.science_tabe);
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
