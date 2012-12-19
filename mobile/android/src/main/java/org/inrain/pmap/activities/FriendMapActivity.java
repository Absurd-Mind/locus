package org.inrain.pmap.activities;

import java.util.List;

import org.inrain.pmap.Friend;
import org.inrain.pmap.R;
import org.inrain.pmap.provider.content.ContentProvider;
import org.inrain.pmap.provider.location.LocationObserver;
import org.inrain.pmap.provider.location.LocationProvider;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.InjectFragment;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.inject.Inject;

public class FriendMapActivity extends RoboFragmentActivity {

    @InjectFragment(R.id.map)
    SupportMapFragment mapFragment;
    
    @Inject
    ContentProvider contentProvider;
    
    @Inject
    LocationProvider locationProvider;
    
//    public FriendMapActivity( MapFragment mapFragment) {
//        super();
//        this.mapFragment = mapFragment;
//    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.map);
        //mapFragment.get
        //MapFragment foo = (MapFragment) mapFragment;
        //mapFragment = (Fragment) findViewById(R.id.map);
        GoogleMap map = mapFragment.getMap();
        map.setLocationSource(new XXX());
        map.setMyLocationEnabled(true);
        List<Friend> friends = contentProvider.getFriendList();
        for (Friend friend : friends) {
            MarkerOptions mo = new MarkerOptions();
            
            mo.position(new LatLng(friend.getLocation().getLatitude(), friend.getLocation().getLongitude()));
            mo.title(friend.getName());
            
            map.addMarker(mo).showInfoWindow();
        }
    }
    
    class XXX implements LocationSource, LocationObserver {
            OnLocationChangedListener foo;
            boolean activated = false;
            
            public void activate(OnLocationChangedListener arg0) {
                foo = arg0;
                foo.onLocationChanged(locationProvider.getCurrentLocation());
                activated = true;
            }

            public void deactivate() {
                activated = false;
            }

            public void locationUpdated(Location location) {
                if (activated) {
                    foo.onLocationChanged(location);
                }
            }
    }
}
