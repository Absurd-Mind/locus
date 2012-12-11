package org.inrain.pmap.services;

import org.inrain.pmap.R;
import org.inrain.pmap.activities.MainActivity;
import org.inrain.pmap.provider.location.LocationProvider;

import roboguice.service.RoboService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import com.google.inject.Inject;

public class LocationUpdateService extends RoboService {
    @Inject
    LocationManager locationManager;
    
    @Inject
    LocationProvider locationProvider;
    
  public LocationUpdateService() {
  }

  
  @Override
public void onCreate() {
    super.onCreate();
    System.out.println("onCreate reallocationProvider");
}
  
//  private String serverUrl;
//  private String user;
//  LocationManager locationManager;
//
  /**
   * The IntentService calls this method from the default worker thread with
   * the intent that started the service. When this method returns, IntentService
   * stops the service, as appropriate.
   */
  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
      System.out.println("starting service");
      Intent notificationIntent = new Intent(this, MainActivity.class);
      PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
      
      Notification notification = new Notification(R.drawable.ic_menu_mapmode,
          getText(R.string.ongoingNotification), System.currentTimeMillis());
      notification.setLatestEventInfo(
          this,
          getText(R.string.ongoingNotification),
          "foo",
          pendingIntent
      );
      
      int ONGOING_NOTIFICATION = 1;
      startForeground(1, notification);
      
      // LOCATION
      // Acquire a reference to the system Location Manager
      String locationProvider = LocationManager.GPS_PROVIDER;
      //String locationProvider = LocationManager.NETWORK_PROVIDER;
    
      //debug("listening for locationsx");
      Location location = locationManager.getLastKnownLocation(locationProvider);
      if (location != null) {
          update(location);
      }

    
      // Register the listener with the Location Manager to receive location updates
      locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
  
      // Normally we would do some work here, like download a file.
      // For our sample, we just sleep for 5 seconds.
      /*long endTime = System.currentTimeMillis() + 5*1000;
      while (System.currentTimeMillis() < endTime) {
          synchronized (this) {
              try {
                  wait(endTime - System.currentTimeMillis());
              } catch (Exception e) {}
      }*/
      
      // If we get killed, after returning from here, restart
      return START_STICKY; // we should not  be killed (foregroudn!)
  }
//  
  @Override
  public void onDestroy() {
      locationManager.removeUpdates(locationListener);
      
      // The service is no longer used and is being destroyed
      stopForeground(true);
  }
//
//  private int counter = 0;
//
  private void update(Location location) {
      locationProvider.setCurrentLocation(location);
  }
//      HttpClient client = new DefaultHttpClient();
//      HttpGet request = new HttpGet();
//      try {
//          String url = String.format("%s?user=%s&lat=%f&long=%f&accuracy=%f", serverUrl, user, location.getLatitude(), location.getLongitude(), location.getAccuracy());
//          URI uri = new URI(url);
//          counter++;
//          ProjectMapActivity.debug(this, counter + " " + uri.toString());
//        request.setURI(uri);
//      } catch (URISyntaxException e) {
//          ProjectMapActivity.debug(this, "fuck application bug!!" + e.toString());
//        return;
//      }
//      try {
//          client.execute(request);
//    } catch (ClientProtocolException e) {
//        ProjectMapActivity.debug(this, "server error: " + e.toString());
//    } catch (IOException e) {
//        ProjectMapActivity.debug(this, "server error: " + e.toString());
//    }
//  }
//
//
  // Define a listener that responds to location updates
  private LocationListener locationListener = new LocationListener() {
      public void onLocationChanged(Location location) {
          // Called when a new location is found by the network location provider.
          location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
          update(location);
      }
  
      public void onStatusChanged(String provider, int status, Bundle extras) {}
      public void onProviderEnabled(String provider) {}
      public void onProviderDisabled(String provider) {}
   };

@Override
public IBinder onBind(Intent intent) {
    // TODO Auto-generated method stub
    return null;
}

}
