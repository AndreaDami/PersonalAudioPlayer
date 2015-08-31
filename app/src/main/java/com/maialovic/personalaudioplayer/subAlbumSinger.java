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
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by andrea on 16/08/14.
 */
public class subAlbumSinger extends ListActivity {

    String selectedSinger;
    ArrayList<String> temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().hide();

        selectedSinger = getIntent().getStringExtra("Singer");

        HashSet<String> uniqueAlb = new HashSet<String>();
        for(int pos = 0; pos < Player.songs.size(); pos++)
        {
            if (Player.songs.get(pos).getmAuthor().compareTo(selectedSinger) == 0)
                uniqueAlb.add(Player.songs.get(pos).getmAlbum());
        }

        temp = new ArrayList<String>(uniqueAlb);
        Collections.sort(temp);
        
        if (temp != null) {
            CustomAdapterAlbum2 adapter = new CustomAdapterAlbum2(this, temp, selectedSinger);
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
        return super.onOptionsItemSelected(item);
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {

        temp.add(0,temp.get(position));
        temp.remove(position + 1);

        Player.pls.setPlaylist(createPlaylist(temp));
        Toast.makeText(this, temp.get(0) + " selected", Toast.LENGTH_LONG).show();
        Player.brgui.Stop();
        Player.brgui.Play();
        finish();
    }

    private ArrayList<Integer> createPlaylist(ArrayList<String> tmp) {
        ArrayList<Integer> temp2 = new ArrayList<Integer>();
        int pos = 0;

        while (pos < tmp.size() )
        {
            int pos2 = 0;
            while ( pos2 < Player.songs.size() )
            {
                if ((Player.songs.get(pos2).getmAuthor().compareTo(selectedSinger) == 0) && (Player.songs.get(pos2).getmAlbum().compareTo(tmp.get(pos)) == 0)) {
                    temp2.add(pos2);
                }
                pos2++;
            }
            pos++;
        }

        return (temp2);
    }
}

