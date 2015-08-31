package com.maialovic.personalaudioplayer;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class ListSinger extends ListActivity {

    ArrayList<String> listSinger;
    String singer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().hide();

        listSinger = Player.getAuthors();

        ArrayList<Integer> album_num = new ArrayList<Integer>();
        HashSet<String> album_nam = new HashSet<String>();
        ArrayList<Integer> album_song_counter = new ArrayList<Integer>();

        for (String art: listSinger)
        {
            int pos = 0;
            int song_num = 0;
            while (pos < Player.songs.size()) {
                if (Player.songs.get(pos).getmAuthor().compareTo(art) == 0) {
                    album_nam.add(Player.songs.get(pos).getmAlbum());
                }

                if (Player.songs.get(pos).getmAuthor().compareTo(art) == 0)
                    song_num++;

                pos++;
            }
            album_num.add(album_nam.size());
            album_song_counter.add(song_num);
            album_nam.clear();
            }

        if (listSinger != null) {
            CustomAdapterSinger adapter = new CustomAdapterSinger(this, listSinger, album_num, album_song_counter);
            setListAdapter(adapter);
        }
        final ListView listView = getListView();
        listView.setBackgroundColor(Color.parseColor("#404040"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_singer, menu);
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

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        singer = (String) getListAdapter().getItem(position);
        Intent myInte = new Intent(super.getApplicationContext(), subAlbumSinger.class);
        myInte.putExtra("Singer", singer);
        startActivity(myInte);
        finish();
    }


    public boolean album_check(ArrayList<String> album_dir, String album_names)
    {
        int pos = 0;
        while (album_dir.get(pos).compareTo(album_names) != 0)
         pos++;

        return (pos < album_dir.size() - 1);
    }

    public void album_increase_counter(ArrayList<String> album_dir, ArrayList<Integer> album_song_counter, String album_names)
    {
        int pos = 0;
        while (album_dir.get(pos).compareTo(album_names) != 0)
            pos++;

        album_song_counter.set(pos, album_song_counter.get(pos) + 1);
    }

}

