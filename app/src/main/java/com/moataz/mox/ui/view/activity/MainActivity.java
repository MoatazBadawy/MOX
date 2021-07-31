package com.moataz.mox.ui.view.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moataz.mox.R;
import com.moataz.mox.ui.view.brush.NotificationAfternoon;
import com.moataz.mox.ui.view.brush.NotificationMorning;
import com.moataz.mox.ui.view.brush.Shortcuts;
import com.moataz.mox.ui.view.fragment.FavouriteFragment;
import com.moataz.mox.ui.view.fragment.HomeFragment;
import com.moataz.mox.ui.view.fragment.PremiumFragment;
import com.moataz.mox.ui.view.fragment.SearchFragment;
import com.moataz.mox.ui.view.fragment.VideosFragment;
import com.moataz.mox.utils.IOnBackPressed;

public class MainActivity extends AppCompatActivity  {

    final Fragment homeFragment = new HomeFragment();
    final Fragment searchFragment = new SearchFragment();
    final Fragment videosFragment = new VideosFragment();
    final Fragment favouriteFragment = new FavouriteFragment();
    final Fragment premiumFragment = new PremiumFragment();
    final FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment mainFragment = homeFragment;

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeView();
        setupShortcuts();
        setupNotification();
        initializeBottomNavigation();
    }

    private void initializeView() {
        // Initialize statue bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            View decorView = window.getDecorView();
            WindowInsetsControllerCompat windowInsetsControllerCompat = new WindowInsetsControllerCompat(window, decorView);
            windowInsetsControllerCompat.setAppearanceLightStatusBars(true);
            window.setStatusBarColor(Color.WHITE);
        }
        // Make the app support only English View "left to Right"
        ViewCompat.setLayoutDirection(getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR);
    }

    private void setupNotification() {
        NotificationMorning.setupMorningNotification(this);
        NotificationAfternoon.setupAfternoonNotification(this);
    }

    private void setupShortcuts() {
        Shortcuts.setupShortcuts(this);
    }


    @SuppressLint("NonConstantResourceId")
    private void initializeBottomNavigation() {
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
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemRippleColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        bottomNavigationView.setOnItemSelectedListener(item -> {
            // start a new transaction
            FragmentTransaction localFragmentTransaction = fragmentManager.beginTransaction();
            localFragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
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

    public static void setHomeItemBack(Activity activity) {
        BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home_item);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_layout);
        if (!(fragment instanceof IOnBackPressed)) {
            super.onBackPressed();
        }

        // Select the right bottom navigation when press back
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        int selectedItemId = bottomNavigationView.getSelectedItemId();
        if (R.id.home_item != selectedItemId) {
            setHomeItemBack(MainActivity.this);
        } else {
            super.onBackPressed();
        }
    }
}