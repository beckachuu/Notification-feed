package com.beckachu.androidfeed.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beckachu.androidfeed.databinding.FragmentNotiListBinding;

public class NotiListFragment extends Fragment {

    private RecyclerView recyclerView;
    private FragmentNotiListBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNotiListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView = binding.notiList;
        recyclerView.setLayoutManager(layoutManager);

        NotiListAdapter adapter = new NotiListAdapter(this.requireActivity());
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}