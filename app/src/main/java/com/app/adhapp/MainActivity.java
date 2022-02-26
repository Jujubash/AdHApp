package com.app.adhapp;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.app.adhapp.NSD.connection;
import com.app.adhapp.NSD.BluetoothSDPEngine;

public class MainActivity extends AppCompatActivity {
    private NsdManager nsdManager;
    private NsdManager.RegistrationListener registrationListener;
    private NsdManager.DiscoveryListener discoveryListener;
    private BluetoothSDPEngine discoverService;
    private static final String TAG = "AdHApp Activity ";

    private BluetoothSDPEngine bluetoothSDPEngine;

    private Button button_start, button_searcher;

    //---------------------------------- Application's Activity ----------------------------------//
    RelativeLayout layout;     // TODO: Teil 2/4: Discover services on the network ??? nsdHelper.discoverServices()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() mode ");
        setContentView(R.layout.activity_main);
        this.bluetoothSDPEngine = new BluetoothSDPEngine(this, null); // TODO: call constructor from BluetoothSDPEngine();
        this.bluetoothSDPEngine.onCreate();
        nsdManager = (NsdManager) this.getSystemService(Context.NSD_SERVICE);
        this.discoverService = bluetoothSDPEngine.discoverServices();

        // Buttons on main menu:
        button_start = (Button) findViewById(R.id.button);
        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionStarter();
            }
        });

        button_searcher = (Button) findViewById(R.id.button);
        button_searcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionSearcher();
            }
        });
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
            this.bluetoothSDPEngine.onPause();
        }
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
    }

    // NsdHelper's tearDown method
    public void tearDown() {
        Log.d(TAG, "tearDown() mode ");
        this.bluetoothSDPEngine.tearDown();
    }
}