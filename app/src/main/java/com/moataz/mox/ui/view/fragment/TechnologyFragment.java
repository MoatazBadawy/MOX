package com.moataz.mox.ui.view.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.moataz.mox.databinding.FragmentTechnologyBinding;
import com.moataz.mox.ui.adapter.ArticleAdapter;
import com.moataz.mox.ui.adapter.ViewPagerAdapter;
import com.moataz.mox.ui.viewmodel.TechnologyViewModel;
import org.jetbrains.annotations.NotNull;

public class TechnologyFragment extends Fragment {

    private ArticleAdapter adapter;
    private TechnologyViewModel viewModel;
    private FragmentTechnologyBinding binding;
    ViewPagerAdapter adapterViewPager;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTechnologyBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        requireActivity().setTitle("");
        initializeViews();
        initializeViewModel();
        getTopList();
        onSwipeRefresh();
        return view;
    }

    private void initializeViews() {
        adapter = new ArticleAdapter();
        binding.recyclerViewTechnology.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewTechnology.setHasFixedSize(true);
        binding.recyclerViewTechnology.setAdapter(adapter);
    }

    private void getTopList() {
        viewModel.makeApiCallTechnology().observe(requireActivity(), response -> {
            switch (response.status){
                case ERROR: {
                    binding.errorBoldTechnology.setVisibility(View.VISIBLE);
                    binding.errorMessage1Technology.setVisibility(View.VISIBLE);
                    binding.errorMessage2Technology.setVisibility(View.VISIBLE);
                    binding.progressBarTechnology.setVisibility(View.GONE);
                    break;
                }
                case LOADING: {
                    binding.progressBarTechnology.setVisibility(View.VISIBLE);
                    break;
                }
                case SUCCESS:{
                    binding.progressBarTechnology.setVisibility(View.GONE);
                    adapter.setNewsList(response.data);
                    break;
                }
            }
        });
    }

    private void onSwipeRefresh() {
        binding.swipeToRefreshTechnology.setOnRefreshListener(() -> viewModel.makeApiCallTechnology().observe(requireActivity(), response -> {
            switch (response.status){
                case ERROR: {
                    binding.swipeToRefreshTechnology.setRefreshing(false);
                    break;
                }
                case SUCCESS:{
                    binding.swipeToRefreshTechnology.setRefreshing(false);
                    binding.errorBoldTechnology.setVisibility(View.INVISIBLE);
                    binding.errorMessage1Technology.setVisibility(View.INVISIBLE);
                    binding.errorMessage2Technology.setVisibility(View.INVISIBLE);
                    adapter.setNewsList(response.data);
                    break;
                }
            }
        }));
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(TechnologyViewModel.class);
    }
}