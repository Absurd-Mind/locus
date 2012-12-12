package org.inrain.pmap.provider.content;

import java.util.ArrayList;
import java.util.List;

import org.inrain.pmap.Friend;

import android.location.Location;

public class DebugContentProvider implements ContentProvider {
    
    public List<Friend> getFriendList() {
        List<Friend> friends = new ArrayList<Friend>();
        
        friends.add(new Friend("Fred"));
        Location loc = new Location("mock");
        loc.setLongitude(9.687778);
        loc.setLatitude(53.475833);
        friends.get(0).setLocation(loc);
        
        friends.add(new Friend("Alfons"));
        Location loc2 = new Location("mock");
        loc2.setLongitude(9.685778);
        loc2.setLatitude(53.465833);
        friends.get(1).setLocation(loc2);
        
        friends.add(new Friend("Rewe"));
        Location loc3 = new Location("mock");
        loc3.setLongitude(11.001456);
        loc3.setLatitude(49.575736);
        friends.get(2).setLocation(loc3);
        
        return friends;
    }
}
