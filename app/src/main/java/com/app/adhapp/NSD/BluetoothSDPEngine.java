package com.app.adhapp.NSD;

import android.annotation.SuppressLint;
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
    private NsdManager nsdManager;
    private NsdManager.DiscoveryListener discServ;
    private NsdManager.RegistrationListener registrationListener;
    private final Context ctx;
    private final ASAPConnectionHandler asapConnectionHandler;
    private NsdManager.ResolveListener resolveListener;
    private NsdServiceInfo mService;
    private String serviceName = "BluetoothSDPENgine"; // TODO: add into constructor ???
    private int localPort;
    private ServerSocket serverSocket;
    private BluetoothSDPEngine discoverService;
    private ASAPConnectionHandler asapConnectionHandler1;
    Button activateDiscovery, searchDiscovery, stopDiscovery; // TODO: fehlen Buttons ???

    public BluetoothSDPEngine(BluetoothSDPEngine bluetoothSDPEngine, Object asapConnectionHandler, Context ctx, ASAPConnectionHandler asapConnectionHandler1) {
        this.ctx = ctx;
        this.asapConnectionHandler = asapConnectionHandler1;
    }

    // TODO: create constructor for BluetoothSDPEngine();

    public void registerService(int port) {
        //--------------------------- Teil 1/2: von registerService(); ---------------------------//
        NsdServiceInfo serviceInfo = new NsdServiceInfo();

        serviceInfo.setServiceName(serviceName);
        serviceInfo.setServiceType(REGISTRATION_TYPE);
        serviceInfo.setPort(port);

        //--------------------------- Teil 2/2: von registerService(); ---------------------------//
        serviceInfo = new NsdServiceInfo();
        serviceInfo.setServiceName(serviceName);
        serviceInfo.setServiceType(SERVICE_TYPE);
        serviceInfo.setPort(port);

        nsdManager = (NsdManager) this.ctx.getSystemService(Context.NSD_SERVICE);
        nsdManager.registerService(
                serviceInfo, NsdManager.PROTOCOL_DNS_SD, registrationListener);
    }

    // BluetoothSDPEngine Constructor
    public BluetoothSDPEngine(Context ctx, ASAPConnectionHandler asapConnectionHandler) {
        this.ctx = ctx;
        this.asapConnectionHandler = asapConnectionHandler;
    }

    public void onClick() {
        // TODO: change this mess!
        // TODO: GUI ???
        /*
        boolean check = true;
        do {
            choose = console.readInteger("AdHapp");
            switch (choose) {
                case 1 -> showListMenu(console).execute();
                case 2 -> showListMenu(console).execute();
                default -> System.out.println("nope");
            }
        } while (check); */
    }

    public void initializeServerSocket() throws IOException {
        // Initialize a server socket on the next available port.
        serverSocket = new ServerSocket(0);

        // Store the chosen port.
        localPort = serverSocket.getLocalPort();
    }


    public void initializeRegistrationListener() { // TODO: onStart();
        registrationListener = new NsdManager.RegistrationListener() {

            @Override
            public void onServiceRegistered(NsdServiceInfo NsdServiceInfo) {
                // Save the service name. Android may have changed it in order to
                // resolve a conflict, so update the name you initially requested
                // with the name Android actually used.
                serviceName = NsdServiceInfo.getServiceName();
            }

            @Override
            public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Registration failed! Put debugging code here to determine why.
            }

            @Override
            public void onServiceUnregistered(NsdServiceInfo arg0) {
                // Service has been unregistered. This only happens when you call
                // NsdManager.unregisterService() and pass in this listener.
            }

            @Override
            public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Unregistration failed. Put debugging code here to determine why.
            }
        };
    }

    public BluetoothSDPEngine discoverServices() {
        nsdManager.discoverServices(
                SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener);
        return null; // TODO: return results
    }

    public void initializeDiscoveryListener() {

        // Instantiate a new DiscoveryListener
        discoveryListener = new NsdManager.DiscoveryListener() {

            // Called as soon as service discovery begins.
            @Override
            public void onDiscoveryStarted(String regType) {
                Log.d(TAG, "Service discovery started");
            }

            @Override
            public void onServiceFound(NsdServiceInfo service) {
                // A service was found! Do something with it.
                Log.d(TAG, "Service discovery success" + service);
                if (!service.getServiceType().equals(SERVICE_TYPE)) {
                    // Service type is the string containing the protocol and
                    // transport layer for this service.
                    Log.d(TAG, "Unknown Service Type: " + service.getServiceType());
                } else if (service.getServiceName().equals(serviceName)) {
                    // The name of the service tells the user what they'd be
                    // connecting to. It could be "Bob's Chat App".
                    Log.d(TAG, "Same machine: " + serviceName);
                } else if (service.getServiceName().contains("NsdChat")){
                    nsdManager.resolveService(service, resolveListener);
                }
            }

            @Override
            public void onServiceLost(NsdServiceInfo service) {
                // When the network service is no longer available.
                // Internal bookkeeping code goes here.
                Log.e(TAG, "service lost: " + service);
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
                // Called when the resolve fails. Use the error code to debug.
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

        // new nsdManager.getSystemService(Context.NSD_SERVICE);
        // nsdManager = (NsdManager) this.getSystemService(Context.NSD_SERVICE);
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

    // TODO: richtig zu Interface anpassen --------------------------------------------------------------------//

    @Override
    public void offer(CharSequence[] formats) {

    }

    @Override
    public void discover(CharSequence[] formats) {

    }

    @Override
    public void stopOffering() {

    }

    @Override
    public void stopDiscovery() {

    }
}
