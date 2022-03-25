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
    private String format_string;
    private BluetoothSDPEngine bluetoothSDPEngine;
    private NsdManager nsdManager;
    /** setting ID to Starter Activity to it distinguish from other Activities */
    private int activity_id = 1;
    private static final String TAG = "Session Starter ";
    /** user interface related buttons and text input */
    private Button button_start_format;
    private EditText edit_format;
    private TextView textview_format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_starter);
        Log.d(TAG, "onCreate() mode ");
        /** users inputs format to search existing session */
        button_start_format = (Button) findViewById(R.id.button_search_format);
        button_start_format.setOnClickListener(v -> {
            // create session and offer it:
            if (format_string != null) {
                sessionStarter(format_string);
            }
            else
                Log.d(TAG, " Users input isn't valid");
        });
        edit_format = (EditText)findViewById(R.id.edit_format);
        textview_format = (TextView)findViewById(R.id.textview_format);
        format_string = edit_format.getText().toString();
        textview_format.setText("Session created: "+ format_string);
    }

    /**
     * creating session
     * @param format_string
     */
    protected void sessionStarter(String format_string) {
        this.format_string = format_string;
        this.bluetoothSDPEngine = new BluetoothSDPEngine(this, null); // TODO: call constructor from BluetoothSDPEngine();
        this.bluetoothSDPEngine.onCreate();
        nsdManager = (NsdManager) this.getSystemService(Context.NSD_SERVICE);
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
            this.bluetoothSDPEngine.onResume(activity_id);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() mode ");
        if (bluetoothSDPEngine != null) {
            this.bluetoothSDPEngine.tearDown(activity_id);
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
        // NsdHelper's tearDown method:
        this.bluetoothSDPEngine.tearDown(activity_id);
    }
}