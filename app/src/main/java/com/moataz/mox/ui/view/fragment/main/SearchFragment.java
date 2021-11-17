package com.moataz.mox.ui.view.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.moataz.mox.R;
import com.moataz.mox.databinding.FragmentSearchBinding;
import com.moataz.mox.ui.adapter.search.TopRecyclerAdapter;
import com.moataz.mox.ui.viewmodel.FirstRecyViewModel;
import com.moataz.mox.utils.IOnBackPressed;

public class SearchFragment extends Fragment implements IOnBackPressed {

    private TopRecyclerAdapter adapter;
    private FirstRecyViewModel viewModel;
    private FragmentSearchBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        initializeAdapter();
        initializeViewModel();
        getFirstRecyList();
        return view;
    }

    private void initializeAdapter() {
        adapter = new TopRecyclerAdapter(getContext());
        binding.topTopicsRecyclerview.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.topTopicsRecyclerview.setHasFixedSize(true);
        binding.topTopicsRecyclerview.setAdapter(adapter);

        binding.developmentRecyclerview.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.developmentRecyclerview.setHasFixedSize(true);
        binding.developmentRecyclerview.setAdapter(adapter);

        /*
        ArrayList<Data> songListDetails2 = new ArrayList<>();
        songListDetails2.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Happy Mode"));
        songListDetails2.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Daily Lift"));
        songListDetails2.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Motivation Mix"));
        songListDetails2.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Mood Booster"));
        songListDetails2.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Happy Mode"));
        songListDetails2.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Daily Lift"));
        songListDetails2.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Motivation Mix"));
        songListDetails2.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Mood Booster"));
        // set up the RecyclerView
        LinearLayoutManager horizontalLayoutManager2 = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.developmentRecyclerview.setLayoutManager(horizontalLayoutManager2);
        FirstRecyclrAdapter adapter2 = new FirstRecyclrAdapter(getContext(), songListDetails2);
        binding.developmentRecyclerview.setAdapter(adapter2);


        ArrayList<Data> songListDetails3 = new ArrayList<>();
        songListDetails3.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Pumped Pop"));
        songListDetails3.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Mega Hit Mix"));
        songListDetails3.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Sing in teh car"));
        songListDetails3.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Lo-Fi Beats"));
        songListDetails3.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Pumped Pop"));
        songListDetails3.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Mega Hit Mix"));
        songListDetails3.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Sing in teh car"));
        songListDetails3.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Lo-Fi Beats"));
        // set up the RecyclerView
        LinearLayoutManager horizontalLayoutManager3 = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.managementRecyclerview.setLayoutManager(horizontalLayoutManager3);
        FirstRecyclrAdapter adapter3 = new FirstRecyclrAdapter(getContext(), songListDetails3);
        binding.managementRecyclerview.setAdapter(adapter3);


        ArrayList<Data> songListDetails4 = new ArrayList<>();
        songListDetails4.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Daily Mix1"));
        songListDetails4.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Top today"));
        songListDetails4.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Release Radar"));
        songListDetails4.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Discover Weekly"));
        songListDetails4.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Daily Mix1"));
        songListDetails4.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Top today"));
        songListDetails4.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Release Radar"));
        songListDetails4.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Discover Weekly"));
        // set up the RecyclerView
        LinearLayoutManager horizontalLayoutManager4 = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.worksLifeRecyclerview.setLayoutManager(horizontalLayoutManager4);
        FirstRecyclrAdapter adapter4 = new FirstRecyclrAdapter(getContext(), songListDetails4);
        binding.worksLifeRecyclerview.setAdapter(adapter4);


        ArrayList<Data> songListDetails5 = new ArrayList<>();
        songListDetails5.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Today's Top Hits"));
        songListDetails5.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Islamic song"));
        songListDetails5.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Cool Songs"));
        songListDetails5.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Hit Rewind"));
        songListDetails5.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Today's Top Hits"));
        songListDetails5.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Islamic song"));
        songListDetails5.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Cool Songs"));
        songListDetails5.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Hit Rewind"));
        // set up the RecyclerView
        LinearLayoutManager horizontalLayoutManager5 = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.graphicDesignRecyclerview.setLayoutManager(horizontalLayoutManager5);
        FirstRecyclrAdapter adapter5 = new FirstRecyclrAdapter(getContext(), songListDetails5);
        binding.graphicDesignRecyclerview.setAdapter(adapter5);

        ArrayList<Data> songListDetails6 = new ArrayList<>();
        songListDetails6.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Today's Top Hits"));
        songListDetails6.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Islamic song"));
        songListDetails6.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Cool Songs"));
        songListDetails6.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Hit Rewind"));
        songListDetails6.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Today's Top Hits"));
        songListDetails6.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Islamic song"));
        songListDetails6.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Cool Songs"));
        songListDetails6.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Hit Rewind"));
        // set up the RecyclerView
        LinearLayoutManager horizontalLayoutManager6 = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.cultureRecyclerview.setLayoutManager(horizontalLayoutManager6);
        FirstRecyclrAdapter adapter6 = new FirstRecyclrAdapter(getContext(), songListDetails6);
        binding.cultureRecyclerview.setAdapter(adapter6);

        ArrayList<Data> songListDetails7 = new ArrayList<>();
        songListDetails7.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Today's Top Hits"));
        songListDetails7.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Islamic song"));
        songListDetails7.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Cool Songs"));
        songListDetails7.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Hit Rewind"));
        songListDetails7.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Today's Top Hits"));
        songListDetails7.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Islamic song"));
        songListDetails7.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Cool Songs"));
        songListDetails7.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Hit Rewind"));
        // set up the RecyclerView
        LinearLayoutManager horizontalLayoutManager7 = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.industryRecyclerview.setLayoutManager(horizontalLayoutManager7);
        FirstRecyclrAdapter adapter7 = new FirstRecyclrAdapter(getContext(), songListDetails7);
        binding.industryRecyclerview.setAdapter(adapter7);

        ArrayList<Data> songListDetails8 = new ArrayList<>();
        songListDetails8.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Today's Top Hits"));
        songListDetails8.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Islamic song"));
        songListDetails8.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Cool Songs"));
        songListDetails8.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Hit Rewind"));
        songListDetails8.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Today's Top Hits"));
        songListDetails8.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Islamic song"));
        songListDetails8.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Cool Songs"));
        songListDetails8.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Hit Rewind"));
        // set up the RecyclerView
        LinearLayoutManager horizontalLayoutManager8 = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.scienceRecyclerview.setLayoutManager(horizontalLayoutManager8);
        FirstRecyclrAdapter adapter8 = new FirstRecyclrAdapter(getContext(), songListDetails8);
        binding.scienceRecyclerview.setAdapter(adapter8);

        ArrayList<Data> songListDetails9 = new ArrayList<>();
        songListDetails9.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Today's Top Hits"));
        songListDetails9.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Islamic song"));
        songListDetails9.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Cool Songs"));
        songListDetails9.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Hit Rewind"));
        songListDetails9.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Today's Top Hits"));
        songListDetails9.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Islamic song"));
        songListDetails9.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Cool Songs"));
        songListDetails9.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Hit Rewind"));
        // set up the RecyclerView
        LinearLayoutManager horizontalLayoutManager9 = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.technologyRecyclerview.setLayoutManager(horizontalLayoutManager9);
        FirstRecyclrAdapter adapter9 = new FirstRecyclrAdapter(getContext(), songListDetails9);
        binding.technologyRecyclerview.setAdapter(adapter9);

        ArrayList<Data> songListDetails10 = new ArrayList<>();
        songListDetails10.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Today's Top Hits"));
        songListDetails10.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Islamic song"));
        songListDetails10.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Cool Songs"));
        songListDetails10.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Hit Rewind"));
        songListDetails10.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Today's Top Hits"));
        songListDetails10.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Islamic song"));
        songListDetails10.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Cool Songs"));
        songListDetails10.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Hit Rewind"));
        // set up the RecyclerView
        LinearLayoutManager horizontalLayoutManager10 = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.proLanguagesRecyclerview.setLayoutManager(horizontalLayoutManager10);
        FirstRecyclrAdapter adapter10 = new FirstRecyclrAdapter(getContext(), songListDetails10);
        binding.proLanguagesRecyclerview.setAdapter(adapter10);
         */
    }

    private void getFirstRecyList() {
        viewModel.getTopMutableLiveData().observe(requireActivity(), response -> {
                adapter.setFirstRecyList(response.data);
        });

        viewModel.getDevMutableLiveData().observe(requireActivity(), response -> {
            adapter.setFirstRecyList(response.data);
        });
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(FirstRecyViewModel.class);
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