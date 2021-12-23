package com.app.adhapp.NSD;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;

import java.io.IOException;
import java.net.ServerSocket;

public class registerService implements RegistrationListener {
    private ServerSocket serverSocket;
    private int localPort;
    private NsdManager nsdManager;
    private NsdManager.RegistrationListener registrationListener;

    public void registerService(int port) {
        // Create the NsdServiceInfo object, and populate it.
        NsdServiceInfo serviceInfo = new NsdServiceInfo();

        // The name is subject to change based on conflicts
        // with other services advertised on the same network.
        serviceInfo.setServiceName("AdHapp");
        serviceInfo.setServiceType("_nsdchat._tcp");
        serviceInfo.setPort(port);

        // This method is asynchronous, so any code that needs to
        // run after the service has been registered must go here.
        nsdManager = Context.getSystemService(Context.NSD_SERVICE); // TODO: fix this getSystemService()
        nsdManager.registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, registrationListener);
    }

    public void initializeServerSocket() throws IOException {
        // Initialize a server socket on the next available port.
        serverSocket = new ServerSocket(0);

        // Store the chosen port.
        localPort = serverSocket.getLocalPort();
    // ...
    }
}
