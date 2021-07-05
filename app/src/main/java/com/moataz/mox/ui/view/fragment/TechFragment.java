package com.moataz.mox.ui.view.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.moataz.mox.databinding.FragmentArticlesBinding;
import com.moataz.mox.ui.adapter.ThevergeAdapter;
import com.moataz.mox.ui.viewmodel.TechViewModel;
import org.jetbrains.annotations.NotNull;

public class TechFragment extends Fragment {

    private ThevergeAdapter adapter;
    private TechViewModel viewModel;
    private FragmentArticlesBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentArticlesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        requireActivity().setTitle("");
        initializeViews();
        initializeViewModel();
        getList();
        onSwipeRefresh();
        return view;
    }

    private void initializeViews() {
        adapter = new ThevergeAdapter();
        binding.recyclerViewArticles.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewArticles.setHasFixedSize(true);
        binding.recyclerViewArticles.setAdapter(adapter);
    }

    private void getList() {
        viewModel.makeApiCallTechArticles().observe(requireActivity(), response -> {
            switch (response.status){
                case ERROR: {
                    binding.progressBarArticles.setVisibility(View.GONE);
                    break;
                }
                case LOADING: {
                    binding.progressBarArticles.setVisibility(View.VISIBLE);
                    break;
                }
                case SUCCESS:{
                    binding.progressBarArticles.setVisibility(View.GONE);
                    adapter.setThevergeList(response.data);
                    break;
                }
            }
        });
    }

    private void onSwipeRefresh() {
        binding.swipeToRefreshArticles.setOnRefreshListener(() -> viewModel.makeApiCallTechArticles().observe(requireActivity(), response -> {
            switch (response.status){
                case ERROR: {
                    binding.swipeToRefreshArticles.setRefreshing(false);
                    break;
                }
                case SUCCESS:{
                    binding.swipeToRefreshArticles.setRefreshing(false);
                    adapter.setThevergeList(response.data);
                    break;
                }
            }
        }));
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(TechViewModel.class);
    }
}