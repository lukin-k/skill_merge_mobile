package com.example.bipapp.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class User {
    private String mUserName;
    private String mFullName;
    private short mAge;
    private String mBiography;
    private ArrayList<String> mUserSkills;
    //TODO set suitable type for photo
    private byte[] mPhoto;

    public User(JSONObject jsonObject) {
        update(jsonObject);
    }

    public void update(JSONObject jsonObject) {
        try {
            mUserName = jsonObject.getString("username");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            mFullName = jsonObject.getString("fullname");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            mAge = (short) jsonObject.getInt("age");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            mBiography = jsonObject.getString("biography");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//TODO            mUserSkills = jsonObject.getString("user_skills");
//TODO            mPhoto = jsonObject.getString("photo_id");
    }

    public String getFullName() {
        return mFullName;
    }

    public short getAge() {
        return mAge;
    }

    public String getBiography() {
        return mBiography;
    }

    public ArrayList<String> getUserSkills() {
        return mUserSkills;
    }
}
