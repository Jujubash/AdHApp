package com.app.adhapp;

import android.content.Context;
import android.content.Intent;
import android.net.nsd.NsdManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.adhapp.NSD.BluetoothSDPEngine;

public class MainActivity extends AppCompatActivity {
    private NsdManager nsdManager;
    private NsdManager.RegistrationListener registrationListener;
    private NsdManager.DiscoveryListener discoveryListener;
    private BluetoothSDPEngine discoverService;
    private BluetoothSDPEngine bluetoothSDPEngine;
    private static final String TAG = "AdHApp Activity ";

    private Button button_start, button_search, button_search_format, button_start_format;
    private EditText edit_format;       // TODO: Search and Start Activity Inputs
    private TextView textview_format;   // TODO: Search and Start Activity Inputs

    //---------------------------------- Application's Activity ----------------------------------//
    RelativeLayout layout;     // TODO: Teil 2/4: Discover services on the network ??? nsdHelper.discoverServices()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate() mode ");
        this.bluetoothSDPEngine = new BluetoothSDPEngine(this, null); // TODO: call constructor from BluetoothSDPEngine();
        this.bluetoothSDPEngine.onCreate();
        nsdManager = (NsdManager) this.getSystemService(Context.NSD_SERVICE);
        this.discoverService = new BluetoothSDPEngine(this, null);
        this.discoverService.onCreate();

        //-------------------------------- Buttons All Activities --------------------------------//
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
                edit_format = (EditText)findViewById(R.id.editText1);
                textview_format = (TextView)findViewById(R.id.textView1);
                textview_format.setText("Welcome "+ edit_format.getText().toString()+"!");
                openSessionSearcher();
            }
        });
    }

    public void openSessionStarter() {
        Intent intent_start = new Intent(this, SessionStarter.class);
        startActivity(intent_start);
    }

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