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

public class ListGender extends ListActivity {

    ArrayList<String> genderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().hide();

        genderList = Player.getGenders();
        Collections.sort(genderList);
        ArrayList<Integer> gen_counter = new ArrayList<Integer>();

        for (String gen: genderList)
        {
            int pos = 0;
            int gen_num = 0;

            while (pos < Player.songs.size()) {
                if (Player.songs.get(pos).getmAlbum().compareTo(gen) == 0) {
                    gen_num++;
                }
                pos++;
            }
            gen_counter.add(gen_num);
        }

        if (genderList != null) {
            CustomAdapterGender adapter = new CustomAdapterGender(this, genderList);
            setListAdapter(adapter);
        }
        final ListView listView = getListView();
        listView.setBackgroundColor(Color.parseColor("#404040"));
}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.open_artist_view, menu);
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
            if ((Player.songs.get(pos).getmGender().compareTo(inp) == 0))
            {
                temp.add(pos);
            }
            pos++;
        }

        return temp;
    }

}
