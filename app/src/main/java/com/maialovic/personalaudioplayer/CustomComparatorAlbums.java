package com.maialovic.personalaudioplayer;

import java.util.Comparator;

/**
 * Created by andrea on 01/09/14.
 */

public class CustomComparatorAlbums implements Comparator<Song> {
    @Override
    public int compare(Song o1, Song o2) {
        return o1.getmAlbum().compareTo(o2.getmAlbum());
    }
}

