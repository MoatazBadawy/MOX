package com.moataz.mox.ui.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moataz.mox.R;
import com.moataz.mox.databinding.FragmentAgileBinding;
import com.moataz.mox.ui.adapter.MediumAdapter;
import com.moataz.mox.ui.viewmodel.AgileViewModel;
import com.moataz.mox.ui.viewmodel.AndroidViewModel;

import org.jetbrains.annotations.NotNull;

public class AgileFragment extends Fragment {

    private MediumAdapter adapter;
    private AgileViewModel viewModel;
    private FragmentAgileBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAgileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        requireActivity().setTitle("");
        initializeViews();
        initializeViewModel();
        getTopList();
        onSwipeRefresh();
        return view;
    }

    private void initializeViews() {
        adapter = new MediumAdapter();
        binding.recyclerViewAgile.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewAgile.setHasFixedSize(true);
        binding.recyclerViewAgile.setAdapter(adapter);
    }

    private void getTopList() {
        viewModel.makeApiCallAgileArticles().observe(requireActivity(), response -> {
            switch (response.status){
                case ERROR: {
                    binding.progressBarAgile.setVisibility(View.GONE);
                    break;
                }
                case LOADING: {
                    binding.progressBarAgile.setVisibility(View.VISIBLE);
                    break;
                }
                case SUCCESS:{
                    binding.progressBarAgile.setVisibility(View.GONE);
                    adapter.setMediumList(response.data);
                    break;
                }
            }
        });
    }

    private void onSwipeRefresh() {
        binding.swipeToRefreshAgile.setOnRefreshListener(() -> viewModel.makeApiCallAgileArticles().observe(requireActivity(), response -> {
            switch (response.status){
                case ERROR: {
                    binding.swipeToRefreshAgile.setRefreshing(false);
                    break;
                }
                case SUCCESS:{
                    binding.swipeToRefreshAgile.setRefreshing(false);
                    adapter.setMediumList(response.data);
                    break;
                }
            }
        }));
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(AgileViewModel.class);
    }
}