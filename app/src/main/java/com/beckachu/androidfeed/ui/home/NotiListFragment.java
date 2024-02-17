package com.beckachu.androidfeed.ui.home;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beckachu.androidfeed.databinding.FragmentNotiListBinding;
import com.beckachu.androidfeed.misc.Const;
import com.beckachu.androidfeed.services.broadcast.NotificationReceiver;

public class NotiListFragment extends Fragment {
    private FragmentNotiListBinding binding;
    private final String packagename;

    private ImageButton selectAllButton;
    private ImageButton favoriteButton;
    private ImageButton trashButton;

    public NotiListFragment() {
        this.packagename = "%";
    }

    public NotiListFragment(String packagename) {
        this.packagename = packagename;
//
//        selectAllButton = new ImageButton(this.getContext());
//        selectAllButton.setImageResource(R.drawable.ic_select_all);
//        selectAllButton.setOnClickListener(view -> {
//        });
//
//        favoriteButton = new ImageButton(this.getContext());
//        favoriteButton.setImageResource(R.drawable.ic_star);
//
//        trashButton = new ImageButton(this.getContext());
//        trashButton.setImageResource(R.drawable.ic_trash);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNotiListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        RecyclerView recyclerView = binding.notiList;
        recyclerView.setLayoutManager(layoutManager);

        NotiListAdapter adapter = new NotiListAdapter(this.requireActivity(), packagename);
        recyclerView.setAdapter(adapter);

        // Register the NotificationReceiver with LocalBroadcastManager
        NotificationReceiver receiver = new NotificationReceiver(adapter);
        IntentFilter filter = new IntentFilter(Const.UPDATE_NEWEST);
        LocalBroadcastManager.getInstance(this.requireActivity()).registerReceiver(receiver, filter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}