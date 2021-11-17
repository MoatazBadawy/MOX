package com.moataz.mox.ui.view.fragment.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.moataz.mox.R;
import com.moataz.mox.data.db.Favorite;
import com.moataz.mox.data.db.SQLiteDatabaseManager;
import com.moataz.mox.databinding.FragmentFavouriteBinding;
import com.moataz.mox.ui.adapter.main.FavoriteAdapter;
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
        setupRecyclerView();
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

    private void setupRecyclerView() {
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int swipedArticlePosition = viewHolder.getAdapterPosition();
                adapter.deleteItem(viewHolder, swipedArticlePosition);
                Snackbar.make(requireView(),"The article has been removed",Snackbar.LENGTH_LONG).show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewFavorites);
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