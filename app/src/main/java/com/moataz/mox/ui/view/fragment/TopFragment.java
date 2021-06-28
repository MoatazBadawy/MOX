package com.moataz.mox.ui.view.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.moataz.mox.databinding.FragmentTopBinding;
import com.moataz.mox.ui.adapter.CNNAdapter;
import com.moataz.mox.ui.viewmodel.TopViewModel;
import org.jetbrains.annotations.NotNull;

public class TopFragment extends Fragment {

    private CNNAdapter adapter;
    private TopViewModel viewModel;
    private FragmentTopBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTopBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        requireActivity().setTitle("");
        initializeViews();
        initializeViewModel();
        getList();
        onSwipeRefresh();
        return view;
    }

    private void initializeViews() {
        adapter = new CNNAdapter();
        binding.recyclerViewTop.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewTop.setHasFixedSize(true);
        binding.recyclerViewTop.setAdapter(adapter);
    }

    private void getList() {
        viewModel.makeApiCallTopArticles().observe(requireActivity(), response -> {
            switch (response.status){
                case ERROR: {
                    binding.progressBarTop.setVisibility(View.GONE);
                    break;
                }
                case LOADING: {
                    binding.progressBarTop.setVisibility(View.VISIBLE);
                    break;
                }
                case SUCCESS:{
                    binding.progressBarTop.setVisibility(View.GONE);
                    adapter.setCNNList(response.data);
                    break;
                }
            }
        });
    }

    private void onSwipeRefresh() {
        binding.swipeToRefreshTop.setOnRefreshListener(() -> viewModel.makeApiCallTopArticles().observe(requireActivity(), response -> {
            switch (response.status){
                case ERROR: {
                    binding.swipeToRefreshTop.setRefreshing(false);
                    break;
                }
                case SUCCESS:{
                    binding.swipeToRefreshTop.setRefreshing(false);
                    adapter.setCNNList(response.data);
                    break;
                }
            }
        }));
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(TopViewModel.class);
    }
}