package com.example.bipapp.client;

import android.util.Log;

import com.example.bipapp.api.API;
import com.example.bipapp.api.EStatusCode;
import com.example.bipapp.models.Skill;
import com.example.bipapp.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ClientMain extends Client {
    private final static String TAG = "ClientMain";

    private User mUser;
    private ArrayList<Skill> mAllSkills;

    private final IClientMainCallback mClientMainCallback;

    public ClientMain(IClientMainCallback clientCallback) {
        super(clientCallback);
        mClientMainCallback = clientCallback;
        mAllSkills = new ArrayList<>();
    }


    protected void handleInPacket(Packet packet) throws JSONException {
        JSONObject jsonObject = packet.getJsonObject();
        EStatusCode statusCode = EStatusCode.getStatusCode(jsonObject.getString("status"));
        if (statusCode == EStatusCode.OK) {
            ETypePacket typePacket = packet.getTypePacket();
            switch (typePacket) {
                case GET_ALL_SKILLS:
                    Log.v(TAG, "Skills " + jsonObject.toString());
                    saveAllSkills(jsonObject.getJSONArray("data"));
                    return;
                case GET_USER_INFO:
                    Log.v(TAG, "GET_USER_INFO " + jsonObject.toString());
                    mUser = new User(jsonObject.getJSONObject("data"));
                    mClientMainCallback.showUserInfo();
                    return;
                case CHANGE_USER_INFO:
                    getUserInfo();
                    return;
                case CREATE_PROJECT:
                    //TODO do something
                    Log.v(TAG, jsonObject.toString());
                    Log.v(TAG, jsonObject.getJSONObject("data").toString());
                    return;
            }
        } else {
            mClientMainCallback.showMessage("Error", jsonObject.getString("message"));
        }
    }

    private void saveAllSkills(JSONArray skills) {
        mAllSkills.clear();
        for (int i = 0; i < skills.length(); ++i) {
            try {
                mAllSkills.add(new Skill(skills.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void getAllSkillsRequest() {
//        Log.v(TAG, "getAllSkills");
        mOutPackets.add(API.getAllSkills());
    }

    public ArrayList<Skill> getAllSkillsList() {
        return mAllSkills;
    }

    public void getUserInfo() {
        mOutPackets.add(API.getUserInfo());
    }

    public void changeUserInfo(JSONObject jsonObject) {
        mOutPackets.add(API.changeUserInfo(jsonObject));
    }

    public void createProject(JSONObject jsonObject) {
        mOutPackets.add(API.createProject(jsonObject));
    }

    public boolean isUserExist() {
        return mUser != null;
    }

    public User getUser() {
        return mUser;
    }

}
