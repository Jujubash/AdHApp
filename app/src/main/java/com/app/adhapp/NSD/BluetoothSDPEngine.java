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
    private CharSequence[] formats; // TODO: Formats anwenden

    public BluetoothSDPEngine(BluetoothSDPEngine bluetoothSDPEngine, Object asapConnectionHandler, Context ctx, ASAPConnectionHandler asapConnectionHandler1) {
        this.ctx = ctx;
        this.asapConnectionHandler = asapConnectionHandler1;
    }

    public void registerService(int port) {
        tearDown();
        initializeRegistrationListener();

        /////////////////////////////////////////////////////////////////////////////////////
        //                               Register Service                                  //
        /////////////////////////////////////////////////////////////////////////////////////

        /**
         * Teil 1/2 von registerService:
         * register your service on the local network, creating a NsdServiceInfo object.
         * This object provides the information that other devices on the network use when
         * they're deciding whether to connect to your service.
         */
        NsdServiceInfo serviceInfo = new NsdServiceInfo();
        serviceInfo.setPort(port);
        serviceInfo.setServiceName(serviceName);   // TODO: Formats anwenden
        serviceInfo.setServiceType(SERVICE_TYPE);  // TODO: zwischen TCP UDP entscheiden lassen
        nsdManager = (NsdManager) this.ctx.getSystemService(Context.NSD_SERVICE);
        nsdManager.registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, registrationListener);
    }

    /////////////////////////////////////////////////////////////////////////////////////
    //                           BluetoothSDPEngine Constructor                        //
    /////////////////////////////////////////////////////////////////////////////////////

    /**
     *
     *
     */
    public BluetoothSDPEngine(Context ctx, ASAPConnectionHandler asapConnectionHandler) {
        initializeResolveListener();
        this.ctx = ctx;
        this.asapConnectionHandler = asapConnectionHandler;
    }

    /**
     * setting the port for the service:
     * avoid conflicts by setting the port to 0
     * to initialize a socket to any available port.
     */
    public void initializeServerSocket() throws IOException {
        serverSocket = new ServerSocket(0);
        localPort = serverSocket.getLocalPort();
    }

    /**
     *
     *
     */
    public void initializeRegistrationListener() { // TODO: onStart();
        registrationListener = new NsdManager.RegistrationListener() {

            @Override
            public void onServiceRegistered(NsdServiceInfo NsdServiceInfo) {
                serviceName = NsdServiceInfo.getServiceName();
                Log.d(TAG, "Service registered: " + serviceName); // serviceName same as mService
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

    /**
     *
     *
     */
    public BluetoothSDPEngine discoverServices() {
        stopDiscovery(formats); // TODO: richtig so???
        initializeDiscoveryListener();
        nsdManager.discoverServices(
                SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener);
        return null; // TODO: return results
    }

    /**
     * Service discovery has two steps:
     * 1. Setting up a discovery listener with the relevant callbacks
     * 2. Making a single asynchronous API call to discoverServices()
     *
     * What the snippet in the IF Statement does:
     * 1. The service name of the found service is compared to the service name of the local service
     *    to determine if the device just picked up its own broadcast (which is valid).
     * 2. Service type is checked, to verify it's a type of service your application can connect to.
     * 3. The service name is checked to verify connection to the correct application.
     */
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

    /**
     *
     *
     */
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

    /**
     * Activities calling methods from Lifecycles:
     * 1. MainActivity: menu for users
     * 2. SessionStarter: user can create session
     * 3. SessionSearcher: user searches for existing session
     */
    public void onCreate() {
        new BluetoothSDPEngine(this, null, ctx, asapConnectionHandler1);
        discoverService = this.discoverServices();
        initializeRegistrationListener();
        initializeResolveListener();
    }

    public void onPause() {
        this.tearDown();
    }

    public void onResume() {
        this.registerService(Connection.getLocalPort());
        this.discoverServices();
    }

    public void onDestroy() {
        this.tearDown();
        Connection.tearDown();
    }

    public void tearDown() {
        nsdManager.unregisterService(registrationListener);
        nsdManager.stopServiceDiscovery(discoveryListener);
    }

    /**
     *
     *
     */
    @Override
    public void offer(CharSequence[] formats) {

    }

    /**
     *
     *
     */
    /*
    @Override
    public void discover(CharSequence[] formats) {
        nsdManager.discoverServices( this, null);
    }
     */ // TODO: fix this

    /**
     *
     *
     */
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

    /**
     *
     *
     */
    @Override
    public void getChosenServiceInfo(CharSequence[] formats) {
        // return serviceName; // TODO: return Service (Beschreibung)
    }
}
