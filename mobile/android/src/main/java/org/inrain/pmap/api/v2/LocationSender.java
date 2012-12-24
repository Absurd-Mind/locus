package org.inrain.pmap.api.v2;

import android.location.Location;

public interface LocationSender {

    /**
     * send the location to the server
     * 
     * @param username the username which will be send
     * @param newLocation the location which will be send
     * @return true if the new location was successfully transmitted
     */
    boolean sendLocation(String serverBase, String username, Location newLocation);

}