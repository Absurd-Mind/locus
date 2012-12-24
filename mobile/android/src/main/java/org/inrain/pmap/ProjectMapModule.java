package org.inrain.pmap;

import org.inrain.pmap.api.v2.ApiDataProvider;
import org.inrain.pmap.api.v2.ServerAPIV2Module;
import org.inrain.pmap.provider.content.ContentProvider;
import org.inrain.pmap.provider.content.InternetContentProvider;
import org.inrain.pmap.provider.location.FooLocationProvider;
import org.inrain.pmap.provider.location.LocationProvider;
import org.inrain.pmap.provider.preferences.PreferencesProvider;
import org.inrain.pmap.provider.preferences.RealPreferencesProvider;

import com.google.inject.AbstractModule;

public class ProjectMapModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ContentProvider.class).to(InternetContentProvider.class);
        bind(LocationProvider.class).to(FooLocationProvider.class);
        bind(PreferencesProvider.class).to(RealPreferencesProvider.class);
        install(new ServerAPIV2Module());
        bind(ApiDataProvider.class).to(ServerAPIConnector.class);
    }
}
