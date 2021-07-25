package com.moataz.mox.ui.view.activity;

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
import androidx.fragment.app.FragmentActivity;

import com.moataz.mox.R;
import com.moataz.mox.databinding.ActivityMainBinding;
import com.moataz.mox.ui.view.brush.NotificationAfternoon;
import com.moataz.mox.ui.view.brush.NotificationMorning;
import com.moataz.mox.ui.view.brush.Shortcuts;
import com.moataz.mox.utils.IOnBackPressed;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

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

    private void setupShortcuts() {
        Shortcuts.setupShortcuts(this);
    }

    private void setupNotification() {
        NotificationMorning.setupMorningNotification(this);
        NotificationAfternoon.setupAfternoonNotification(this);
    }

    private void initializeBottomNavigation() {
        FragmentActivity activity = this;
        BottomNavigation bottomNavigation = new BottomNavigation(this, activity);
        bottomNavigation.initializeBottomNavigation(binding.bottomNavigation);
    }

    public void setHomeItemBack() {
        binding.bottomNavigation.setSelectedItemId(R.id.home_item);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_layout);
        if (!(fragment instanceof IOnBackPressed)) {
            super.onBackPressed();
        }

        int selectedItemId = binding.bottomNavigation.getSelectedItemId();
        if (R.id.home_item != selectedItemId) {
            setHomeItemBack();
        } else {
            super.onBackPressed();
        }
    }
}
