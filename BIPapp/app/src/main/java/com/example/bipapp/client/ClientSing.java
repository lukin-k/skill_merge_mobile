package com.example.bipapp.client;


import com.example.bipapp.api.API;
import com.example.bipapp.api.EStatusCode;

import org.json.JSONException;
import org.json.JSONObject;


public class ClientSing extends Client {
    private final IClientSingCallback mClientSingCallback;


    public ClientSing(IClientSingCallback clientCallback) {
        super(clientCallback);
        mClientSingCallback = clientCallback;
    }


    protected void handleInPacket(Packet packet) throws JSONException {
        JSONObject jsonObject = packet.getJsonObject();
        EStatusCode statusCode = EStatusCode.getStatusCode(jsonObject.getString("status"));
        if (statusCode == EStatusCode.OK) {
            ETypePacket typePacket = packet.getTypePacket();
            switch (typePacket) {
                case SING_IN:
                    API.setToken(jsonObject.getJSONObject("data").getString("token"));
                    mClientSingCallback.singIn();
                    isRun = false;
                    return;
                case SING_UP:
                    mClientSingCallback.singUp();
                    return;
            }
        } else {
            mClientSingCallback.showMessage("Error", jsonObject.getString("message"));
        }
    }

    public void singUp(String login, String password) {
        mOutPackets.add(API.singUp(login, password));
    }

    public void singIn(String login, String password) {
        mOutPackets.add(API.singIn(login, password));
    }
}
