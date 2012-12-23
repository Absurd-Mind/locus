package org.inrain.pmap.api.v2;

import java.util.List;

import org.inrain.pmap.Friend;

import android.location.Location;

import com.google.inject.Inject;

class ServerAPIV2Impl implements ServerAPIV2 {
    private LocationSender locationSender;
    
    @Inject
    ServerAPIV2Impl(LocationSender locationSender) {
        this.locationSender = locationSender;
    }
    
    public boolean sendLocation(String username, Location location) {
        return locationSender.sendLocation(location);
    }

    public List<Friend> retrieveFriends() {
        // TODO Auto-generated method stub
        return null;
    }

}
