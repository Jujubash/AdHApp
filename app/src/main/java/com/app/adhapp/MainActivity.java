package com.app.adhapp;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.app.adhapp.NSD.connection;
import com.app.adhapp.NSD.nsdHelper;

public class MainActivity extends AppCompatActivity {
    private NsdManager nsdManager;
    private NsdManager.RegistrationListener registrationListener;
    private NsdManager.DiscoveryListener discoveryListener;

    public static void main(String[] args) {
        System.out.println("This is main!");
    }

    //---------------------------------- Application's Activity ----------------------------------//
    //------------------------ Teil 2/4: Discover services on the network ------------------------//
    nsdHelper discoverService = new nsdHelper().discoverServices();

    //------------------ Teil 4/4: Unregister your service on application close ------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nsdHelper NsdServiceInfo = new nsdHelper();
        nsdHelper nsdHelper = new nsdHelper();
        nsdManager = Context.getSystemService(Context.NSD_SERVICE); // TODO: import getSystemService shouldn't be private

        System.out.println(NsdServiceInfo); // Just do it

        @Override
        protected void onPause() {
            if (nsdHelper != null) {
                nsdHelper.tearDown();
            }
            super.onPause();
        }

        @Override
        protected void onResume() {
            super.onResume();
            if (nsdHelper != null) {
                nsdHelper.registerService(connection.getLocalPort());
                nsdHelper.discoverServices();
            }
        }

        @Override
        protected void onDestroy() {
            nsdHelper.tearDown();
            connection.tearDown();
            super.onDestroy();
        }

        // NsdHelper's tearDown method
        public void tearDown() {
            nsdManager.unregisterService(registrationListener);
            nsdManager.stopServiceDiscovery(discoveryListener);
        }

    }
}