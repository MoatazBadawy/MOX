package com.moataz.mox.ui.view.fragment.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.moataz.mox.R;
import com.moataz.mox.data.db.Favorite;
import com.moataz.mox.data.db.SQLiteDatabaseManager;
import com.moataz.mox.databinding.FragmentFavouriteBinding;
import com.moataz.mox.ui.adapter.FavoriteAdapter;
import com.moataz.mox.utils.IOnBackPressed;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FavouriteFragment extends Fragment implements IOnBackPressed {

    private SQLiteDatabaseManager sqliteManager;
    private FragmentFavouriteBinding binding;
    FavoriteAdapter adapter;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavouriteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        initializeSqliteDatabase();
        setupAdapter();
        onSwipeRefresh();
        return view;
    }

    @SuppressLint({"NotifyDataSetChanged", "ClickableViewAccessibility"})
    private void setupAdapter() {
        List<Favorite> favoriteList = sqliteManager.getFavoriteData();
        adapter = new FavoriteAdapter(favoriteList, getContext());
        binding.recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewFavorites.setHasFixedSize(true);
        binding.recyclerViewFavorites.setAdapter(adapter);
        binding.recyclerViewFavorites.setOnTouchListener((view, motionEvent) -> {
            binding.recyclerViewFavorites.onTouchEvent(motionEvent);
            return true;
        });
        adapter.notifyDataSetChanged();
    }

    private void onSwipeRefresh() {
        binding.swipeToRefreshFavourites.setOnRefreshListener(() -> {
            adapter.clearAll();
            (new Handler()).postDelayed(this::setupAdapter, 100);
            binding.swipeToRefreshFavourites.setRefreshing(false);
        });
    }

    private void initializeSqliteDatabase() { sqliteManager = new SQLiteDatabaseManager(getContext()); }

    @Override
    public boolean onBackPressed() {
        HomeFragment mainFragment = new HomeFragment();
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_layout, mainFragment, "findThisFragment")
                .addToBackStack(null)
                .commit();
        return true;
    }
}