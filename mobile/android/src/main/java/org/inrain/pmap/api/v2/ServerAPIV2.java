package org.inrain.pmap.api.v2;

import java.util.List;

import org.inrain.pmap.Friend;

import android.location.Location;

public interface ServerAPIV2 {
    boolean sendLocation(Location location);
    
    List<Friend> retrieveCurrentFriendLocations();
}
