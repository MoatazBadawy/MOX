package com.moataz.mox.ui.view.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.moataz.mox.databinding.FragmentBusinessBinding;
import com.moataz.mox.ui.adapter.MediumAdapter;
import com.moataz.mox.ui.adapter.ViewPagerAdapter;
import com.moataz.mox.ui.viewmodel.UIViewModel;
import org.jetbrains.annotations.NotNull;

public class UIFragment extends Fragment {

    private MediumAdapter adapter;
    private UIViewModel viewModel;
    private FragmentBusinessBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBusinessBinding.inflate(getLayoutInflater());
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
        binding.recyclerViewBusiness.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewBusiness.setHasFixedSize(true);
        binding.recyclerViewBusiness.setAdapter(adapter);
    }

    private void getList() {
        viewModel.makeApiCallUIArticles().observe(requireActivity(), response -> {
            switch (response.status){
                case ERROR: {
                    binding.progressBarBusiness.setVisibility(View.GONE);
                    break;
                }
                case LOADING: {
                    binding.progressBarBusiness.setVisibility(View.VISIBLE);
                    break;
                }
                case SUCCESS:{
                    binding.progressBarBusiness.setVisibility(View.GONE);
                    adapter.setMediumList(response.data);
                    break;
                }
            }
        });
    }

    private void onSwipeRefresh() {
        binding.swipeToRefreshBusiness.setOnRefreshListener(() -> viewModel.makeApiCallUIArticles().observe(requireActivity(), response -> {
            switch (response.status){
                case ERROR: {
                    binding.swipeToRefreshBusiness.setRefreshing(false);
                    break;
                }
                case SUCCESS:{
                    binding.swipeToRefreshBusiness.setRefreshing(false);
                    adapter.setMediumList(response.data);
                    break;
                }
            }
        }));
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(UIViewModel.class);
    }
}