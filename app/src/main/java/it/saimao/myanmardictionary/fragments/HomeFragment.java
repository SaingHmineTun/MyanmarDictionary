package it.saimao.myanmardictionary.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import it.saimao.myanmardictionary.Utils;
import it.saimao.myanmardictionary.adapter.DictAdapter;
import it.saimao.myanmardictionary.dao.DictDao;
import it.saimao.myanmardictionary.database.MyanmarDictionary;
import it.saimao.myanmardictionary.databinding.FragmentHomeBinding;
import it.saimao.myanmardictionary.entities.Dict;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private DictDao dictDao;
    private DictAdapter dictAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        initDatabase();
        initRecyclerView();
        initSearchFunctionality();
        return binding.getRoot();
    }

    private void initDatabase() {
        dictDao = MyanmarDictionary.getInstance(requireContext()).dicDao();
    }

    private void initRecyclerView() {
        dictAdapter = new DictAdapter(requireContext());
        binding.rvDict.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvDict.setAdapter(dictAdapter);
        showEmptyView(true);
    }

    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> filterTask;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Cancel previous task if exists
            if (filterTask != null && !filterTask.isDone()) {
                filterTask.cancel(false);
            }

            // Schedule new task with 1 second delay
            filterTask = scheduler.schedule(() -> {
                requireActivity().runOnUiThread(() -> {
                    filterData(s.toString());
                });
            }, 1, TimeUnit.SECONDS);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };


    private void initSearchFunctionality() {
        binding.etSearch.addTextChangedListener(textWatcher);
    }

    private void filterData(String query) {
        if (query == null || query.isEmpty()) {
            showEmptyView(true);
            return;
        }
        List<Dict> filteredDicts = dictDao.getDictByWord(query);
        if (filteredDicts == null || filteredDicts.isEmpty()) {
            showEmptyView(true);
        } else {
            showEmptyView(false);
            dictAdapter.submitList(filteredDicts);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.etSearch.setText(Utils.getSavedWord());
        binding.etSearch.setSelection(binding.etSearch.getText().length());
        binding.etSearch.requestFocus();
    }

    private void showEmptyView(boolean yes) {
        if (yes) {
            binding.rvDict.setVisibility(View.GONE);
            binding.emptyStateLayout.setVisibility(View.VISIBLE);
        } else {
            binding.rvDict.setVisibility(View.VISIBLE);
            binding.emptyStateLayout.setVisibility(View.GONE);
        }
    }
}