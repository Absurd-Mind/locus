package org.inrain.pmap.services;

import org.inrain.pmap.Util;
import org.inrain.pmap.provider.location.LocationProvider;
import org.slf4j.Logger;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.google.inject.Inject;

public class LocationListenerAndHolder implements LocationListener {
    private final Logger logger = Util.createLogger();
    
    @Inject
    private LocationProvider locationProvider;
    
    private static final long LOCATION_TIMEOUT = 30 * 1000;
    
    public void onLocationChanged(Location newLocation) {
        logger.trace("new location received, provider: {} accuracy: {}", newLocation.getProvider(), newLocation.getAccuracy());
        Location currentLocation = locationProvider.getCurrentLocation();
        if (newLocationIsBetterThanCurrent(newLocation, currentLocation)) {
            locationProvider.setCurrentLocation(newLocation);
        }
    }
    
    private boolean newLocationIsBetterThanCurrent(Location newLocation, Location currentLocation) {
        if (currentLocation == null) {
            logger.trace("no previous location available");
            return true;
        }

        if (newLocation.getTime() > currentLocation.getTime() + LOCATION_TIMEOUT) {
            logger.trace("last location is not up to date anymore");
            return true;
        }

        if (newLocation.getAccuracy() < currentLocation.getAccuracy() * 0.80) {
            logger.trace("new location has higher accuracy");
            return true;
        }
        
        if (newLocation.distanceTo(currentLocation) > newLocation.getAccuracy() + currentLocation.getAccuracy()) {
            logger.trace("mobile moved a lot");
            return true;
        }

        logger.trace("location update not needed");
        return false;
    }

    public void onProviderDisabled(String provider) {
        logger.trace("provider disabled: {}", provider);
    }

    public void onProviderEnabled(String provider) {
        logger.trace("provider enabled: {}", provider);
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        logger.trace("status changed");
    }
}
