package org.inrain.pmap.services;

import android.location.Location;

public interface LocationSender {

    public abstract boolean sendLocation(Location newLocation);

}