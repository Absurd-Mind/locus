package org.inrain.pmap.provider.content;

import java.util.ArrayList;
import java.util.List;

import org.inrain.pmap.Friend;

public class InternetContentProvider implements ContentProvider {

    public List<Friend> getFriendList() {
        return new ArrayList<Friend>();
    }
}
