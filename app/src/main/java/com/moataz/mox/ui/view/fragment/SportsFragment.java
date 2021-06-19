package com.moataz.mox.ui.view.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.moataz.mox.databinding.FragmentSportsBinding;
import com.moataz.mox.ui.adapter.ArticleAdapter;
import com.moataz.mox.ui.adapter.ViewPagerAdapter;
import com.moataz.mox.ui.viewmodel.SportsViewModel;

import org.jetbrains.annotations.NotNull;

public class SportsFragment extends Fragment {

    private ArticleAdapter adapter;
    private SportsViewModel viewModel;
    private FragmentSportsBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSportsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        requireActivity().setTitle("");
        initializeViews();
        initializeViewModel();
        getList();
        return view;
    }

    private void initializeViews() {
        adapter = new ArticleAdapter();
        binding.recyclerViewSports.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewSports.setHasFixedSize(true);
        binding.recyclerViewSports.setAdapter(adapter);
    }

    private void getList() {
        viewModel.makeApiCallSports().observe(requireActivity(), response -> {
            switch (response.status){
                case ERROR: {
                    binding.errorBoldSports.setVisibility(View.VISIBLE);
                    binding.errorMessage1Sports.setVisibility(View.VISIBLE);
                    binding.errorMessage2Sports.setVisibility(View.VISIBLE);
                    binding.progressBarSports.setVisibility(View.GONE);
                    break;
                }
                case LOADING: {
                    binding.progressBarSports.setVisibility(View.VISIBLE);
                    break;
                }
                case SUCCESS:{
                    binding.progressBarSports.setVisibility(View.GONE);
                    adapter.setNewsList(response.data);
                    break;
                }
            }
        });
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(SportsViewModel.class);
    }
}