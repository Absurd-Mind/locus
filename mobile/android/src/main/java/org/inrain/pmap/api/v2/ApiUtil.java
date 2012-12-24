package org.inrain.pmap.api.v2;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

class ApiUtil {
    ApiUtil() {
        
    }
    
    public URI prepareServerUri(String serverFormatString, String serverBase, String action) throws URISyntaxException {
        String url = String.format(Locale.ENGLISH,
                                   serverFormatString,
                                   serverBase,
                                   action);
        URI uri = new URI(url);
        return uri;
    }
}
