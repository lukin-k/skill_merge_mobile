package com.example.bipapp.models;


import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class User {
    private String mUserName;
    private String mFullName;
    private short mAge;
    private String mBiography;
    private ArrayList<Skill> mSkills;
    private Bitmap mPhoto;

    public User(JSONObject jsonObject) {
        mSkills = new ArrayList<>();
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

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("user_skills");
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject map = jsonArray.getJSONObject(i);
                mSkills.add(new Skill(map));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //TODO mPhoto = jsonObject.getString("photo_id");
    }

    public String getUserName() {
        return mUserName;
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

    public Bitmap getPhoto() { return mPhoto; }

    public void setPhoto(Bitmap photo) { mPhoto = photo; }

    public ArrayList<Skill> getSkills() {
        return mSkills;
    }
}
