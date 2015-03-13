package com.devclo.ticketoptimizer;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.Preference;
import android.preference.PreferenceFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrefFragment extends PreferenceFragment
                implements SharedPreferences.OnSharedPreferenceChangeListener {


    public PrefFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        SharedPreferences sharedPref = getPreferenceScreen().getSharedPreferences();
        Preference connectionPref = findPreference("pref_ticket1");
        connectionPref.setSummary(sharedPref.getString("pref_ticket1", "4.00"));
        connectionPref = findPreference("pref_ticket2");
        connectionPref.setSummary(sharedPref.getString("pref_ticket2", "1.40"));
    }


    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPref, String key) {
        if (key.equals("pref_ticket1")) {
            Preference connectionPref = findPreference(key);
            connectionPref.setSummary(sharedPref.getString(key, ""));
        } else if (key.equals("pref_ticket2")) {
            Preference connectionPref = findPreference(key);
            connectionPref.setSummary(sharedPref.getString(key, ""));
        }
    }
}
