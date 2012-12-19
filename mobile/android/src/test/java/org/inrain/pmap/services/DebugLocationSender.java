package org.inrain.pmap.services;

import android.location.Location;

public class DebugLocationSender implements LocationSender {

    public boolean sendLocation(Location newLocation) {
        return true;
    }

}
