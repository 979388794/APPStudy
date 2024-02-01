package com.example.PowerControlTest;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.legacy.content.WakefulBroadcastReceiver;

public class WLWakefulReceiver extends WakefulBroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {
        String extra = intent.getStringExtra("msg");
        Intent serviceIntent = new Intent(context, MyIntentService.class);
        serviceIntent.putExtra("msg", extra);
        startWakefulService(context, serviceIntent);

    }


}
