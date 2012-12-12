package org.inrain.pmap.provider.location;

import java.util.HashSet;
import java.util.Set;

import android.location.Location;

import com.google.inject.Singleton;

@Singleton
public class FooLocationProvider implements LocationProvider {
    private Set<LocationObserver> locationObservers = new HashSet<LocationObserver>();

    private Location location = new Location("dummy");

    public void setCurrentLocation(Location location) {
        this.location = location;

        callLocationObservers(location);
    }

    private void callLocationObservers(Location location) {
        for (LocationObserver locationObserver : locationObservers) {
            locationObserver.locationUpdated(location);
        }
    }
    
    public Location getCurrentLocation() {
        return location;
    }

    public void registerLocationObserver(LocationObserver locationObserver) {
        locationObservers.add(locationObserver);
    }

    public void unregisterLocationObserver(LocationObserver locationObserver) {
        locationObservers.remove(locationObserver);
    }
}
