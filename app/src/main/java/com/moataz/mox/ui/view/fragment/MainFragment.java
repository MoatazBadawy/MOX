package com.moataz.mox.ui.view.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.moataz.mox.databinding.FragmentMainBinding;
import com.moataz.mox.ui.adapter.ViewPagerAdapter;

/**
 * Created by Moataz
 */
public class MainFragment extends Fragment {

    FragmentMainBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        initializeViewPager();
        return view;
    }

    private void initializeViewPager() {
        final ViewPagerAdapter adapter = new ViewPagerAdapter(this, requireActivity().getSupportFragmentManager());
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setOffscreenPageLimit(5); // make TabLayout not Update the data when swipe
        binding.tabs.setupWithViewPager(binding.viewPager);
    }
}