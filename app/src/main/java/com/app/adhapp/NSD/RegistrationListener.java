package com.app.adhapp.NSD;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;

public interface RegistrationListener {
     default void initializeRegistrationListener() {
         // Save the service name. Android may have changed it in order to
         // resolve a conflict, so update the name you initially requested
         // with the name Android actually used.
         // Registration failed! Put debugging code here to determine why.
         // Service has been unregistered. This only happens when you call
         // NsdManager.unregisterService() and pass in this listener.
         // Unregistration failed. Put debugging code here to determine why.
         NsdManager.RegistrationListener registrationListener = new NsdManager.RegistrationListener() {
             String serviceName;

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
}
