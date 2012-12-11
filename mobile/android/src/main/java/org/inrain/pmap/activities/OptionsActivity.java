package org.inrain.pmap.activities;

import org.inrain.pmap.Constants;
import org.inrain.pmap.R;

import roboguice.activity.RoboPreferenceActivity;
import roboguice.inject.InjectPreference;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;

public class OptionsActivity extends RoboPreferenceActivity implements OnSharedPreferenceChangeListener {
    @InjectPreference(Constants.PREFERENCE_USERNAME_KEY)
    EditTextPreference username;
    
    @InjectPreference(Constants.PREFERENCE_SERVER_URL_KEY)
    EditTextPreference serverUrl;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        
        SharedPreferences sp = getPreferenceScreen().getSharedPreferences();

        username.setSummary(sp.getString(Constants.PREFERENCE_USERNAME_KEY, "anonymous"));
        
        serverUrl.setSummary(sp.getString(Constants.PREFERENCE_SERVER_URL_KEY, ""));
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener( this );
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener( this );
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference pref = findPreference(key);
        pref.setSummary(sharedPreferences.getString(key, ""));
    }
}
