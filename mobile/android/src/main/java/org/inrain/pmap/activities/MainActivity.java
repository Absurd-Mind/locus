package org.inrain.pmap.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.inrain.pmap.R;
import org.inrain.pmap.Util;
import org.inrain.pmap.services.LocationUpdateService;
import org.slf4j.Logger;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends ListActivity {
    private final Logger logger = Util.createLogger();

    private ListAdapter listAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        logger.trace("onCreate");

        startService(new Intent(this, LocationUpdateService.class));

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("line1", "locate your friends");
        map.put("line2", "show distances of your friends");
        list.add(map);

        listAdapter = new SimpleAdapter(this, list, android.R.layout.two_line_list_item, new String[] { "line1",
                "line2" }, new int[] { android.R.id.text1, android.R.id.text2 });
        setListAdapter(listAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent i = new Intent(this, FriendList.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.options:
            Intent i = new Intent(this, OptionsActivity.class);
            startActivity(i);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
