package org.inrain.pmap.api.v2;

import java.util.List;

import org.inrain.pmap.Friend;

interface FriendLocationReceiver {
    
    public List<Friend> getCurrentFriendLocations(String serverBase);
}
