package com.maialovic.personalaudioplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class optionMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        getActionBar().hide();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_songs, menu);
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

    public void openSingerList(View view)
    {
        Intent myIntent=new Intent(view.getContext(), ListSinger.class);
        startActivity(myIntent);
        finish();
    }

    public void openAlbumList(View view)
    {
        Intent myIntent = new Intent(view.getContext(), ListAlbum.class);
        startActivity(myIntent);
        finish();
    }
    public void openSettingList(View view)
    {
        Intent myIntent=new Intent(view.getContext(), About.class);
        startActivity(myIntent);
        finish();
    }

    public void openFullList(View view)
    {
        Intent myIntent=new Intent(view.getContext(), ListFull.class);
        startActivity(myIntent);
        finish();
    }

    public  void openFullGender(View view)
    {
        Intent myIntent = new Intent(view.getContext(), ListGender.class);
        startActivity(myIntent);
        finish();
    }

}
