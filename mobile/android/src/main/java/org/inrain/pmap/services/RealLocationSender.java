package org.inrain.pmap.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
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
        HttpPost request = new HttpPost();
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

    private boolean prepareRequest(HttpPost request, Location location) {
        URI uri = null;
        try {
            uri = createServerURI();
        } catch (URISyntaxException e) {
            return false;
        }
        
        String host = uri.getHost();
        if (host == null) {
            logger.trace("host is invalid");
            return false;
        }

        logger.trace("resulting URI: {}", uri.toString());
        request.setURI(uri);
        
        String username = preferencesProvider.getUsername();
        
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("username", username));
        nameValuePairs.add(new BasicNameValuePair("latitude", Double.toString(location.getLatitude())));
        nameValuePairs.add(new BasicNameValuePair("longitude", Double.toString(location.getLongitude())));
        nameValuePairs.add(new BasicNameValuePair("accuracy", Float.toString(location.getAccuracy())));
        nameValuePairs.add(new BasicNameValuePair("provider", location.getProvider()));
        
        try {
            request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        } catch (UnsupportedEncodingException e) {
            return false;
        }
        
        return true;
    }

    private URI createServerURI() throws URISyntaxException {
        String serverUrl = preferencesProvider.getServerURL();

        String url = String.format(Locale.ENGLISH,
                                   Constants.SERVER_UPDATE_FORMAT_STRING,
                                   serverUrl);
        URI uri = new URI(url);
        return uri;
    }
}
