package org.inrain.pmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.location.Location;
import android.os.Bundle;
import android.widget.SimpleAdapter;

public class FriendList extends ListActivity {
    private SimpleAdapter sa;
    private List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("onCreate");

        HashMap<String, String> item;
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
        Location myLoc = new Location("mock");
        myLoc.setLongitude(9.687778);
        myLoc.setLatitude(53.465833);
        for (Friend friend : friends) {
            item = new HashMap<String, String>();
            item.put("line1", friend.getName());
            //item.put("line2", friend.getLocation().getLatitude() + " " + friend.getLocation().getLongitude());
            float[] result = new float[1];
            friend.getLocation().getTime();
            Location.distanceBetween(myLoc.getLatitude(), myLoc.getLongitude(), friend.getLocation().getLatitude(), friend.getLocation().getLongitude(), result);
            item.put("line2", result[0] + "m");
            list.add(item);
        }
        sa = new SimpleAdapter(this, list, android.R.layout.two_line_list_item, new String[] { "line1", "line2" },
                new int[] { android.R.id.text1, android.R.id.text2 });
        setListAdapter(sa);
    }
    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("    onResume");
    }
    
    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("  onRestart");
    }
    @Override
    protected void onStart() {
        super.onRestart();
        System.out.println("  onStart");
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("    onPause");
    }
    
    protected void onStop() {
        super.onStop();
        System.out.println("  onStop");
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy");
    }
}

