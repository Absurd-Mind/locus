package org.inrain.pmap.provider.location;

import android.location.Location;

import com.google.inject.Singleton;

@Singleton
public class FooLocationProvider implements LocationProvider {
    private Location location;
    
    public void setCurrentLocation(Location location) {
        this.location = location;
    }
    
    public Location getCurrentLocation() {
        return location;
    }
}
