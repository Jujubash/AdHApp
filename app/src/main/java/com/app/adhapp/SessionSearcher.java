package com.app.adhapp;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.adhapp.NSD.BluetoothSDPEngine;

public class SessionSearcher extends MainActivity {
    private BluetoothSDPEngine bluetoothSDPEngine;
    private BluetoothSDPEngine discoverService;
    private NsdManager nsdManager;
    private String format_string;       // format_string is void offer(CharSequence[] formats)
    private static final String TAG = "Session Searcher ";
    /** user interface related buttons and text input */
    private Button button_search_format;
    private EditText edit_format;
    private TextView textview_format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_searcher);
        Log.d(TAG, "onCreate() mode ");

        /** users inputs format to search existing session */
        button_search_format = (Button) findViewById(R.id.button_search_format);
        edit_format = (EditText)findViewById(R.id.edit_format);
        textview_format = (TextView)findViewById(R.id.textview_format);
        format_string = edit_format.getText().toString();
        button_search_format.setOnClickListener(v -> {
            //connect here to existing session:
            if (format_string != null) {
                sessionSearcher(format_string);
            }
            else
                Log.d(TAG, " Users input isn't valid");
        });
        textview_format.setText("Session chosen: "+ format_string);
    }

    /**
     * searching session
     * @param format_string
     */
    protected void sessionSearcher(String format_string) {
        this.format_string = format_string;
        this.discoverService = new BluetoothSDPEngine(this, null); // TODO: call constructor from BluetoothSDPEngine();
        this.discoverService.onCreate();
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