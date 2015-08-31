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

import static java.util.Collections.shuffle;

public class ListFull extends ListActivity {

    ArrayList<String> full_tracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().hide();
        full_tracks = Player.getTracks();


        if (full_tracks != null) {
            CustomAdapter3l adapter = new CustomAdapter3l(this, full_tracks);
            setListAdapter(adapter);
        }
        final ListView listView = getListView();
        listView.setBackgroundColor(Color.parseColor("#404040"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.open_full_list, menu);
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
        String item = (String) getListAdapter().getItem(position);
        Player.pls.setPlaylist2(createPlaylist(item));
        Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
        Player.pls.SetPosition(position);
        Player.brgui.Stop();
        Player.brgui.Play();
        finish();
    }

    private ArrayList<ArrayList<Integer>> createPlaylist(String inp) {
        ArrayList<ArrayList<Integer>> suk = new ArrayList<ArrayList<Integer>>();

            ArrayList<Integer> pr = new ArrayList<Integer>();
            for(int pos = 0; pos < Player.songs.size(); pos++)
                pr.add(pos);
            ArrayList<Integer> se = new ArrayList<Integer>(pr);
        if (Player.getRandom()) {
            shuffle(pr);
        }
            suk.add(pr);
            suk.add(se);

      return suk;
    }


}
