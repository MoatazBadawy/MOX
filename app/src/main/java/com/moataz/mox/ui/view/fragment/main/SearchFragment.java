package com.moataz.mox.ui.view.fragment.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moataz.mox.R;
import com.moataz.mox.ui.view.fragment.main.HomeFragment;
import com.moataz.mox.utils.IOnBackPressed;

public class SearchFragment extends Fragment implements IOnBackPressed {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
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