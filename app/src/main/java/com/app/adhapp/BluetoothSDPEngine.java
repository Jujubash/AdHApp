package com.app.adhapp;

import net.sharksystem.asap.ASAPConnectionHandler;
import net.sharksystem.asap.ASAPException;
import net.sharksystem.asap.EncounterConnectionType;
import net.sharksystem.asap.protocol.ASAPConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

public class BluetoothSDPEngine implements ASAPConnectionHandler {
    @Override
    public ASAPConnection handleConnection(InputStream is, OutputStream os, boolean encrypt, boolean sign, Set<CharSequence> appsWhiteList, Set<CharSequence> appsBlackList) throws IOException, ASAPException {
        return null; // TODO: return expected value
    }

    @Override
    public ASAPConnection handleConnection(InputStream is, OutputStream os, boolean encrypt, boolean sign, EncounterConnectionType connectionType, Set<CharSequence> appsWhiteList, Set<CharSequence> appsBlackList) throws IOException, ASAPException {
        return null; // TODO: return expected value
    }

    @Override
    public ASAPConnection handleConnection(InputStream is, OutputStream os) throws IOException, ASAPException {
        return null; // TODO: return expected value
    }

    @Override
    public ASAPConnection handleConnection(InputStream inputStream, OutputStream outputStream, EncounterConnectionType connectionType) throws IOException, ASAPException {
        return null; // TODO: return expected value
    }
}
