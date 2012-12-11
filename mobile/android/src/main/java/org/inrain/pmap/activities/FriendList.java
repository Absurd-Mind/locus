package org.inrain.pmap.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.inrain.pmap.Friend;
import org.inrain.pmap.provider.content.ContentProvider;
import org.inrain.pmap.provider.location.LocationProvider;

import roboguice.activity.RoboListActivity;
import android.location.Location;
import android.os.Bundle;
import android.widget.SimpleAdapter;

import com.google.inject.Inject;

public class FriendList extends RoboListActivity {
    private SimpleAdapter sa;
    private List<Map<String, String>> list;
    
    @Inject
    private ContentProvider contentProvider;
    
    @Inject
    private LocationProvider locationProvider;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("onCreate");
        
        List<Friend> friendList = contentProvider.getFriendList();
        Location myLocation = locationProvider.getCurrentLocation();
        list = createFriendsList(friendList, myLocation);
        
        sa = new SimpleAdapter(this, list, android.R.layout.two_line_list_item, new String[] { "line1", "line2" },
                new int[] { android.R.id.text1, android.R.id.text2 });
        setListAdapter(sa);
    }
    
    private List<Map<String, String>> createFriendsList(List<Friend> friendList, Location myLocation) {
        final List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> item;
        
        for (Friend friend : friendList) {
            item = new HashMap<String, String>();
            String name = friend.getName();
            item.put("line1", name);
            
            String distanceFromFriend = getDistanceFromFriend(friend, myLocation);
            item.put("line2", distanceFromFriend);
            
            list.add(item);
        }
        
        return list;
    }
    
    private String getDistanceFromFriend(Friend friend, Location myLocation) {
        if (friend.getLocation() == null) {
            return "unknown location";
        }
        
        float[] result = new float[1];
        Location.distanceBetween(myLocation.getLatitude(),
                myLocation.getLongitude(),
                friend.getLocation().getLatitude(),
                friend.getLocation().getLongitude(),
                result);
        return result[0] + "m";
    }

}

