package com.moataz.mox.ui.view.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.moataz.mox.databinding.FragmentSportsBinding;
import com.moataz.mox.ui.adapter.MediumAdapter;
import com.moataz.mox.ui.viewmodel.DevViewModel;

import org.jetbrains.annotations.NotNull;

public class DevFragment extends Fragment {

    private MediumAdapter adapter;
    private DevViewModel viewModel;
    private FragmentSportsBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSportsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        requireActivity().setTitle("");
        initializeViews();
        initializeViewModel();
        getList();
        onSwipeRefresh();
        return view;
    }

    private void initializeViews() {
        adapter = new MediumAdapter();
        binding.recyclerViewSports.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewSports.setHasFixedSize(true);
        binding.recyclerViewSports.setAdapter(adapter);
    }

    private void getList() {
        viewModel.makeApiCallDevArticles().observe(requireActivity(), response -> {
            switch (response.status){
                case ERROR: {
                    binding.progressBarSports.setVisibility(View.GONE);
                    break;
                }
                case LOADING: {
                    binding.progressBarSports.setVisibility(View.VISIBLE);
                    break;
                }
                case SUCCESS:{
                    binding.progressBarSports.setVisibility(View.GONE);
                    adapter.setMediumList(response.data);
                    break;
                }
            }
        });
    }

    private void onSwipeRefresh() {
        binding.swipeToRefreshSports.setOnRefreshListener(() -> viewModel.makeApiCallDevArticles().observe(requireActivity(), response -> {
            switch (response.status){
                case ERROR: {
                    binding.swipeToRefreshSports.setRefreshing(false);
                    break;
                }
                case SUCCESS:{
                    binding.swipeToRefreshSports.setRefreshing(false);
                    adapter.setMediumList(response.data);
                    break;
                }
            }
        }));
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(DevViewModel.class);
    }
}