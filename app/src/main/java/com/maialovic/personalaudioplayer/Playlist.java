package com.maialovic.personalaudioplayer;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by andrea on 25/08/14.
 */
public class Playlist {
    private ArrayList<Integer> playlist;
    private ArrayList<Integer> pls1;
    private ArrayList<Integer> pls2;
    private boolean random;
    private boolean repeat;
    private Integer position;

    public Playlist(){

        random = false;
        playlist = new ArrayList<Integer>(Player.songs.size());
        pls1 = new ArrayList<Integer>(Player.songs.size());
        pls2 = new ArrayList<Integer>(Player.songs.size());
        position = 0;
        int pos = 0;
        repeat = false;
        while (pos < Player.songs.size())
        {
            playlist.add(pos);
            pls1.add(pos);
            pls2.add(pos);
            pos++;
        }
    }

    public Integer getSize()
    {
        return playlist.size();
    }

    public void random()
    {
        if (random)
        {
            random = false;
            int ps = pls1.get(position);
            int pos = 0;
            pls1 = new ArrayList<Integer>(pls2);
            while (ps != pls2.get(pos))
            {
                pos++;
            }
            position = pos;
        }
        else
        {
            random = true;
            Collections.shuffle(pls1);
        }
    }

    public void setPlaylist(ArrayList<Integer> i_playlist)
    {
        position = 0;
        this.playlist = new ArrayList<Integer>(i_playlist);
        pls1 = new ArrayList<Integer>();
        pls2 = new ArrayList<Integer>();
        Integer pos = 0;
        while (pos < i_playlist.size())
        {
            pls1.add(pos);
            pls2.add(pos);
            pos++;
        }
        if (Player.getRandom())
        {
            Collections.shuffle(pls1);
        }
    }



    public void setPlaylist2(ArrayList<ArrayList<Integer>> i_pl)
    {
        playlist = i_pl.get(0);
    }

    public int getPosition()
    {
        return playlist.get(pls1.get(position));
        //return position;
    }

    public void SetPosition(Integer integer)
    {
        position = integer;
    }

    public void Next() {
        if (position < playlist.size() - 1) {
            position++;
            Player.brgui.Play();
        }
        else {
            if (repeat) {
                position = 0;
            }
            if (Player.btnPress) {
                position = 0;
                Player.btnPress = false;
            }
        }
        Player.brgui.Play();
    }

    public void Prev() {
        if (position > 0)
        position--;
        else
            position = playlist.size() - 1;

        Player.btnPress = false;
        Player.brgui.Play();
    }


    public Integer getPosition2()
    {
        return pls1.get(position);
    }

    public void setRepeat()
    {
        repeat = !repeat;
    }
}
