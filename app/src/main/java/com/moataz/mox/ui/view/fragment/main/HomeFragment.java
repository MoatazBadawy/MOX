package com.moataz.mox.ui.view.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;
import com.moataz.mox.R;
import com.moataz.mox.databinding.FragmentHomeBinding;
import com.moataz.mox.ui.adapter.main.ViewPagerAdapter;
import com.moataz.mox.utils.IOnBackPressed;

public class HomeFragment extends BottomSheetDialogFragment implements IOnBackPressed {

    FragmentHomeBinding binding;
    ViewPagerAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        initializeViewPager();
        return view;
    }

    private void initializeViewPager() {
        adapter = new ViewPagerAdapter(this, 0, requireActivity().getSupportFragmentManager());
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setOffscreenPageLimit(6); // make TabLayout not Update the data when swipe
        binding.tabs.setupWithViewPager(binding.viewPager);

        binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                RecyclerView mRecyclerView = requireActivity().findViewById(R.id.recyclerView_articles); // mine one is RecyclerView
                if (mRecyclerView.getAdapter() != null && mRecyclerView.getAdapter().getItemCount() > 0) {
                    mRecyclerView.smoothScrollToPosition(0); //scroll to top
                }
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