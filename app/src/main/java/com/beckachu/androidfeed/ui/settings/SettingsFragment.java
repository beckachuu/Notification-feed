package com.beckachu.androidfeed.ui.settings;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceFragmentCompat;

import com.beckachu.androidfeed.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    public static final String PAGE_ID = "page_id";

    public SettingsFragment() {
        Bundle args = new Bundle();
        args.putString(PAGE_ID, "temp");
        setArguments(args);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set the default white background in the view so as to avoid transparency
//        view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));

    }


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings_preference, rootKey);
    }
}
