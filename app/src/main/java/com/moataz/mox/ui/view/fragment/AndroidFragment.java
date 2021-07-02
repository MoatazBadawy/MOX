package com.moataz.mox.ui.view.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.moataz.mox.databinding.FragmentAndroidBinding;
import com.moataz.mox.ui.adapter.MediumAdapter;
import com.moataz.mox.ui.viewmodel.AndroidViewModel;
import org.jetbrains.annotations.NotNull;

public class AndroidFragment extends Fragment {

    private MediumAdapter adapter;
    private AndroidViewModel viewModel;
    private FragmentAndroidBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAndroidBinding.inflate(getLayoutInflater());
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
        binding.recyclerViewTechnology.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewTechnology.setHasFixedSize(true);
        binding.recyclerViewTechnology.setAdapter(adapter);
    }

    private void getTopList() {
        viewModel.makeApiCallAndroidArticles().observe(requireActivity(), response -> {
            switch (response.status){
                case ERROR: {
                    binding.progressBarTechnology.setVisibility(View.GONE);
                    break;
                }
                case LOADING: {
                    binding.progressBarTechnology.setVisibility(View.VISIBLE);
                    break;
                }
                case SUCCESS:{
                    binding.progressBarTechnology.setVisibility(View.GONE);
                    adapter.setMediumList(response.data);
                    break;
                }
            }
        });
    }

    private void onSwipeRefresh() {
        binding.swipeToRefreshTechnology.setOnRefreshListener(() -> viewModel.makeApiCallAndroidArticles().observe(requireActivity(), response -> {
            switch (response.status){
                case ERROR: {
                    binding.swipeToRefreshTechnology.setRefreshing(false);
                    break;
                }
                case SUCCESS:{
                    binding.swipeToRefreshTechnology.setRefreshing(false);
                    adapter.setMediumList(response.data);
                    break;
                }
            }
        }));
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(AndroidViewModel.class);
    }
}