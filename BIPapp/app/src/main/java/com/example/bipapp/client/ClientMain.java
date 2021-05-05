package com.example.bipapp.client;

import com.example.bipapp.api.API;
import com.example.bipapp.api.EStatusCode;
import com.example.bipapp.models.User;

import org.json.JSONException;
import org.json.JSONObject;


public class ClientMain extends Client {
    private User mUser;

    private final IClientMainCallback mClientMainCallback;

    public ClientMain(IClientMainCallback clientCallback) {
        super(clientCallback);
        mClientMainCallback = clientCallback;
    }


    protected void handleInPacket(Packet packet) throws JSONException {
        JSONObject jsonObject = packet.getJsonObject();
        EStatusCode statusCode = EStatusCode.getStatusCode(jsonObject.getString("status"));
        if (statusCode == EStatusCode.OK) {
            ETypePacket typePacket = packet.getTypePacket();
            switch (typePacket) {
                case GET_USER_INFO:
                    mUser = new User(jsonObject.getJSONObject("data"));
                    mClientMainCallback.showUserInfo();
                    return;
                case CHANGE_USER_INFO:
                    getUserInfo();
                    return;
            }
        } else {
            mClientMainCallback.showMessage("Error", jsonObject.getString("message"));
        }
    }


    public void getUserInfo(){
        mOutPackets.add(API.getUserInfo());
    }

    public void changeUserInfo(JSONObject jsonObject){
        mOutPackets.add(API.changeUserInfo(jsonObject));
    }

    public boolean isUserExist(){
        return mUser != null;
    }

    public User getUser() {
        return mUser;
    }
}
