package com.henry.aidlTest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;

import com.henry.basic.R;

public class System_Event_Test extends AppCompatActivity {
    Context context;
    Button register;
    Button unregister;
    Button sendevent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_event_test);
        context = getApplicationContext();

        SystemEventManager eventManager = (SystemEventManager) context.getSystemService("test_systemevent");

        register = findViewById(R.id.rigister);
        unregister = findViewById(R.id.unrigister);
        sendevent = findViewById(R.id.sendevent);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventManager.register(eventCallback);
            }
        });

        unregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventManager.unregister(eventCallback);
            }
        });

        sendevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventManager.sendEvent(41541, "dasdsad");
            }
        });

        //  eventManager.sendEvent(1, "test string");
    }

    private IEventCallback.Stub eventCallback = new IEventCallback.Stub() {
        @Override
        public void onSystemEvent(int type, String value) throws RemoteException {
        }
    };


}