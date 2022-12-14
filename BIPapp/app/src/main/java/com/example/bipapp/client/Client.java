package com.example.bipapp.client;

import com.example.bipapp.api.API;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public abstract class Client extends Thread {
    private final IClientCallback mClientCallback;
    protected final List<Packet> mOutPackets;
    protected boolean isRun;

    protected static Client mClient = null;

    protected Client(IClientCallback clientCallback) {
        isRun = true;
        mClientCallback = clientCallback;
        mOutPackets = Collections.synchronizedList(new ArrayList<Packet>());
        start();
    }

    @Override
    public void run() {
        while (isRun) {
            sendAndHandlePackets();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendAndHandlePackets() {
        synchronized (mOutPackets) {
            int i = mOutPackets.size() - 1;
            for (; i >= 0; --i) {
                try {
                    Packet outPacket = mOutPackets.get(i);
                    if (outPacket.getTypePacket() == ETypePacket.BAD) {
                        mClientCallback.showMessage("Error", outPacket.getJsonObject().getString("message"));
                        continue;
                    }
                    handleInPacket(API.sendPacket(outPacket));
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    mClientCallback.showMessage("Error", e.toString());
                }
                mOutPackets.remove(i);
            }
        }
    }

    public void stopClient() {
        isRun = false;
    }

    abstract protected void handleInPacket(Packet packet) throws JSONException;
}
