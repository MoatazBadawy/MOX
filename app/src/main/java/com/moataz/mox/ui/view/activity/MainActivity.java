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
import com.moataz.mox.ui.helper.IOnBackPressed;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeView();
        setupShortcuts();
        setupNotification();
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
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,
                new HomeFragment()).commit();
    }

    private void setupNotification() {
        NotificationMorning.setupMorningNotification(this);
        NotificationAfternoon.setupAfternoonNotification(this);
    }

    private void setupShortcuts() {
        Shortcuts.setupShortcuts(this);
    }
}