package com.beckachu.androidfeed.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.preference.CheckBoxPreference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.beckachu.androidfeed.R;
import com.beckachu.androidfeed.data.SharedPrefsManager;
import com.beckachu.androidfeed.misc.Const;
import com.beckachu.androidfeed.misc.Util;

import java.util.HashSet;
import java.util.List;

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
        view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white));

        SharedPreferences sharedPref = requireContext().getSharedPreferences(SharedPrefsManager.DEFAULT_NAME, Context.MODE_PRIVATE);

        SwitchPreference recordPref = findPreference(getString(R.string.record_pref_key));
        if (recordPref != null) {
            recordPref.setOnPreferenceChangeListener((preference, isChecked) -> {
                SharedPrefsManager.putBool(sharedPref, SharedPrefsManager.RECORD_CHECKED, (Boolean) isChecked);
                return true;
            });
        }

        HashSet<String> appList = SharedPrefsManager.getStringSet(sharedPref, SharedPrefsManager.APP_LIST);
        PreferenceCategory category = findPreference(getString(R.string.apps_category_key));
        List<ApplicationInfo> apps = this.requireContext().getPackageManager().getInstalledApplications(0);
        for (ApplicationInfo app : apps) {
            CheckBoxPreference checkBoxPref = new CheckBoxPreference(requireContext());
            checkBoxPref.setKey(app.packageName);
            checkBoxPref.setTitle(Util.getAppNameFromPackage(this.requireContext(), app.packageName, false));
            checkBoxPref.setIcon(Util.getAppIconFromPackage(this.requireContext(), app.packageName));

            checkBoxPref.setOnPreferenceChangeListener((preference, isChecked) -> {
                HashSet<String> appSet = SharedPrefsManager.getStringSet(sharedPref, SharedPrefsManager.APP_LIST);
                if ((boolean) isChecked) {
                    if (Const.DEBUG) System.out.println("Checked " + preference.getKey());
                    appSet.add(preference.getKey());
                } else {
                    if (Const.DEBUG) System.out.println("Unchecked " + preference.getKey());
                    appSet.remove(preference.getKey());
                }
                SharedPrefsManager.putStringSet(sharedPref, SharedPrefsManager.APP_LIST, appSet);
                return true;
            });

            if (category != null && (!category.addPreference(checkBoxPref) || checkBoxPref.isChecked())) {
                appList.add(app.packageName);
            }
        }
        SharedPrefsManager.putStringSet(sharedPref, SharedPrefsManager.APP_LIST, appList);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings_preference, rootKey);
    }
}
