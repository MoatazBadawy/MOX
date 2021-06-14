package com.moataz.mox.ui.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moataz.mox.data.model.ArticlesModel;
import com.moataz.mox.databinding.FragmentTopBinding;
import com.moataz.mox.ui.adapter.NewsAdapter;
import com.moataz.mox.ui.viewmodel.TopViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TopFragment extends Fragment {

    private NewsAdapter adapter;
    private TopViewModel viewModel;
    private FragmentTopBinding binding;
    private List<ArticlesModel> movieModelList;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTopBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        requireActivity().setTitle("");
        initializeViews();
        initializeViewModel();
        getTopList();
        onSwipeRefresh();
        return view;
    }

    private void initializeViews() {
        adapter = new NewsAdapter(getContext(),movieModelList);
        binding.recyclerViewTop.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewTop.setHasFixedSize(true);
        binding.recyclerViewTop.setAdapter(adapter);
    }

    private void getTopList() {
        viewModel.getMoviesListObserver().observe(getViewLifecycleOwner(), movieModels -> {
            if(movieModels != null) {
                movieModelList = movieModels;
                adapter.setNewsList(movieModels);
            } else {
                binding.errorBoldTop.setVisibility(View.VISIBLE);
                binding.errorMessage1Top.setVisibility(View.VISIBLE);
                binding.errorMessage2Top.setVisibility(View.VISIBLE);
            }
            binding.progressBarTop.setVisibility(View.GONE);
        });
        viewModel.makeApiCall();
    }

    private void onSwipeRefresh() {
        binding.swipeToRefreshTop.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.getMoviesListObserver().observe(getViewLifecycleOwner(), new Observer<List<ArticlesModel>>() {
                    @Override
                    public void onChanged(List<ArticlesModel> movieModels) {
                        if (movieModels != null) {
                            movieModelList = movieModels;
                            adapter.setNewsList(movieModels);
                        } else {
                            binding.errorBoldTop.setVisibility(View.VISIBLE);
                            binding.errorMessage1Top.setVisibility(View.VISIBLE);
                            binding.errorMessage2Top.setVisibility(View.VISIBLE);
                        }
                        binding.progressBarTop.setVisibility(View.GONE);
                    }
                });
                viewModel.makeApiCall();
            }
        });
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(TopViewModel.class);
    }
}