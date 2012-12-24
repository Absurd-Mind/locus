package org.inrain.pmap;

import org.inrain.pmap.api.v2.ApiDataProvider;
import org.inrain.pmap.provider.preferences.PreferencesProvider;

import com.google.inject.Inject;

class ServerAPIConnector implements ApiDataProvider {

    private final PreferencesProvider preferencesProvider;
    
    @Inject
    ServerAPIConnector(PreferencesProvider preferencesProvider) {
        this.preferencesProvider = preferencesProvider;
    }
    
    public String getUsername() {
        return preferencesProvider.getUsername();
    }

    public String getServerBase() {
        return preferencesProvider.getServerURL();
    }

}
