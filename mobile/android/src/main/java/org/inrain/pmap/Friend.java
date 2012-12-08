package org.inrain.pmap;

import android.location.Location;

public class Friend {
    private Location location;
    private final String name;
    
    public Friend(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }
}
