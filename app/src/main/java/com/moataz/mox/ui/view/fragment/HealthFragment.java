package com.moataz.mox.ui.view.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.moataz.mox.databinding.FragmentHealthBinding;
import com.moataz.mox.ui.adapter.ArticleAdapter;
import com.moataz.mox.ui.viewmodel.HealthViewModel;
import org.jetbrains.annotations.NotNull;

public class HealthFragment extends Fragment {

    private ArticleAdapter adapter;
    private HealthViewModel viewModel;
    private FragmentHealthBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHealthBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        requireActivity().setTitle("");
        initializeViews();
        initializeViewModel();
        getList();
        onSwipeRefresh();
        return view;
    }

    private void initializeViews() {
        adapter = new ArticleAdapter();
        binding.recyclerViewHealth.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewHealth.setHasFixedSize(true);
        binding.recyclerViewHealth.setAdapter(adapter);
    }

    private void getList() {
        viewModel.makeApiCallHealth().observe(requireActivity(), response -> {
            switch (response.status){
                case ERROR: {
                    binding.errorBoldHealth.setVisibility(View.VISIBLE);
                    binding.errorMessage1Health.setVisibility(View.VISIBLE);
                    binding.errorMessage2Health.setVisibility(View.VISIBLE);
                    binding.progressBarHealth.setVisibility(View.GONE);
                    break;
                }
                case LOADING: {
                    binding.progressBarHealth.setVisibility(View.VISIBLE);
                    break;
                }
                case SUCCESS:{
                    binding.progressBarHealth.setVisibility(View.GONE);
                    adapter.setNewsList(response.data);
                    break;
                }
            }
        });
    }

    private void onSwipeRefresh() {
        binding.swipeToRefreshHealth.setOnRefreshListener(() -> viewModel.makeApiCallHealth().observe(requireActivity(), response -> {
            switch (response.status){
                case ERROR: {
                    binding.swipeToRefreshHealth.setRefreshing(false);
                    break;
                }
                case SUCCESS:{
                    binding.swipeToRefreshHealth.setRefreshing(false);
                    binding.errorBoldHealth.setVisibility(View.INVISIBLE);
                    binding.errorMessage1Health.setVisibility(View.INVISIBLE);
                    binding.errorMessage2Health.setVisibility(View.INVISIBLE);
                    adapter.setNewsList(response.data);
                    break;
                }
            }
        }));
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(HealthViewModel.class);
    }
}