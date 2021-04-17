package com.example.bipapp.client;

import com.example.bipapp.api.API;
import com.example.bipapp.api.EStatusCode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Client extends Thread {
    private final IClientCallback mClientCallback;
    private final List<Packet> mOutPackets;
    private boolean isRun;

    public Client(IClientCallback clientCallback) {
        isRun = true;
        mClientCallback = clientCallback;
        mOutPackets = new ArrayList<>();
        start();
    }

    @Override
    public void run() {
        //TODO send/receive, lists to send/receive packets

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
        for (int i = 0; i < mOutPackets.size(); i++) {
            try {
                Packet outPacket = mOutPackets.get(i);
                if (outPacket.getTypePacket() == ETypePacket.BAD) {
                    mClientCallback.showMessage("Error", outPacket.getJsonObject().getString("message"));
                    continue;
                }
                handleInPacket(API.sendPacket(outPacket));
            } catch (IOException | JSONException e) {
                mClientCallback.showMessage("Error", e.toString());
                e.printStackTrace();
            }
        }

        mOutPackets.clear();
    }

    private void handleInPacket(Packet packet) throws JSONException {
        JSONObject jsonObject = packet.getJsonObject();
        EStatusCode statusCode = EStatusCode.getStatusCode(jsonObject.getString("status"));
        if (statusCode == EStatusCode.OK) {
            ETypePacket typePacket = packet.getTypePacket();
            switch (typePacket) {
                case SING_IN:
                    API.setToken(jsonObject.getJSONObject("data").getString("token"));
                    mClientCallback.singIn();
                    isRun = false;
                    return;
                case SING_UP:
                    mClientCallback.showMessage("SingUp", "Ok");
                    return;
//                case BAD:
//                    mClientCallback.showError("Error", e.toString());
//                    return;
            }
        } else {
            mClientCallback.showMessage("Error", jsonObject.getString("message"));
        }
    }

    public void singUp(String login, String password) {
        mOutPackets.add(API.singUp(login, password));
    }

    public void singIn(String login, String password) {
        mOutPackets.add(API.singIn(login, password));
    }
}
