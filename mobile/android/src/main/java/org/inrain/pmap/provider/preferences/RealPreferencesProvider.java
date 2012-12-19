package org.inrain.pmap.provider.preferences;

import org.inrain.pmap.Constants;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class RealPreferencesProvider implements PreferencesProvider {
    
    @Inject
    SharedPreferences sharedPreferences;

    public String getUsername() {
        return sharedPreferences.getString(Constants.PREFERENCE_USERNAME_KEY, Constants.PREFERENCE_USERNAME_DEFAULT);
    }

    public String getServerURL() {
        return sharedPreferences.getString(Constants.PREFERENCE_SERVER_URL_KEY, Constants.PREFERENCE_SERVER_URL_DEFAULT);
    }

    public boolean isActivated() {
        return sharedPreferences.getBoolean(Constants.PREFERENCE_ACTIVATED_KEY, Constants.PREFERENCE_ACTIVATED_DEFAULT);
    }

    public void setActivated(boolean activated) {
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constants.PREFERENCE_ACTIVATED_KEY, activated);
        editor.commit();
    }
}
