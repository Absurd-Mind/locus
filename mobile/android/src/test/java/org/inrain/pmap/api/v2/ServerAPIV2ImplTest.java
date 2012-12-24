package org.inrain.pmap.api.v2;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Test;

import android.location.Location;

public class ServerAPIV2ImplTest {
    
    @Test
    public void testSendLocation() {
        final String username = "testName";
        final String serverBase = "serverBase";
        Location location = mock(Location.class);
        
        
        ApiDataProvider apiDataProvider = mock(ApiDataProvider.class);
        when(apiDataProvider.getUsername()).thenReturn(username);
        when(apiDataProvider.getServerBase()).thenReturn(serverBase);
        
        FriendLocationReceiver friendLocationReceiver = mock(FriendLocationReceiver.class);
        
        
        LocationSender locationSender = mock(LocationSender.class);
        when(locationSender.sendLocation(serverBase, username, location)).thenReturn(true);
        
        ServerAPIV2 serverApi = new ServerAPIV2Impl(locationSender, friendLocationReceiver, apiDataProvider);
        
        assertTrue(serverApi.sendLocation(location));
        verify(locationSender).sendLocation(serverBase, username, location);
    }
}
