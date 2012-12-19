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
import org.inrain.pmap.Util;
import org.inrain.pmap.provider.preferences.PreferencesProvider;
import org.slf4j.Logger;

import android.location.Location;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class RealLocationSender implements LocationSender {
    @Inject
    private PreferencesProvider preferencesProvider;

    private final Logger logger = Util.createLogger();

    /* (non-Javadoc)
     * @see org.inrain.pmap.services.LocationSender#sendLocation(android.location.Location)
     */
    public boolean sendLocation(Location newLocation) {
        logger.trace("updateLocation");
        if (sendLocationToServer(newLocation)) {
            logger.trace("sending location to server: success");
            return true;
        }
        return false;
    }

    private boolean sendLocationToServer(Location location) {
        HttpGet request = new HttpGet();
        boolean prepareRequestSucceeded = prepareRequest(request, location);
        if (!prepareRequestSucceeded) {
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

    private boolean prepareRequest(HttpGet request, Location location) {
        URI uri = null;
        try {
            uri = createServerURI(location);
        } catch (URISyntaxException e) {
            return false;
        }

        logger.trace("resulting URI: {}", uri.toString());
        request.setURI(uri);
        return true;
    }

    private URI createServerURI(Location location) throws URISyntaxException {
        String username = preferencesProvider.getUsername();
        String serverUrl = preferencesProvider.getServerURL();

        String url = String.format(Locale.ENGLISH,
                                   Constants.SERVER_UPDATE_FORMAT_STRING,
                                   serverUrl,
                                   username,
                                   location.getLatitude(),
                                   location.getLongitude(),
                                   location.getAccuracy());
        URI uri = new URI(url);
        return uri;
    }
}
