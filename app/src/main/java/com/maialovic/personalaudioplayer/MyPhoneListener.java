package com.maialovic.personalaudioplayer;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * Created by asus on 01/11/2014.
 */

public class MyPhoneListener extends PhoneStateListener
{
    private Context ctx;

    public MyPhoneListener(Context in_ctx) {
    ctx = in_ctx;
    }

    public void onCallStateChanged (int state, String incomingNumber)
    {
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                if (Player.brgui != null)
                    Player.brgui.resume();
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                if (Player.brgui != null)
                    Player.brgui.pause();
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                if (Player.brgui != null)
                    Player.brgui.pause();
                break;
        }
    }
    public int update()
    {
        return LISTEN_CALL_STATE;
    }
}
