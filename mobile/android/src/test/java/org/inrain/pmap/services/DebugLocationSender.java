package org.inrain.pmap.services;

import org.inrain.pmap.api.v2.LocationSender;

import android.location.Location;

public class DebugLocationSender implements LocationSender {

    public boolean sendLocation(Location newLocation) {
        return true;
    }

}
