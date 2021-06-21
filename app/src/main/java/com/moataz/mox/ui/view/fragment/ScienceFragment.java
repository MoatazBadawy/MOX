package com.moataz.mox.ui.view.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.moataz.mox.databinding.FragmentScienceBinding;
import com.moataz.mox.ui.adapter.ArticleAdapter;
import com.moataz.mox.ui.adapter.ViewPagerAdapter;
import com.moataz.mox.ui.viewmodel.ScienceViewModel;
import org.jetbrains.annotations.NotNull;

public class ScienceFragment extends Fragment {

    private ArticleAdapter adapter;
    private ScienceViewModel viewModel;
    private FragmentScienceBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentScienceBinding.inflate(getLayoutInflater());
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
        binding.recyclerViewScience.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewScience.setHasFixedSize(true);
        binding.recyclerViewScience.setAdapter(adapter);
    }

    private void getList() {
        viewModel.makeApiCallScience().observe(requireActivity(), response -> {
            switch (response.status){
                case ERROR: {
                    binding.errorBoldScience.setVisibility(View.VISIBLE);
                    binding.errorMessage1Science.setVisibility(View.VISIBLE);
                    binding.errorMessage2Science.setVisibility(View.VISIBLE);
                    binding.progressBarScience.setVisibility(View.GONE);
                    break;
                }
                case LOADING: {
                    binding.progressBarScience.setVisibility(View.VISIBLE);
                    break;
                }
                case SUCCESS:{
                    binding.progressBarScience.setVisibility(View.GONE);
                    adapter.setNewsList(response.data);
                    break;
                }
            }
        });
    }

    private void onSwipeRefresh() {
        binding.swipeToRefreshScience.setOnRefreshListener(() -> viewModel.makeApiCallScience().observe(requireActivity(), response -> {
            switch (response.status){
                case ERROR: {
                    binding.swipeToRefreshScience.setRefreshing(false);
                    break;
                }
                case SUCCESS:{
                    binding.swipeToRefreshScience.setRefreshing(false);
                    binding.errorBoldScience.setVisibility(View.INVISIBLE);
                    binding.errorMessage1Science.setVisibility(View.INVISIBLE);
                    binding.errorMessage2Science.setVisibility(View.INVISIBLE);
                    adapter.setNewsList(response.data);
                    break;
                }
            }
        }));
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(ScienceViewModel.class);
    }
}