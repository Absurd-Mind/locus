package org.inrain.pmap.api.v2;

import java.util.List;

import org.inrain.pmap.Friend;

import android.location.Location;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
class ServerAPIV2Impl implements ServerAPIV2 {
    private LocationSender locationSender;
    private FriendLocationReceiver friendLocationReceiver;
    private ApiDataProvider apiDataProvider;
    
    @Inject
    ServerAPIV2Impl(LocationSender locationSender, FriendLocationReceiver friendLocationReceiver, ApiDataProvider apiDataProvider) {
        this.locationSender = locationSender;
        this.friendLocationReceiver = friendLocationReceiver;
        this.apiDataProvider = apiDataProvider;
    }
    
    public boolean sendLocation(Location location) {
        return locationSender.sendLocation(apiDataProvider.getServerBase(), apiDataProvider.getUsername(), location);
    }

    public List<Friend> retrieveCurrentFriendLocations() {
        return friendLocationReceiver.getCurrentFriendLocations(apiDataProvider.getServerBase());
    }
}
