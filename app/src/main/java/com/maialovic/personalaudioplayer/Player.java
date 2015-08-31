package com.maialovic.personalaudioplayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Player extends Activity {

    public static Playlist pls;

    public static ArrayList<Song> songs;
    public static ArrayList<Song> songs_sorted_album;
    private static Boolean random;
    private static Boolean repeat;
    public static Boolean btnPress;
    public static GuiPlayerBridge brgui;
    public static Activity activity;
    TelephonyManager TelephonyMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        getActionBar().hide();
        random = false;
        repeat = false;
        btnPress = false;
        Button play = (Button) findViewById(R.id.play);
        play.setText("Play");

        TelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        TelephonyMgr.listen(new MyPhoneListener(this),  PhoneStateListener.LISTEN_CALL_STATE);
        activity = this;
        brgui = new GuiPlayerBridge( findViewById(R.id.left_time).getRootView(), activity);
        songs_sorted_album = new ArrayList<Song>(songs);
        Collections.sort(songs_sorted_album, new CustomComparatorAlbums());
        pls = new Playlist();
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.player, menu);
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

    public void ch_screen(View view) {
        Intent myIntent = new Intent(view.getContext(), optionMenu.class);
        startActivity(myIntent);
    }

    public synchronized void Stop(View view) {
        Button play = (Button) findViewById(R.id.play);
        play.setText("Play");
        play.setTextSize(16);
        brgui.Stop();
    }

    public void Play(View view) {
        Button play = (Button) findViewById(R.id.play);
        if (songs.size() > 0)
        {
            if ((!brgui.get_mpl())) {
                brgui.Play();
                play.setText("Pause");
            }
            else
            if  (play.getText() == "Play") {

                brgui.resume();
                play.setText("Pause");
                play.setTextSize(13);
            }
            else if (play.getText() == "Pause")
            {
                brgui.pause();
                play.setText("Play");
                play.setTextSize(16);
            }
        }

    }

    public void Next(View view) {
       Player.btnPress = true;
        Button play = (Button) findViewById(R.id.play);
        play.setText("Pause");
        play.setTextSize(13);
       brgui.Next();
    }

    public void Prev(View view) {
        Player.btnPress = true;
        Button play = (Button) findViewById(R.id.play);
        play.setText("Pause");
        play.setTextSize(13);
        brgui.Prev();
    }

    public void Random(View view)
    {
        if (random)
        {
            random = false;
            ImageButton bt = (ImageButton) findViewById(R.id.rand);
            bt.setImageResource(R.drawable.random);
        }
        else
        {
            random = true;
            ImageButton bt = (ImageButton) findViewById(R.id.rand);
            bt.setImageResource(R.drawable.random2);
        }

        pls.random();
    }

    public void Repeat(View view)
    {
        if (!repeat)
        {
            repeat = true;
            ImageButton bt = (ImageButton) findViewById(R.id.repeat);
            bt.setImageResource(R.drawable.x_repeat2);
        }
        else
        {
            repeat = false;
            ImageButton bt = (ImageButton) findViewById(R.id.repeat);
            bt.setImageResource(R.drawable.x_repeat);
        }
        pls.setRepeat();
    }

    @Override
    public void onBackPressed() {
    }

    public Context getContext()
    {
        return   getApplicationContext();
    }

    static public ArrayList<String> getAlbums()
    {
        Set<String> albums = new HashSet<String>();
        for (int pos = 0; pos < songs.size() ; pos++)
        {
            albums.add(songs.get(pos).getmAlbum());
        }
        ArrayList<String> ret = new ArrayList<String>(albums);
        Collections.sort(ret);
        return ret;
    }

    static public ArrayList<String> getAuthors()
    {
        Set<String> authors = new HashSet<String>();
        for (int pos = 0; pos < songs.size() ; pos++)
        {
            authors.add(songs.get(pos).getmAuthor());
        }
        ArrayList<String> ret = new ArrayList<String>(authors);
        Collections.sort(ret);
        return ret;
    }

    public static ArrayList<String> getGenders()
    {
        Set<String> genders = new HashSet<String>();
        for (int pos = 0; pos < songs.size() ; pos++)
        {
            genders.add(songs.get(pos).getmGender());
        }
        ArrayList<String> ret = new ArrayList<String>(genders);
        Collections.sort(ret);
        return ret;
    }

    static public ArrayList<String> getTracks()
    {
        ArrayList<String> tracks = new ArrayList<String>();
        for (int pos = 0; pos < songs.size() ; pos++)
        {
            tracks.add(songs.get(pos).getmTitle());
        }
        return new ArrayList<String>(tracks);
    }

    public static Boolean getRandom()
    {
        return random;
    }

    public Player getMe()
    {
        return Player.this;
    }

    public static boolean isPlaying()
    {
        if (brgui.get_mpl())
            return brgui.is_playing();
        else
            return false;
    }
}
