package org.inrain.pmap;

import org.inrain.pmap.services.LocationUpdateService;
import org.slf4j.Logger;

import android.app.Application;
import android.content.Intent;

public class ProjectMapApplication extends Application {
    private final Logger logger = Util.createLogger();

    @Override
    public void onCreate() {
        super.onCreate();
        logger.trace("onCreate ProjectMapApplication");

        startService(new Intent(this, LocationUpdateService.class));
    }
}
