package com.app.adhapp.NSD;

public class connection {
    private static int localPort;

    public void setConnection(int localPort) {
        this.localPort = localPort;
    }

    public static void tearDown() {
    }

    public static int getLocalPort() {
        return localPort;
    }
}
