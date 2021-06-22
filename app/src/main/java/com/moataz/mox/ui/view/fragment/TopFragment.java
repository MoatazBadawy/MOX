package com.moataz.mox.ui.view.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.moataz.mox.R;
import com.moataz.mox.databinding.FragmentTopBinding;
import com.moataz.mox.ui.adapter.ArticleAdapter;
import com.moataz.mox.ui.viewmodel.TopViewModel;
import com.moataz.mox.utils.CheckNetwork;
import com.moataz.mox.utils.IOnBackPressed;

import org.jetbrains.annotations.NotNull;

public class TopFragment extends Fragment implements IOnBackPressed  {

    private ArticleAdapter adapter;
    private TopViewModel viewModel;
    private FragmentTopBinding binding;
    BottomSheetDialog bottomSheetDialog;

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
        adapter = new ArticleAdapter();
        binding.recyclerViewTop.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewTop.setHasFixedSize(true);
        binding.recyclerViewTop.setAdapter(adapter);
    }

    private void getList() {
        viewModel.makeApiCallTop().observe(requireActivity(), response -> {
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
                    adapter.setNewsList(response.data);
                    break;
                }
            }
        });
    }

    private void onSwipeRefresh() {
        binding.swipeToRefreshTop.setOnRefreshListener(() -> viewModel.makeApiCallTop().observe(requireActivity(), response -> {
            switch (response.status){
                case ERROR: {
                    binding.swipeToRefreshTop.setRefreshing(false);
                    break;
                }
                case SUCCESS:{
                    binding.swipeToRefreshTop.setRefreshing(false);
                    adapter.setNewsList(response.data);
                    break;
                }
            }
        }));
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(TopViewModel.class);
    }

    @Override
    public boolean onBackPressed() {
        requireActivity().moveTaskToBack(true); //exit the app when press back
        requireActivity().finish();
        return true;
    }
}