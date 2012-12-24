package org.inrain.pmap.api.v2;

import com.google.inject.AbstractModule;

public class ServerAPIV2Module extends AbstractModule {

    @Override
    protected void configure() {
        bind(LocationSender.class).to(LocationSenderImpl.class);
        bind(FriendLocationReceiver.class).to(FriendLocationReceiverImpl.class);
        bind(ServerAPIV2.class).to(ServerAPIV2Impl.class);
    }

}
