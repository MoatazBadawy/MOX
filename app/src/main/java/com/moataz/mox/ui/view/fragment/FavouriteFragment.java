package com.moataz.mox.ui.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.moataz.mox.R;
import com.moataz.mox.data.model.article.Item;
import com.moataz.mox.databinding.FragmentFavouriteBinding;
import com.moataz.mox.ui.adapter.FavoriteAdapter;
import com.moataz.mox.utils.IOnBackPressed;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FavouriteFragment extends Fragment implements IOnBackPressed {

    private FragmentFavouriteBinding binding;
    ArrayList<Item> arrayList = new ArrayList<>();;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavouriteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        requireActivity().setTitle("");
        initializeViews();
        return view;
    }

    private void initializeViews() {
        FavoriteAdapter adapter = new FavoriteAdapter();
        binding.recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewFavorites.setHasFixedSize(true);
        binding.recyclerViewFavorites.setAdapter(adapter);
        adapter.setFavoriteList(arrayList,getContext());
    }

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