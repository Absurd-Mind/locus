package org.inrain.pmap.api.v2;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.inrain.pmap.Util;
import org.slf4j.Logger;

import android.location.Location;

import com.google.inject.Inject;

class LocationSenderImpl implements LocationSender {
    private final Logger logger = Util.createLogger();
    
    private HttpHelper httpHelper;
    
    private ApiUtil apiUtil;
    
    @Inject
    LocationSenderImpl(HttpHelper httpHelper, ApiUtil apiUtil) {
        this.httpHelper = httpHelper;
        this.apiUtil = apiUtil;
    }

    /* (non-Javadoc)
     * @see org.inrain.pmap.services.LocationSender#sendLocation(android.location.Location)
     */
    public boolean sendLocation(String serverBase, String username, Location newLocation) {
        logger.trace("updateLocation");
        if (sendLocationToServer(serverBase, username, newLocation)) {
            logger.trace("sending location to server: success");
            return true;
        }
        return false;
    }

    private boolean sendLocationToServer(String serverBase, String username, Location location) {
        URI uri = null;
        try {
            uri = apiUtil.prepareServerUri(APIConstants.serverFormatString, serverBase, APIConstants.ACTION_UPDATE);
        } catch (URISyntaxException e) {
            return false;
        }

        Map<String, String> postValues = getPostValues(username, location);

        return httpHelper.sendPost(uri, postValues);
    }

    private Map<String, String> getPostValues(String username, Location location) {
        Map<String, String> postValues = new HashMap<String, String>();
        postValues.put("username", username);
        postValues.put("latitude", Double.toString(location.getLatitude()));
        postValues.put("longitude", Double.toString(location.getLongitude()));
        postValues.put("accuracy", Float.toString(location.getAccuracy()));
        postValues.put("provider", location.getProvider());
        return postValues;
    }
}
