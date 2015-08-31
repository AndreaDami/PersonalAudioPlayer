package com.maialovic.personalaudioplayer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

/**
 * Created by andrea on 21/07/14.
 */


public class MediaPlayerP {
    public MediaPlayer mp;
    private Song song;
    private boolean pause = false;

    public MediaPlayerP(Song i_song) {
        song = i_song;
    }


    public void Play() throws IOException {
        mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.setDataSource(song.getmPath());
        mp.prepare();
        mp.start();

       mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                if (!Player.btnPress) {
                    Player.brgui.Stop();
                    Player.brgui.Next();
                }
            }
        });

        mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Player.brgui.Stop();
                Player.brgui.Next();
                Log.v("errore", String.valueOf(what));
                return true;
            }
        });

    }

    public void Stop() {
            if (mp.isPlaying()) {
                mp.stop();
            }
        mp.reset();
        mp.release();
        mp = null;
    }

    public Boolean isPlaying()
    {
        if (mp != null)
            return mp.isPlaying();
        else
            return false;
    }

    public Integer getDuration()
    {
        return mp.getDuration();
    }

    public void setPause ()
    {
        pause = true;
        this.mp.pause();
    }

    public boolean isPaused()
    {
        return pause;
    }

    public void resume_playing()
    {
        pause = false;
        mp.start();
    }


    }
