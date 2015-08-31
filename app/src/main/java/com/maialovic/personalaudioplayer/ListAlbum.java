package com.maialovic.personalaudioplayer;

import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListAlbum extends ListActivity {

   private ArrayList<String> albumList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().hide();

        albumList = Player.getAlbums();

        if (albumList != null) {
            CustomAdapterAlbum adapter = new CustomAdapterAlbum(this, albumList);
            setListAdapter(adapter);
        }
        final ListView listView = getListView();
        listView.setBackgroundColor(Color.parseColor("#404040"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.album_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);

        Player.pls.setPlaylist(createPlaylist(item));
        Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
        Player.brgui.Stop();
        Player.brgui.Play();
        finish();
    }

    private ArrayList<Integer> createPlaylist(String inp) {
        ArrayList<Integer> temp = new ArrayList<Integer>();
        int pos = 0;

        while (pos < Player.songs.size())
        {
            if ((Player.songs.get(pos).getmAlbum().compareTo(inp) == 0))
            {
                temp.add(pos);
            }
            pos++;
        }

        return temp;
    }
}
