package com.app.adhapp;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.adhapp.NSD.BluetoothSDPEngine;

public class SessionStarter extends MainActivity {
    private BluetoothSDPEngine bluetoothSDPEngine;
    private BluetoothSDPEngine discoverService;
    private NsdManager nsdManager;
    private String format_string;       // format_string is void offer(CharSequence[] formats)
    private static final String TAG = "Session Starter ";
    /** user interface related buttons and text input */
    private Button button_start, button_search, button_search_format, button_start_format;
    private EditText edit_format;       // Search and Start Activity Inputs
    private TextView textview_format;   // Search and Start Activity Inputs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_starter);
        Log.d(TAG, "onCreate() mode ");
        /* creating session */
        this.bluetoothSDPEngine = new BluetoothSDPEngine(this, null); // TODO: call constructor from BluetoothSDPEngine();
        this.bluetoothSDPEngine.onCreate();
        nsdManager = (NsdManager) this.getSystemService(Context.NSD_SERVICE);
        this.discoverService = new BluetoothSDPEngine(this, null);
        this.discoverService.onCreate();

        edit_format = (EditText)findViewById(R.id.edit_format);
        textview_format = (TextView)findViewById(R.id.textview_format);
        format_string = edit_format.getText().toString();
        textview_format.setText("Session chosen: "+ format_string);
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
        if (bluetoothSDPEngine != null) {
            this.bluetoothSDPEngine.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() mode ");
        if (bluetoothSDPEngine != null) {
            this.bluetoothSDPEngine.tearDown();
        }
        super.onPause();
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

    // NsdHelper's tearDown method
    public void tearDown() {
        Log.d(TAG, "tearDown() mode ");
        this.bluetoothSDPEngine.tearDown();
    }
}