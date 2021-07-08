package com.moataz.mox.ui.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moataz.mox.R;
import com.moataz.mox.ui.view.brush.NotificationAfternoon;
import com.moataz.mox.ui.view.brush.NotificationMorning;
import com.moataz.mox.ui.view.fragment.PremiumFragment;
import com.moataz.mox.ui.view.fragment.SavedFragment;
import com.moataz.mox.ui.view.fragment.SearchFragment;
import com.moataz.mox.ui.view.fragment.HomeFragment;
import com.moataz.mox.ui.view.brush.Shortcuts;
import com.moataz.mox.utils.IOnBackPressed;

public class MainActivity extends AppCompatActivity  {

    final Fragment homeFragment = new HomeFragment();
    final Fragment searchFragment = new SearchFragment();
    final Fragment savedFragment = new SavedFragment();
    final Fragment premiumFragment = new PremiumFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = homeFragment;

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // Initialize statue bar
            Window window = getWindow();
            View decorView = window.getDecorView();
            WindowInsetsControllerCompat windowInsetsControllerCompat = new WindowInsetsControllerCompat(window, decorView);
            windowInsetsControllerCompat.setAppearanceLightStatusBars(true);
            window.setStatusBarColor(Color.WHITE);
        }
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
    public void initializeBottomNavigation() {
        // first one transaction to add each Fragment
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_layout, homeFragment, "1");
        ft.add(R.id.fragment_layout, searchFragment, "2").hide(searchFragment);
        ft.add(R.id.fragment_layout, savedFragment, "3").hide(savedFragment);
        ft.add(R.id.fragment_layout, premiumFragment, "4").hide(premiumFragment);
        // commit once! to finish the transaction
        ft.commit();

        // show and hide them when click on BottomNav items
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(item -> {
            // start a new transaction
            FragmentTransaction ft1 = fm.beginTransaction();
            switch (item.getItemId()) {
                case R.id.home_item:
                    ft1.hide(active).show(homeFragment).commit();
                    active = homeFragment;
                    return true;

                case R.id.search_item:
                    ft1.hide(active).show(searchFragment).commit();
                    active = searchFragment;
                    return true;

                case R.id.saved_item:
                    ft1.hide(active).show(savedFragment).commit();
                    active = savedFragment;
                    return true;

                case R.id.premium_item:
                    ft1.hide(active).show(premiumFragment).commit();
                    active = premiumFragment;
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

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        int selectedItemId = bottomNavigationView.getSelectedItemId();
        if (R.id.home_item != selectedItemId) {
            setHomeItemBack(MainActivity.this);
        } else {
            super.onBackPressed();
        }
    }
}