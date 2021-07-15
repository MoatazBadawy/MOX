package com.moataz.mox.ui.view.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.moataz.mox.R;
import com.moataz.mox.databinding.FragmentHomeBinding;
import com.moataz.mox.ui.adapter.ViewPagerAdapter;
import com.moataz.mox.utils.CheckNetwork;

public class HomeFragment extends BottomSheetDialogFragment {

    FragmentHomeBinding binding;
    ViewPagerAdapter adapter;
    BottomSheetDialog bottomSheetDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        initializeViewPager();
        showBottomSheetDialog();
        return view;
    }

    private void initializeViewPager() {
        adapter = new ViewPagerAdapter(this,0,requireActivity().getSupportFragmentManager());
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setOffscreenPageLimit(8); // make TabLayout not Update the data when swipe
        binding.tabs.setupWithViewPager(binding.viewPager);
    }

    private void showBottomSheetDialog() {
        bottomSheetDialog = new BottomSheetDialog(requireContext(),R.style.BottomSheetDialogTheme);
        bottomSheetDialog.setContentView(R.layout.bottomsheet_no_internet);
        if (CheckNetwork.isInternetAvailable(requireActivity())) {
            bottomSheetDialog.dismiss();
        } else {
            setupBottomSheetDialog();
        }
        Button buttonNoInternet = bottomSheetDialog.findViewById(R.id.buttonNoInternet);
        assert buttonNoInternet != null;
        buttonNoInternet.setOnClickListener(v -> {
            if (CheckNetwork.isInternetAvailable(requireActivity())) {
                adapter.notifyDataSetChanged();
                bottomSheetDialog.dismiss();
            } else {
                bottomSheetDialog.dismiss();
                adapter.notifyDataSetChanged();
                bottomSheetDialog.show();
            }
        });
    }

    private void setupBottomSheetDialog() {
        bottomSheetDialog = new BottomSheetDialog(requireContext(),R.style.BottomSheetDialogTheme) {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setOnKeyListener((dialog, keyCode, event) -> {
                    if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                        bottomSheetDialog.dismiss();
                        requireActivity().moveTaskToBack(true);
                        requireActivity().finish();
                        return true;
                    }
                    return false;
                });

            }
        };
        bottomSheetDialog.setContentView(R.layout.bottomsheet_no_internet);
        bottomSheetDialog.setCancelable(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (CheckNetwork.isInternetAvailable(requireActivity())) {
            bottomSheetDialog.dismiss();
        } else {
            bottomSheetDialog.show();
        }
    }
}