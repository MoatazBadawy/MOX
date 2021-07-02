package com.moataz.mox.ui.view.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.moataz.mox.R;
import com.moataz.mox.databinding.FragmentMainBinding;
import com.moataz.mox.ui.adapter.ViewPagerAdapter;
import com.moataz.mox.utils.helper.CheckNetwork;
import com.moataz.mox.utils.helper.IOnBackPressed;

import java.util.Objects;

public class HomeFragment extends Fragment implements IOnBackPressed {

    FragmentMainBinding binding;
    ViewPagerAdapter adapter;
    BottomSheetDialog bottomSheetDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        initializeViewPager();
        showBottomSheetDialog();
        return view;
    }

    private void initializeViewPager() {
        adapter = new ViewPagerAdapter(this,0,requireActivity().getSupportFragmentManager());
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setOffscreenPageLimit(6); // make TabLayout not Update the data when swipe
        binding.tabs.setupWithViewPager(binding.viewPager);
    }

    private void showBottomSheetDialog() {
        bottomSheetDialog = new BottomSheetDialog(requireContext(),R.style.BottomSheetDialogTheme);
        bottomSheetDialog.setContentView(R.layout.bottomsheet_no_internet);
        if (CheckNetwork.isInternetAvailable(requireActivity())) {
            bottomSheetDialog.dismiss();
        } else {
            bottomSheetDialog.setCanceledOnTouchOutside(false);
            bottomSheetDialog.show();
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

    @Override
    public boolean onBackPressed() {
        requireActivity().moveTaskToBack(true);
        requireActivity().finish();
        return true;
    }
}