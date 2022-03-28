package com.app.adhapp.NSD;

import java.util.ArrayList;

public interface SDPEngine {
    /**
     * Die Komponente benutzt die Formate und werden ggf. konvertiert.
     * Der Format wird als Servicebeschreibung genutzt und baut eine Verbindung auf.
     * Nach dem Aufruf aber, bietet sich die Komponente anderen als Service an
     * sowie erlaubt einen Verbindungsaufbau
     * Ist eine neue Verbindung entstanden, wird
     * asapConnectionHandler.handleConnection(inputStream, outputStream) gerufen.
     */
    CharSequence formats = "AAAAAAAAAAAAA";
    ArrayList path = {formats};

    /**
     * This is the Session Starter Method:
     * Here a user can create a session
     * and starts offering it directly.
     */
    void offer(CharSequence[] formats);

    /**
     * This is the Discovery Method
     * Bevor es verbunden wird: Beschreibung vom Service:
     * wenn gleiche Formate unterstützen dann auf Discovery.
     */
    void discover(CharSequence[] formats);

    /**
     * This is the Stop Discoverable Method:
     * After method call, the Session Starter
     * stops showing the connection.
     */
    void stopDiscovery(CharSequence[] formats);

    /**
     * Connection Infos:
     * Method informs about the session
     * being offered or already connected.
     */
    void getChosenServiceInfo(CharSequence[] formats);

    /**
     * This is the Tear Down method:
     * Method terminates existing session
     * being offered or not for other devices.
     */
    void tearDown(CharSequence[] formats);
}
