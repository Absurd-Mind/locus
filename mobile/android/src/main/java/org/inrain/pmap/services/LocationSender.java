package org.inrain.pmap.services;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.inrain.pmap.Constants;
import org.inrain.pmap.provider.preferences.PreferencesProvider;

import android.location.Location;

import com.google.inject.Inject;

public class LocationSender {
    @Inject
    private PreferencesProvider preferencesProvider;
    
    private Location lastSuccessfulSendLocation;
    
    private static final long LOCATION_TIMEOUT = 300; 

    public boolean updateLocation(Location newLocation) {
        if (updateNeeded(newLocation)) {
            String username = preferencesProvider.getUsername();
            String serverUrl = preferencesProvider.getServerURL();
            
            return sendLocationToServer(serverUrl, username, newLocation);
        }
        return false;
    }
    
    private boolean updateNeeded(Location newLocation) {
        if (newLocation.getTime() > lastSuccessfulSendLocation.getTime() + LOCATION_TIMEOUT) {
            return true;
        }
        
        if (newLocation.getAccuracy() < lastSuccessfulSendLocation.getAccuracy()) {
            return true;
        }
        
        return false;
    }
    
    private boolean sendLocationToServer(String serverUrl, String username, Location location) {
         HttpGet request = new HttpGet();
         try {
             String url = String.format(Locale.ENGLISH,
                                        Constants.SERVER_UPDATE_FORMAT_STRING,
                                        serverUrl,
                                        username,
                                        location.getLatitude(),
                                        location.getLongitude(),
                                        location.getAccuracy());
             URI uri = new URI(url);
             request.setURI(uri);
         } catch (URISyntaxException e) {
             return false;
         }

         HttpClient client = new DefaultHttpClient();
         try {
             client.execute(request);
         } catch (ClientProtocolException e) {
             return false;
         } catch (IOException e) {
             return false;
         }
         
         return true;
    }
}
