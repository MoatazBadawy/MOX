package com.moataz.mox.ui.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.os.Bundle;
import android.view.View;

import com.moataz.mox.R;
import com.moataz.mox.ui.view.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeView();
    }

    private void initializeView() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR); // make the icons on Statues black
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,
                new MainFragment()).commit();
    }
}