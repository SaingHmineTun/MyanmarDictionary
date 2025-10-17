package it.saimao.myanmardictionary.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.saimao.myanmardictionary.adapter.DictAdapter;
import it.saimao.myanmardictionary.dao.DictDao;
import it.saimao.myanmardictionary.dao.FavDao;
import it.saimao.myanmardictionary.database.MyanmarDictionary;
import it.saimao.myanmardictionary.databinding.FragmentFavoriteBinding;
import it.saimao.myanmardictionary.entities.Dict;
import it.saimao.myanmardictionary.entities.Fav;

public class FavoriteFragment extends Fragment {

    private FragmentFavoriteBinding binding;
    private FavDao favDao;
    private DictDao dictDao;
    private DictAdapter dictAdapter;
    private ExecutorService executorService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        initDatabase();
        initRecyclerView();
        return binding.getRoot();
    }

    private void initDatabase() {
        favDao = MyanmarDictionary.getInstance(requireContext()).favDao();
        dictDao = MyanmarDictionary.getInstance(requireContext()).dicDao();
        executorService = Executors.newFixedThreadPool(2);
    }

    private void initRecyclerView() {
        dictAdapter = new DictAdapter(requireContext());
        dictAdapter.setOnFavItemClickListener(dict -> {
            loadFavoriteData();
        });
        binding.rvFavorite.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvFavorite.setAdapter(dictAdapter);
    }

    private void loadFavoriteData() {
        executorService.execute(() -> {
            var favourites = favDao.getAllFavorites();
            if (favourites == null || favourites.isEmpty()) {
                requireActivity().runOnUiThread(() -> {
                    showEmptyView(true);
                });
            } else {
                var favoriteDicts = new ArrayList<Dict>();
                for (Fav fav : favourites) {
                    Dict dict = dictDao.getDictById(fav.getId());
                    favoriteDicts.add(dict);
                }
                requireActivity().runOnUiThread(() -> {
                    showEmptyView(false);
                    dictAdapter.submitList(favoriteDicts);
                });

            }
        });
    }

    private void showEmptyView(boolean yes) {
        if (yes) {
            binding.rvFavorite.setVisibility(View.GONE);
            binding.emptyStateLayout.setVisibility(View.VISIBLE);
        } else {
            binding.rvFavorite.setVisibility(View.VISIBLE);
            binding.emptyStateLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavoriteData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}