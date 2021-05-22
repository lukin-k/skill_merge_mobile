package com.example.bipapp.client;


import com.example.bipapp.api.API;
import com.example.bipapp.api.EStatusCode;

import org.json.JSONException;
import org.json.JSONObject;


public class ClientSign extends Client {
    private final IClientSignCallback mClientSignCallback;


    private ClientSign(IClientSignCallback clientCallback) {
        super(clientCallback);
        mClientSignCallback = clientCallback;
    }


    public static void createClient(IClientSignCallback clientCallback) {
        mClient = new ClientSign(clientCallback);
    }

    public static ClientSign getClient() throws Exception {
        if (mClient == null) {
            throw new Exception("No IClientSingCallback in ClientSing");
        }
        return (ClientSign) mClient;
    }

    protected void handleInPacket(Packet packet) throws JSONException {
        JSONObject jsonObject = packet.getJsonObject();
        EStatusCode statusCode = EStatusCode.getStatusCode(jsonObject.getString("status"));
        if (statusCode == EStatusCode.OK) {
            ETypePacket typePacket = packet.getTypePacket();
            switch (typePacket) {
                case SIGN_IN:
                    API.setToken(jsonObject.getJSONObject("data").getString("token"));
                    mClientSignCallback.signIn();
                    isRun = false;
                    return;
                case SIGN_UP:
                    mClientSignCallback.signUp();
                    return;
            }
        } else {
            mClientSignCallback.showMessage("Error", jsonObject.getString("message"));
        }
    }

    public void signUp(String login, String password) {
        mOutPackets.add(API.signUp(login, password));
    }

    public void signIn(String login, String password) {
        mOutPackets.add(API.signIn(login, password));
    }
}
