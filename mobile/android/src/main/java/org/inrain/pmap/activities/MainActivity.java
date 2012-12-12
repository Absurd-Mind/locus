package org.inrain.pmap.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.inrain.pmap.ProjectMapApplication;
import org.inrain.pmap.R;
import org.inrain.pmap.Util;
import org.inrain.pmap.provider.location.LocationObserver;
import org.inrain.pmap.provider.location.LocationProvider;
import org.inrain.pmap.services.LocationUpdateService;
import org.slf4j.Logger;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.inject.Inject;

public class MainActivity extends RoboActivity implements LocationObserver {
    private final Logger logger = Util.createLogger();

    private ListAdapter listAdapter;

    @InjectView(R.id.mainMenu)
    private ListView listView;

    @InjectView(R.id.locationProvider)
    private TextView locationProvide;

    @InjectView(R.id.locationLatitude)
    private TextView locationLatitude;

    @InjectView(R.id.locationLongitude)
    private TextView locationLongitude;

    @InjectView(R.id.locationAccuracy)
    private TextView locationAccuracy;

    @InjectView(R.id.locationTime)
    private TextView locationTime;

    @Inject
    private LocationProvider locationProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        logger.trace("onCreate");

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("line1", "locate your friends");
        map.put("line2", "show distances of your friends");
        list.add(map);

        listAdapter = new SimpleAdapter(this, list, android.R.layout.two_line_list_item, new String[] { "line1",
                "line2" }, new int[] { android.R.id.text1, android.R.id.text2 });
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                logger.trace("onListItemClick");

                Intent i = new Intent(MainActivity.this, FriendList.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Location location = locationProvider.getCurrentLocation();
        displayLocation(location);

        locationProvider.registerLocationObserver(this);
    }

    private void displayLocation(Location location) {
        locationProvide.setText(location.getProvider());
        locationLatitude.setText("" + location.getLatitude());
        locationLongitude.setText("" + location.getLongitude());
        locationAccuracy.setText("" + location.getAccuracy());
        locationTime.setText("" + location.getTime());
    }

    @Override
    protected void onPause() {
        super.onPause();

        locationProvider.unregisterLocationObserver(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        logger.trace("onCreateOptionsMenu");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        logger.trace("onOptionsItemSelected");
        switch (item.getItemId()) {
        case R.id.options:
            Intent i = new Intent(this, OptionsActivity.class);
            startActivity(i);
            return true;
        case R.id.forceLocationUpdate:
            startService(new Intent(MainActivity.this, LocationUpdateService.class));
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    public void locationUpdated(Location location) {
        displayLocation(location);
    }
}
