package org.inrain.pmap;

import org.inrain.pmap.provider.content.ContentProvider;
import org.inrain.pmap.provider.content.DebugContentProvider;
import org.inrain.pmap.provider.location.FooLocationProvider;
import org.inrain.pmap.provider.location.LocationProvider;
import org.inrain.pmap.provider.preferences.PreferencesProvider;
import org.inrain.pmap.provider.preferences.RealPreferencesProvider;

import com.google.inject.AbstractModule;

public class ProjectMapModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ContentProvider.class).to(DebugContentProvider.class);
        bind(LocationProvider.class).to(FooLocationProvider.class);
        bind(PreferencesProvider.class).to(RealPreferencesProvider.class);
    }
}
