package org.inrain.pmap.provider.location;

import android.location.Location;


public interface LocationProvider {
    public Location getCurrentLocation();

    public void setCurrentLocation(Location location);

    public void registerLocationObserver(LocationObserver locationObserver);
    public void unregisterLocationObserver(LocationObserver locationObserver);
}
