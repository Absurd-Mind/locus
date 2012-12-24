package org.inrain.pmap.api.v2;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class HttpHelper {
    boolean sendPost(URI uri, Map<String, String> postValues) {
        HttpPost post = new HttpPost(uri);
        
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        for (String key : postValues.keySet()) {
            nameValuePairs.add(new BasicNameValuePair(key, postValues.get(key)));
        }
        
        try {
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        } catch (UnsupportedEncodingException e) {
            return false;
        }
        
        HttpClient client = new DefaultHttpClient();
        try {
            client.execute(post);
        } catch (ClientProtocolException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
        
        return true;
    }
}
