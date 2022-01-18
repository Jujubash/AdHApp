package com.app.adhapp;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.app.adhapp.NSD.connection;
import com.app.adhapp.NSD.nsdHelper;

public class MainActivity extends AppCompatActivity {
    private NsdManager nsdManager;
    private NsdManager.RegistrationListener registrationListener;
    private NsdManager.DiscoveryListener discoveryListener;
    nsdHelper discoverService = new nsdHelper().discoverServices();
    private static final String TAG = "AdHApp Activity ";

    public static void main(String[] args) {
        System.out.println("This is the main!");
    }

    //---------------------------------- Application's Activity ----------------------------------//
    RelativeLayout layout;     // TODO: Teil 2/4: Discover services on the network ??? nsdHelper.discoverServices()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() mode ");
        setContentView(R.layout.activity_main);

        nsdHelper NsdServiceInfo = new nsdHelper();
        nsdHelper nsdHelper = new nsdHelper();
        nsdManager = Context.getSystemService(Context.NSD_SERVICE); // TODO: import getSystemService shouldn't be private

        System.out.println(NsdServiceInfo); // Just do it
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
        if (nsdHelper != null) {
            nsdHelper.registerService(connection.getLocalPort());
            nsdHelper.discoverServices();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() mode ");
        if (nsdHelper != null) {
            nsdHelper.tearDown();
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
        nsdHelper.tearDown();
        connection.tearDown();
    }

    // NsdHelper's tearDown method
    public void tearDown() {
        Log.d(TAG, "tearDown() mode ");
        nsdManager.unregisterService(registrationListener);
        nsdManager.stopServiceDiscovery(discoveryListener);
    }
}