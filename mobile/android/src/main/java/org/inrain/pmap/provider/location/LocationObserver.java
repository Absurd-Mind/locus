package org.inrain.pmap.provider.location;

import android.location.Location;

public interface LocationObserver {
    public void locationUpdated(Location location);
}
