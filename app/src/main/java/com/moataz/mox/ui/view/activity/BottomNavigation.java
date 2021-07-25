package com.moataz.mox.ui.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moataz.mox.R;
import com.moataz.mox.ui.view.fragment.FavouriteFragment;
import com.moataz.mox.ui.view.fragment.HomeFragment;
import com.moataz.mox.ui.view.fragment.PremiumFragment;
import com.moataz.mox.ui.view.fragment.SearchFragment;
import com.moataz.mox.ui.view.fragment.VideosFragment;

@SuppressLint("ViewConstructor")
public class BottomNavigation extends BottomNavigationView {

    FragmentActivity fragmentActivity;
    final Fragment homeFragment = new HomeFragment();
    final Fragment searchFragment = new SearchFragment();
    final Fragment videosFragment = new VideosFragment();
    final Fragment favouriteFragment = new FavouriteFragment();
    final Fragment premiumFragment = new PremiumFragment();
    final FragmentManager fragmentManager;
    Fragment mainFragment = homeFragment;

    public BottomNavigation(@NonNull Context context, @NonNull FragmentActivity activity) {
        super(context);
        fragmentActivity = activity;
        fragmentManager = fragmentActivity.getSupportFragmentManager();
    }

    @SuppressLint("NonConstantResourceId")
    public void initializeBottomNavigation(BottomNavigationView bottomNavigationView) {
        // first one transaction to add each Fragment
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_layout, premiumFragment, "5").hide(premiumFragment);
        fragmentTransaction.add(R.id.fragment_layout, favouriteFragment, "4").hide(favouriteFragment);
        fragmentTransaction.add(R.id.fragment_layout, videosFragment, "3").hide(videosFragment);
        fragmentTransaction.add(R.id.fragment_layout, searchFragment, "2").hide(searchFragment);
        fragmentTransaction.add(R.id.fragment_layout, homeFragment, "1");
        // commit once! to finish the transaction
        fragmentTransaction.commit();

        // show and hide them when click on BottomNav items
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            // start a new transaction
            FragmentTransaction localFragmentTransaction = fragmentManager.beginTransaction();
            // TODO: ADD Animations
            switch (item.getItemId()) {
                case R.id.home_item:
                    localFragmentTransaction.hide(mainFragment).show(homeFragment).commit();
                    mainFragment = homeFragment;
                    return true;

                case R.id.search_item:
                    localFragmentTransaction.hide(mainFragment).show(searchFragment).commit();
                    mainFragment = searchFragment;
                    return true;

                case R.id.videos_item:
                    localFragmentTransaction.hide(mainFragment).show(videosFragment).commit();
                    mainFragment = videosFragment;
                    return true;

                case R.id.saved_item:
                    localFragmentTransaction.hide(mainFragment).show(favouriteFragment).commit();
                    mainFragment = favouriteFragment;
                    return true;

                case R.id.premium_item:
                    localFragmentTransaction.hide(mainFragment).show(premiumFragment).commit();
                    mainFragment = premiumFragment;
                    return true;
            }
            return false;
        });
    }
}
