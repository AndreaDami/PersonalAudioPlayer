package com.maialovic.personalaudioplayer;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class About extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        setContentView(R.layout.activity_setting);
        ImageView im = (ImageView) findViewById(R.id.image);
        im.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher));
        TextView b = (TextView) findViewById(R.id.text2);
        try {
            b.setText("Version " + getPackageManager().getPackageInfo(getPackageName(), 0).versionName + "\n");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        TextView a = (TextView) findViewById(R.id.text1);
        a.setText("Personal Audio Player\n");
        TextView c = (TextView) findViewById(R.id.text3);
        c.setText("\n\n©2014 Andrea D'Amico\n");
        TextView d = (TextView) findViewById(R.id.text4);
        d.setText("All rights reserved\n\n\n");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
