package org.inrain.pmap.services;

import org.inrain.pmap.Util;
import org.inrain.pmap.api.v2.ServerAPIV2;
import org.inrain.pmap.provider.location.LocationProvider;
import org.inrain.pmap.provider.preferences.PreferencesProvider;
import org.slf4j.Logger;

import roboguice.service.RoboService;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.IBinder;

import com.google.inject.Inject;

public class LocationUpdateService extends RoboService {
    @Inject
    private LocationManager locationManager;

    @Inject
    private LocationProvider locationProvider;

    @Inject
    private ServerAPIV2 serverAPI;
    
    @Inject
    private LocationListenerAndHolder locationListener;

    @Inject
    private PreferencesProvider preferencesProvider;

    private final Logger logger = Util.createLogger();

    private static final long TIMEOUT = 10 * 60 * 1000;

    private Handler handler = new Handler();


    @Override
    public void onCreate() {
        super.onCreate();
        logger.trace("onCreate LocationUpdateService");

        final Runnable runnable = new Runnable() {
            public void run() {
                startService(new Intent(LocationUpdateService.this, LocationUpdateService.class));

                handler.postDelayed(this, TIMEOUT);
            }
         };
         handler.postDelayed(runnable, TIMEOUT);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        logger.trace("starting LocationUpdateService");
        
        boolean activated = preferencesProvider.isActivated();
        if (!activated) {
            logger.trace("not running because it is deactivated");
            return START_STICKY;
        }

        final long sleepTime = registerListenerOnLocationManager(locationListener);
        
        Thread workingThread = new Thread() {
            @Override
            public void run() {
                waitForLocationFix(sleepTime);
                
                locationManager.removeUpdates(locationListener);
                logger.trace("new location: {} {}", locationProvider.getCurrentLocation().getLatitude(), locationProvider.getCurrentLocation().getLongitude());
                sendNewLocation();
            };            
        };
        
        workingThread.start();

        return START_STICKY;
    }

    private void sendNewLocation() {
        serverAPI.sendLocation(locationProvider.getCurrentLocation());
    }

    private long registerListenerOnLocationManager(LocationListener locationListener) {
        logger.trace("enabling listener for network");
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
        
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            logger.trace("enabling listener for gps");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
            
            return 30 * 1000;
        }
        return 5 * 1000;
    }

    private void waitForLocationFix(long sleepTime) {
        logger.trace("sleeping for {} seconds", sleepTime / 1000);
        
        long endTime = System.currentTimeMillis() + sleepTime;
        while (System.currentTimeMillis() < endTime) {
            synchronized (this) {
                try {
                    wait(endTime - System.currentTimeMillis());
                } catch (Exception e) {
                }
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
