package com.app.adhapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.content.Context;
import android.widget.TextView;
import android.net.nsd.NsdManager;
import com.app.adhapp.NSD.SDPEngine;
import android.widget.RelativeLayout;
import com.app.adhapp.NSD.BluetoothSDPEngine;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SDPEngine {
    private SDPEngine sdpEngine;
    private NsdManager nsdManager;
    private NsdManager.RegistrationListener registrationListener;
    private NsdManager.DiscoveryListener discoveryListener;
    private BluetoothSDPEngine bluetoothSDPEngine;
    private String format_string; // TODO: IST void offer(CharSequence[] formats)
    private static final String TAG = "AdHApp Activity ";
    /** user interface related buttons and text input */
    private Button button_start, button_search;

    /////////////////////////////////////////////////////////////////////////////////////
    //                             Lifecycle from MainActicity                         //
    /////////////////////////////////////////////////////////////////////////////////////
    RelativeLayout layout;     // TODO: Teil 2/4: Discover services on the network ??? nsdHelper.discoverServices()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate() mode ");
        /** buttons MainActivity menu */
        button_start = (Button) findViewById(R.id.button_start);
        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSessionStarter();
            }
        });
        button_search = (Button) findViewById(R.id.button_search);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSessionSearcher();
            }
        });
    }

    /**
     * redirecting user to the SessionStarter Activity
     */
    public void openSessionStarter() {
        Intent intent_start = new Intent(this, SessionStarter.class);
        startActivity(intent_start);
    }

    /**
     * redirecting user to the SessionSearcher Activity
     */
    public void openSessionSearcher() {
        Intent intent_search = new Intent(this, SessionSearcher.class);
        startActivity(intent_search);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() mode ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() mode ");
        // TODO: if (session != 0) goto Session Activity
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() mode ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart() mode ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() mode ");
        this.bluetoothSDPEngine.onDestroy();
        super.onDestroy();
    }

    public void tearDown() {
        Log.d(TAG, "tearDown() mode ");
        // NsdHelper's tearDown method:
        this.bluetoothSDPEngine.tearDown();
    }

    // TODO: implement Interface methods

    @Override
    public void offer(CharSequence[] formats) {

    }

    @Override
    public void stopDiscovery(CharSequence[] formats) {

    }

    @Override
    public void getChosenServiceInfo(CharSequence[] formats) {

    }
}