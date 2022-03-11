package com.app.adhapp.NSD;

public interface SDPEngine {
    /**
     Die Komponente benutzt die Formate.
     Sie werden ggf. konvertiert.
     Diese Format werde aber als Servicebeschreibung genutzt.
     Wenn das nicht geht, lassen Sie uns darüber diskutieren.
     Nach dem Aufruf aber, bietet sich die Komponente anderen als Service an
     und erlaubt einen Verbindungsaufbau
     */
    void offer(CharSequence[] formats); // TODO: komplett löschen ???

    /** Die Komponenten sucht nun nach passenden Services.
     * Sie baut eine Verbindung auf.
     * Ist eine neue Verbindung entstanden, rufen Sie
     asapConnectionHandler.handleConnection(inputStream, outputStream) auf.
     In einer kleinere Demoimplementierung zeigen Sie, dass das funktioniert.
     */

    /*
    void discover(CharSequence[] formats); // Bevor es verbunden wird: Beschreibung vom Service:
                                           // wenn gleiche Formate unterstützen dann auf Discovery

     */
    void stopDiscovery(CharSequence[] formats); // discovery stoppen // TODO: implementieren

    void getChosenServiceInfo(CharSequence[] formats); // TODO: Button, um Beschreibung vom Service String zu sehen
                                           // TODO: ??? es könnte dann auch später ein QR-Code Sharing implementiert werden
}
