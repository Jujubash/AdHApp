package com.app.adhapp;

import android.os.Bundle;
import android.util.Log;

import com.app.adhapp.NSD.BluetoothSDPEngine;

public class SessionStarter extends MainActivity {
    private BluetoothSDPEngine bluetoothSDPEngine;
    private static final String TAG = "Session Starter ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_starter);
        Log.d(TAG, "onCreate() mode ");
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