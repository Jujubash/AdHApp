package com.app.adhapp;

import android.net.nsd.NsdManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private NsdManager nsdManager;
    private NsdManager.RegistrationListener registrationListener;
    private NsdManager.DiscoveryListener discoveryListener;
    private Object nsdHelper;

    public static void main(String[] args) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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