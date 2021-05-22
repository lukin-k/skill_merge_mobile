package com.example.bipapp.models;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Base64;

public class User {
    private String mUserName;
    private String mFullName;
    private short mAge;
    private String mBiography;
    private final ArrayList<Skill> mSkills;
    private Bitmap mPhoto;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public User(JSONObject jsonObject) {
        mSkills = new ArrayList<>();
        update(jsonObject);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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

        try {
            String photo_id = jsonObject.getString("photo_id");
            byte[] bytesPhoto = Base64.getDecoder().decode(photo_id.getBytes());
            mPhoto = BitmapFactory.decodeByteArray(bytesPhoto, 0, bytesPhoto.length);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    public Bitmap getPhoto() {
        return mPhoto;
    }

    public ArrayList<Skill> getSkills() {
        return mSkills;
    }
}
