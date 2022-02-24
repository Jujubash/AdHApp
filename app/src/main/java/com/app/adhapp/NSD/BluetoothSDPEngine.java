package com.app.adhapp.NSD;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;
import android.widget.Button;

import net.sharksystem.asap.ASAPConnectionHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class BluetoothSDPEngine implements SDPEngine{
    private static final String SERVICE_TYPE = "_http._tcp.";
    private static final String REGISTRATION_TYPE = "_nsdchat._tcp";
    private static final String TAG = "NSDHelper";
    private static NsdManager.DiscoveryListener discoveryListener;
    private final ASAPConnectionHandler asapConnectionHandler;
    private final Context ctx;
    private String serviceName = "BluetoothSDPENgine"; // TODO: add into constructor ???
    private int localPort;
    private NsdManager nsdManager;
    private NsdManager.DiscoveryListener discServ;
    private NsdManager.RegistrationListener registrationListener;
    private NsdManager.ResolveListener resolveListener;
    private NsdServiceInfo mService;
    private ServerSocket serverSocket; // TODO: recherchieren, wie implementiere ich ein Socket in diesem Fall ???
    private BluetoothSDPEngine discoverService;
    private ASAPConnectionHandler asapConnectionHandler1;
    private CharSequence[] formats;
    public Button activateDiscovery, searchDiscovery, stopDiscovery; // TODO: fehlen Buttons ???

    public BluetoothSDPEngine(BluetoothSDPEngine bluetoothSDPEngine, Object asapConnectionHandler, Context ctx, ASAPConnectionHandler asapConnectionHandler1) {
        this.ctx = ctx;
        this.asapConnectionHandler = asapConnectionHandler1;
    }

    // TODO: create constructor for BluetoothSDPEngine();

    public void registerService(int port) {
        tearDown();
        initializeRegistrationListener();
        //--------------------------- Teil 1/2: von registerService(); ---------------------------//
        NsdServiceInfo serviceInfo = new NsdServiceInfo();
        serviceInfo.setPort(port);
        serviceInfo.setServiceName(serviceName); // String von Interface @SuppressLint
        serviceInfo.setServiceType(REGISTRATION_TYPE);
        nsdManager = (NsdManager) this.ctx.getSystemService(Context.NSD_SERVICE);
        nsdManager.registerService(
                serviceInfo, NsdManager.PROTOCOL_DNS_SD, registrationListener);

        //--------------------------- Teil 2/2: von registerService(); ---------------------------//
        serviceInfo = new NsdServiceInfo();
        serviceInfo.setPort(port);
        serviceInfo.setServiceName(serviceName);
        serviceInfo.setServiceType(SERVICE_TYPE);
    }

    // BluetoothSDPEngine Constructor
    public BluetoothSDPEngine(Context ctx, ASAPConnectionHandler asapConnectionHandler) {
        initializeResolveListener();
        this.ctx = ctx;
        this.asapConnectionHandler = asapConnectionHandler;
    }

    public void onClick() {
        // TODO: GUI ???
        /*
        boolean check = true;
        do {
            choose = console.readInteger("AdHapp");
            switch (choose) {
                case 1 -> showListMenu(console).execute();
                case 2 -> showListMenu(console).execute();
                default -> System.out.println("No button were chosen");
            }
        } while (check); */
    }

    public void initializeServerSocket() throws IOException {
        serverSocket = new ServerSocket(0);
        localPort = serverSocket.getLocalPort();
    }

    public void initializeRegistrationListener() { // TODO: onStart();
        registrationListener = new NsdManager.RegistrationListener() {

            @Override
            public void onServiceRegistered(NsdServiceInfo NsdServiceInfo) {
                serviceName = NsdServiceInfo.getServiceName();
                Log.d(TAG, "Service registered: " + serviceName); // TODO: change all mService for serviceName aka formats ???
            }

            @Override
            public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Registration failed: debugging code here
                Log.d(TAG, "Service registration failed: " + errorCode);
            }

            @Override
            public void onServiceUnregistered(NsdServiceInfo arg0) {
                // Unregister: call NsdManager.unregisterService() and pass in this listener.
                Log.d(TAG, "Service unregistered: " + arg0.getServiceName());
            }

            @Override
            public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Unregistration failed: debugging code here
                Log.d(TAG, "Service unregistration failed: " + errorCode);
            }
        };
    }

    public BluetoothSDPEngine discoverServices() {
        stopDiscovery(formats); // TODO: richtig so???
        initializeDiscoveryListener();
        nsdManager.discoverServices(
                SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener);
        return null; // TODO: return results
    }

    public void initializeDiscoveryListener() {
        discoveryListener = new NsdManager.DiscoveryListener() {

            @Override
            public void onDiscoveryStarted(String regType) {
                Log.d(TAG, "initializeDiscoveryListener(): Service discovery started");
            }

            @Override
            public void onServiceFound(NsdServiceInfo service) {
                // A service was found! Do something with it.
                Log.d(TAG, "Service discovery success" + service);
                if (!service.getServiceType().equals(SERVICE_TYPE)) {
                    Log.d(TAG, "Unknown Service Type: " + service.getServiceType());
                } else if (service.getServiceName().equals(serviceName)) {
                    Log.d(TAG, "Same machine: " + serviceName);
                } else if (service.getServiceName().contains("NsdChat")){
                    nsdManager.resolveService(service, resolveListener);
                }
            }

            @Override
            public void onServiceLost(NsdServiceInfo service) {
                if (mService == service) {
                    mService = null;
                }
                Log.e(TAG, "Service lost: " + service);
            }

            @Override
            public void onDiscoveryStopped(String serviceType) {
                Log.i(TAG, "Discovery stopped: " + serviceType);
            }

            @Override
            public void onStartDiscoveryFailed(String serviceType, int errorCode) {
                Log.e(TAG, "Discovery failed: Error code:" + errorCode);
                nsdManager.stopServiceDiscovery(this);
            }

            @Override
            public void onStopDiscoveryFailed(String serviceType, int errorCode) {
                Log.e(TAG, "Discovery failed: Error code:" + errorCode);
                nsdManager.stopServiceDiscovery(this);
            }
        };
    }

        public void initializeResolveListener() {
        resolveListener = new NsdManager.ResolveListener() {

            @Override
            public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Call when resolve fails: errorCode for debugging
                Log.e(TAG, "Resolve failed: " + errorCode);
            }

            @Override
            public void onServiceResolved(NsdServiceInfo serviceInfo) {
                Log.e(TAG, "Resolve Succeeded. " + serviceInfo);

                if (serviceInfo.getServiceName().equals(serviceName)) {
                    Log.d(TAG, "Same IP.");
                    return;
                }
                mService = serviceInfo;
                int port = mService.getPort();
                InetAddress host = mService.getHost();
            }
        };
    }

    public void onCreate() {
        new BluetoothSDPEngine(this, null, ctx, asapConnectionHandler1);
        discoverService = this.discoverServices();
    }

    public void onPause() {
        this.tearDown();
    }

    public void onResume() {
        this.registerService(connection.getLocalPort());
        this.discoverServices();
    }

    public void onDestroy() {
        this.tearDown();
        connection.tearDown();
    }

    public void tearDown() {
        nsdManager.unregisterService(registrationListener);
        nsdManager.stopServiceDiscovery(discoveryListener);
    }

    @Override
    public void offer(CharSequence[] formats) {

    }

    @Override
    public void discover(CharSequence[] formats) {
        nsdManager.discoverServices( this, null);
    }

    @Override
    public void stopDiscovery(CharSequence[] formats) {
        if (discoveryListener != null) {
            try {
                nsdManager.stopServiceDiscovery(discoveryListener);
            } finally {
            }
            discoveryListener = null;
        }
    }

    @Override
    public void getChosenServiceInfo(CharSequence[] formats) {
        // return serviceName; // TODO: return Service Beschreibung
    }
}
