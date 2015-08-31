package com.maialovic.personalaudioplayer;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Date;

import static com.maialovic.personalaudioplayer.Player.brgui;
import static com.maialovic.personalaudioplayer.Player.pls;
import static com.maialovic.personalaudioplayer.Player.songs;

/**
 * Created by andrea on 27/10/14.
 */
public class GuiPlayerBridge {

    private MediaPlayerP mpl;
    private View view;
    private static Thread xx;
    private Context content;
    private boolean pause_state;

    public GuiPlayerBridge( View i_view, Context i_context)
    {
        view = i_view;
        content = i_context;
        pause_state = false;

        SeekBar skb = (SeekBar) view.findViewById(R.id.seekbar1);
        skb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onStopTrackingTouch(SeekBar skb)
            {

            }


            @Override
            public void onStartTrackingTouch(SeekBar skb)
            {

            }

            @Override
            public void onProgressChanged(SeekBar skb, int progress, boolean fromUser)
            {
                if (fromUser)
                    mpl.mp.seekTo(progress);
            }
        });


    }

    public void Play()
    {
        if (mpl == null)
        try {
            Song song = songs.get(pls.getPosition());
            mpl = new MediaPlayerP(song);

            mpl.Play();

            if (xx != null)
                xx.interrupt();

            SeekBar sk = (SeekBar) view.findViewById(R.id.seekbar1);
            sk.setMax(mpl.getDuration());

            xx = new Thread(new progressbar(mpl, view));
            xx.start();
            ImageView im = (ImageView) view.findViewById(R.id.image);
            im.setImageBitmap(song.getmCover());

            TextView tx1 = (TextView) view.findViewById(R.id.current);
            tx1.setText(String.valueOf(pls.getPosition2() + 1));
            TextView tx2 = (TextView) view.findViewById(R.id.total);
            tx2.setText(String.valueOf(pls.getSize()));

            TextView tx3 = (TextView) view.findViewById(R.id.left_time);
            tx3.setText(String.valueOf("0:00"));
            TextView tx4 = (TextView) view.findViewById(R.id.remain_time);
            String seco;
            int seconds = (mpl.getDuration() % 60000) / 1000;
            if (seconds < 10)
                seco = "0" + Integer.toString(seconds);
            else
                seco = Integer.toString(seconds);
            tx4.setText(String.valueOf(Integer.toString(mpl.getDuration() / 60000) + ":" + seco));

            TextView ss = (TextView) view.findViewById(R.id.author);
            ss.setText(songs.get(pls.getPosition()).getmAuthor() + " - " + songs.get(pls.getPosition()).getmTitle());
            TextView ss2 = (TextView) view.findViewById(R.id.album);
            ss2.setText(songs.get(pls.getPosition()).getmAlbum());
        }
        catch (Exception e)
        {
        }
    }

    public void Stop()
    {
        if (mpl != null) {
            mpl.Stop();
            ImageView imv = (ImageView)view.findViewById(R.id.image);
            imv.setImageDrawable(null);
            TextView tx1 = (TextView) view.findViewById(R.id.current);
            tx1.setText("");
            TextView tx2 = (TextView) view.findViewById(R.id.total);
            tx2.setText("");
            SeekBar vv = (SeekBar) view.findViewById(R.id.seekbar1);
            vv.setMax(0);
            vv.setProgress(1);

            TextView tx3 = (TextView) view.findViewById(R.id.left_time);
            tx3.setText(String.valueOf("-:--"));
            TextView tx4 = (TextView) view.findViewById(R.id.remain_time);
            tx4.setText(String.valueOf("-:--"));

            TextView ss = (TextView) view.findViewById(R.id.author);
            ss.setText("");
            TextView ss2 = (TextView) view.findViewById(R.id.album);
            ss2.setText("");

            mpl = null;
        }
    }

    public void Next()
    {
        if (mpl != null) {
            Stop();
        }
        pls.Next();
    }

    public void Prev()
    {
        if (mpl != null){
            Stop();
        }
        pls.Prev();
    }

    public class progressbar implements Runnable {
        private MediaPlayerP mpp;
        private long tn;
        private View view;
        private boolean type ;

        public progressbar(MediaPlayerP i_mpl, View i_view) {
            mpp = i_mpl;
            view = i_view;
        }

        @Override
        public synchronized void run() {
            tn = new Date().getTime();
            SeekBar sb1 = (SeekBar) view.findViewById(R.id.seekbar1);
                while ((mpl != null) && (mpl.isPlaying())) {
                sb1.setProgress(mpp.mp.getCurrentPosition());
                ((Player)brgui.content).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        long ttn = new Date().getTime();
                        if ((ttn - tn) > 5) {
                            TextView tx3 = (TextView) view.findViewById(R.id.left_time);
                            String seco;
                            if (mpl != null) {
                                int seconds = (mpl.mp.getCurrentPosition() % 60000) / 1000;
                                if (seconds < 10)
                                    seco = "0" + Integer.toString(seconds);
                                else
                                    seco = Integer.toString(seconds);

                                if (mpl != null)
                                    tx3.setText(String.valueOf(Integer.toString(mpl.mp.getCurrentPosition() / 60000) + ":" + seco));
                                tn = new Date().getTime();
                            }
                        }
                    }
                });
            }
        }

    }

    public void resume()
    {
        if (pause_state) {
            mpl.resume_playing();
            pause_state = false;
            xx = new Thread(new progressbar(brgui.mpl, brgui.view));
            xx.start();
        }
    }

    public void pause()
    {
        if (Player.isPlaying())
        {
            mpl.setPause();
            pause_state = true;
        }
    }

    public boolean get_mpl()
    {
        return mpl != null;
    }


    public boolean is_playing()
    {
        return  mpl.isPlaying();
    }


}
