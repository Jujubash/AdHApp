package com.app.adhapp.NSD;

public interface SDPEngine {
    /**
     Die Komponente benutzt die Formate und werden ggf. konvertiert.
     Der Format wird als Servicebeschreibung genutzt.
     Nach dem Aufruf aber, bietet sich die Komponente anderen als Service an
     sowie erlaubt einen Verbindungsaufbau
     */
    CharSequence formats = "AAAAAAAAAAAAA";

    void offer(CharSequence[] formats);

    /** Die Komponenten sucht nun nach passenden Services.
     * Sie baut eine Verbindung auf.
     * Ist eine neue Verbindung entstanden, wird
     * asapConnectionHandler.handleConnection(inputStream, outputStream) gerufen.
     */

    /*
    void discover(CharSequence[] formats); // Bevor es verbunden wird: Beschreibung vom Service:
                                           // wenn gleiche Formate unterstützen dann auf Discovery

     */
    void stopDiscovery(CharSequence[] formats); // discovery stoppen // TODO: implementieren

    void getChosenServiceInfo(CharSequence[] formats);
}
