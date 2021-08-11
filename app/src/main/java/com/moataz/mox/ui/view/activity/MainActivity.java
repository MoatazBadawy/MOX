package com.moataz.mox.ui.view.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moataz.mox.R;
import com.moataz.mox.ui.view.fragment.FavouriteFragment;
import com.moataz.mox.ui.view.fragment.HomeFragment;
import com.moataz.mox.ui.view.fragment.PremiumFragment;
import com.moataz.mox.ui.view.fragment.SearchFragment;
import com.moataz.mox.ui.view.fragment.VideosFragment;
import com.moataz.mox.ui.view.notification.NotificationAfternoon;
import com.moataz.mox.ui.view.notification.NotificationMorning;
import com.moataz.mox.ui.view.shortcut.Shortcuts;
import com.moataz.mox.utils.IOnBackPressed;

public class MainActivity extends AppCompatActivity  {

    final FragmentManager fragmentManager = getSupportFragmentManager();

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
        // make the icons on Statues black
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        // Make the app support only English View "left to Right"
        ViewCompat.setLayoutDirection(getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR);
    }

    private void setupNotification() {
        NotificationAfternoon.setupAfternoonNotification(this);
        NotificationMorning.setupInMorningNotification(this);
    }

    private void setupShortcuts() {
        Shortcuts.setupShortcuts(this);
    }


    @SuppressLint("NonConstantResourceId")
    private void initializeBottomNavigation() {
        // first one transaction to add each Fragment

        // show and hide them when click on BottomNav items
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemRippleColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,
                new HomeFragment()).commit();
        bottomNavigationView.setOnItemSelectedListener(item -> {
            // start a new transaction
            FragmentTransaction localFragmentTransaction = fragmentManager.beginTransaction();
            localFragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.home_item:
                    selectedFragment = new HomeFragment();
                    break;

                case R.id.search_item:
                    selectedFragment = new SearchFragment();

                    break;

                case R.id.videos_item:
                    selectedFragment = new VideosFragment();

                    break;

                case R.id.saved_item:
                    selectedFragment = new FavouriteFragment();
                    break;

                case R.id.premium_item:
                    selectedFragment = new PremiumFragment();

                    break;
            }
            assert selectedFragment != null;
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, selectedFragment).commit();

            return true;
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