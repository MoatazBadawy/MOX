package com.moataz.mox.ui.view.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.moataz.mox.databinding.FragmentTechBinding;
import com.moataz.mox.ui.adapter.ThevergeAdapter;
import com.moataz.mox.ui.viewmodel.TechViewModel;
import org.jetbrains.annotations.NotNull;

public class TechFragment extends Fragment {

    private ThevergeAdapter adapter;
    private TechViewModel viewModel;
    private FragmentTechBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTechBinding.inflate(getLayoutInflater());
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
        binding.recyclerViewScience.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewScience.setHasFixedSize(true);
        binding.recyclerViewScience.setAdapter(adapter);
    }

    private void getList() {
        viewModel.makeApiCallTechArticles().observe(requireActivity(), response -> {
            switch (response.status){
                case ERROR: {
                    binding.progressBarScience.setVisibility(View.GONE);
                    break;
                }
                case LOADING: {
                    binding.progressBarScience.setVisibility(View.VISIBLE);
                    break;
                }
                case SUCCESS:{
                    binding.progressBarScience.setVisibility(View.GONE);
                    adapter.setThevergeList(response.data);
                    break;
                }
            }
        });
    }

    private void onSwipeRefresh() {
        binding.swipeToRefreshScience.setOnRefreshListener(() -> viewModel.makeApiCallTechArticles().observe(requireActivity(), response -> {
            switch (response.status){
                case ERROR: {
                    binding.swipeToRefreshScience.setRefreshing(false);
                    break;
                }
                case SUCCESS:{
                    binding.swipeToRefreshScience.setRefreshing(false);
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