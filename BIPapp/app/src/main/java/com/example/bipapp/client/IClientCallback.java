package com.example.bipapp.client;

import org.json.JSONObject;
//TODO divide to interface for sing and for main
public interface IClientCallback {
    void showMessage(String title, String message);
    void singIn();
    void showUserInfo(JSONObject data);
}
