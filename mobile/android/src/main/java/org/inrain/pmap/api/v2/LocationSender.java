package org.inrain.pmap.api.v2;

import android.location.Location;

public interface LocationSender {

    public abstract boolean sendLocation(Location newLocation);

}