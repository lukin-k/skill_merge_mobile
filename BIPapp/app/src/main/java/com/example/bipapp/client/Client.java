package com.example.bipapp.client;

import android.util.Log;

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

    //TODO divide to interface for sing and for main

    public Client(IClientCallback clientCallback) {
        isRun = true;
        mClientCallback = clientCallback;
        mOutPackets = new ArrayList<>();
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
        int i = mOutPackets.size() -1;

        for (; i >= 0; --i) {
            try {
                Packet outPacket = mOutPackets.get(i);
                if (outPacket.getTypePacket() == ETypePacket.BAD) {
                    mClientCallback.showMessage("Error", outPacket.getJsonObject().getString("message"));
                    continue;
                }
                handleInPacket(API.sendPacket(outPacket));
                mOutPackets.remove(i);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                mClientCallback.showMessage("Error", e.toString());
            }
        }

        mOutPackets.clear();
    }

    private void handleInPacket(Packet packet) throws JSONException {
        JSONObject jsonObject = packet.getJsonObject();
        Log.v("Test", jsonObject.toString());
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
                case GET_USER_INFO:
                    mClientCallback.showUserInfo(jsonObject.getJSONObject("data"));
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

    public void getUserInfo(){
        mOutPackets.add(API.getUserInfo());
    }
}
