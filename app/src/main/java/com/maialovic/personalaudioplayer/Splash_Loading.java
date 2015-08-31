package com.maialovic.personalaudioplayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;


public class Splash_Loading extends Activity {

    final static int wait_time = 25;
    private ArrayList<ArrayList<Song>> partial_lists;
    private boolean scan_check;
    private android.os.Handler [] hdr;
    private ArrayList<String> path_files;
    public static ArrayList<Song> songs;
    private ArrayList<String> path_files_temp;
    private int arra[];
    private int th_numb;
    waiting_th  wai;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__loading);
        getActionBar().hide();
        partial_lists = new ArrayList<ArrayList<Song>>(4);
        partial_lists.add(new ArrayList<Song>());
        partial_lists.add(new ArrayList<Song>());
        partial_lists.add(new ArrayList<Song>());
        partial_lists.add(new ArrayList<Song>());
        path_files = new ArrayList<String>();
        path_files_temp = new ArrayList<String>();
        songs = new ArrayList<Song>();
        Splash_Loading.songs = new ArrayList<Song>(new ArrayList<Song>(deserialize_songs()));

        try {
            Thread.sleep(wait_time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setup stp = new setup();
        new Thread(stp).start();

        hdr = new android.os.Handler[4];

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash__loading, menu);
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


    public class setup implements Runnable {
        public setup() {
        }

        public void run() {

            path_files.addAll(check_directory_for_mp3(Environment.getDataDirectory().getPath() + "/"));
            path_files.addAll(check_directory_for_mp3(Environment.getRootDirectory().getPath() + "/"));
            path_files.addAll(check_directory_for_mp3(Environment.getExternalStorageDirectory().getPath() + "/"));
            path_files_temp.addAll(check_data());

            ArrayList<String> add = new ArrayList<String>(path_files);
            ArrayList<String> rem = new ArrayList<String>(path_files_temp);

            add.removeAll(path_files_temp);
            Log.v("path", Integer.toString(path_files_temp.size()));
            rem.removeAll(path_files);
            Log.v("path", Integer.toString(path_files.size()));
            Song_manager(add, rem);
        }
    }

    private void Song_manager(final ArrayList<String> add, ArrayList<String> rem)
    {

        ArrayList<Song> song_rem = new ArrayList<Song>();

        Thread stm = new Thread(new Runnable() {
            @Override
            public void run() {
                setupMp3(add);
            }
        });

        stm.start();
        try {
            stm.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (rem != null) {
            int posi;
            for (int pp = 0; pp < rem.size(); pp++)
            {
                posi = 0;
                while (posi < Splash_Loading.songs.size())
                {
                    if (Splash_Loading.songs.get(posi).getmPath().compareTo(rem.get(pp)) == 0)
                    {
                        Splash_Loading.songs.remove(posi);
                        break;
                    }
                    else
                        ++posi;
                }
            }
        }

        if (song_rem != null && song_rem.size() > 0)
            Splash_Loading.songs.removeAll(song_rem);

        if ( path_files.size() > 0)
            store_actual_list();


            Intent myIntent = new Intent(getApplicationContext(), Player.class);
            Player.songs = new ArrayList<Song>(Splash_Loading.songs);
            serialize_songs();
            startActivity(myIntent);
            finish();
    }

    private ArrayList<String> check_directory_for_mp3(String dir) {
        ArrayList<String> temp = new ArrayList<String>();
        File directory = new File(dir);
        File[] fList = directory.listFiles();
        if (fList != null && fList.length > 0)
        for (File file : fList) {
            if (file.isFile() && !(file == null)) {
                if (file.toString().endsWith(".mp3"))
                    temp.add(file.getAbsolutePath());
            } else if (file.isDirectory()) {
                temp.addAll(check_directory_for_mp3(file.getAbsolutePath()));
            }
        }
        return temp;
    }

    public void setupMp3(ArrayList<String> add) {
        arra = new int[ Runtime.getRuntime().availableProcessors()];
        for (int r = 0; r <  Runtime.getRuntime().availableProcessors(); r++)
            arra[r] = 0;
        int f1 = (int) Math.floor(add.size() / 2);
        int f2 = add.size() - 1;
        if (add.size() == 0)
            return;

        th_numb = Runtime.getRuntime().availableProcessors();
        if (Runtime.getRuntime().availableProcessors() == 1)
        {
            extract_data x1 = new extract_data(0, f2, 0, add);
           Thread t = new Thread(x1);
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        else
        if (Runtime.getRuntime().availableProcessors() == 2)
        {
            if (add.size() < 2)
            {
                extract_data x1 = new extract_data(0, f2, 0, add);
                Thread t1 = new Thread(x1);
                t1.start();
                try {
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            else
            {
                extract_data x1 = new extract_data(0, f1, 0, add);
                extract_data x2 = new extract_data(f1 + 1, f2, 1, add);
                Thread t1 = new Thread(x1);
                Thread t2 = new Thread(x2);
                t1.start();
                t2.start();
                try {
                    t1.join();
                    t2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        else
        if (Runtime.getRuntime().availableProcessors() == 4)
        {
            if (add.size() < 2)
            {
                extract_data x1 = new extract_data(0, f2, 0, add);
                Thread t1 = new Thread(x1);
                t1.start();

                try {
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            else if (add.size() >= 2 && add.size() < 4)
            {
                extract_data x1 = new extract_data(0, f1, 0, add);
                extract_data x2 = new extract_data(f1 + 1, f2, 1, add);
                Thread t1 = new Thread(x1);
                Thread t2 = new Thread(x2);
                t1.start();
                t2.start();
                try {
                    t1.join();
                    t2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                int f3 = (int) Math.floor(add.size() / 4);
                extract_data x1 = new extract_data(0, f3, 0, add);
                extract_data x2 = new extract_data(f3 + 1, 2 * f3, 1, add);
                Thread t1 = new Thread(x1);
                Thread t2 = new Thread(x2);
                extract_data x3 = new extract_data( 2 * f3 + 1, 3 * f3, 2, add);
                extract_data x4 = new extract_data(3 * f3 + 1, f2, 3, add);
                Thread t3 = new Thread(x3);
                Thread t4 = new Thread(x4);
                t1.start();
                t2.start();
                t3.start();
                t4.start();

                try {
                    t1.join();
                    t2.join();
                    t3.join();
                    t4.join();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        wai = new waiting_th();
        Thread t5 = new Thread(wai);
        t5.start();
        try {
            t5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public class extract_data implements Runnable {
        private int start;
        private int finish;
        private int name;
        private ArrayList<String> add;

        public extract_data(int start, int finish, int name, ArrayList<String> i_add) {
            this.start = start;
            this.finish = finish;
            this.name = name;
            this.add = i_add;
        }

        public void run() {

            try {
                Thread.sleep(wait_time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int diff = this.name * (int) Math.floor(this.add.size() / 2);

            int error = 0;
            for (int cn = this.start - diff; cn < this.finish - diff + 1; cn++) {
                if (this.add.size() > 0) {
                    try {
                        partial_lists.get(this.name).add(new Song(this.add.get(cn + diff), getApplicationContext()));
                    }
                    catch (Exception ex){
                        error++;
                    }
                }
                if (cn == this.finish - diff + 1)
                    arra[this.name] = 1;
            }
        }
    }

    private ArrayList<String> check_data() {
        FileInputStream inputStream;
        BufferedReader reader;
        String line;
        ArrayList<String> temp = new ArrayList<String>();
        try {
            if (getFileStreamPath("songs_table") != null) {
                File mypath = new File(getFileStreamPath("songs_table").toString());
                if (mypath.exists()) {
                    int pos = 0;
                    inputStream = new FileInputStream(getFileStreamPath("songs_table").toString());
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    line = reader.readLine();
                    while (line != null) {
                        temp.add(pos, line);
                        pos++;
                        line = reader.readLine();
                    }
                    Log.v("restore songs_table", Integer.toString(Splash_Loading.songs.size()));
                    inputStream.close();

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;
    }

    private ArrayList<Song> deserialize_songs()
    {
        ArrayList<Song> temp_songs = new ArrayList<Song>();
        try {
                FileInputStream inputStream;
                File f = new File(getFileStreamPath("songs").toString());
            if (f.isFile()) {
                    inputStream = new FileInputStream(getFileStreamPath("songs").toString());
                    ObjectInputStream objectInStream = new ObjectInputStream(inputStream);
                    temp_songs = (ArrayList<Song>) objectInStream.readObject();
                objectInStream.close();
                Log.v("restore songs", Integer.toString(temp_songs.size()));
                return temp_songs;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp_songs;
    }

    private void serialize_songs()
    {
        FileOutputStream outStream;
        try {
            outStream = openFileOutput("songs", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutStream = new ObjectOutputStream(outStream);

            Log.v("store songs", Integer.toString(Splash_Loading.songs.size()));
            objectOutStream.writeObject(Splash_Loading.songs);

            objectOutStream.flush();
            objectOutStream.close();
            outStream.flush();
            outStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void store_actual_list()
    {
        int pos = 0;
        FileOutputStream outputStream;
        try {
            Log.v("store songs_table", Integer.toString(path_files.size()));
            outputStream = openFileOutput("songs_table", Context.MODE_PRIVATE);
            while (pos < path_files.size())
            {
                outputStream.write((path_files.get(pos) + "\n").getBytes());
                pos++;
            }
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public class waiting_th implements Runnable{

        @Override
        public void run() {
            if (!(sum_fi() != th_numb))
            {}
            else {
                Splash_Loading.songs.addAll(partial_lists.get(0));
                Splash_Loading.songs.addAll(partial_lists.get(1));
                Splash_Loading.songs.addAll(partial_lists.get(2));
                Splash_Loading.songs.addAll(partial_lists.get(3));
            }
        }
    }


    private int sum_fi(){
        int r = 0;
        for (int a = 0; a < Runtime.getRuntime().availableProcessors(); a++)
            r += arra[a];
        return r;
    }
}
