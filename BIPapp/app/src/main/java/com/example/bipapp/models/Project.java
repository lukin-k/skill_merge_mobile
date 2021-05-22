package com.example.bipapp.models;

import android.os.Build;
import android.support.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Project {
    private String mName;
    private String mId;
    private String mDescription;
    private User mInitiator;
    private final ArrayList<User> mParticipants;
    private final ArrayList<User> mVolunteer;
    private final ArrayList<Skill> mSkills;
    private String mTag;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Project(JSONObject jsonObject) {
        mParticipants = new ArrayList<>();
        mVolunteer = new ArrayList<>();
        mSkills = new ArrayList<>();
        update(jsonObject);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void update(JSONObject jsonObject) {
        try {
            mName = jsonObject.getString("project_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            mId = jsonObject.getString("project_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            mDescription = jsonObject.getString("project_description");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            mInitiator = new User(jsonObject.getJSONObject("initiator"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("participants");
            for (int i = 0; i < jsonArray.length(); ++i) {
                mParticipants.add(new User(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("volunteer");
            for (int i = 0; i < jsonArray.length(); ++i) {
                mVolunteer.add(new User(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("project_skills");
            for (int i = 0; i < jsonArray.length(); ++i) {
                mSkills.add(new Skill(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            mTag = jsonObject.getString("project_tags");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return mName;
    }

    public String getId() {
        return mId;
    }

    public String getDescription() {
        return mDescription;
    }

    public User getInitiator() {
        return mInitiator;
    }

    public ArrayList<User> getParticipants() {
        return mParticipants;
    }

    public ArrayList<User> getVolunteer() {
        return mVolunteer;
    }

    public ArrayList<Skill> getSkills() {
        return mSkills;
    }

    public String getTag() {
        return mTag;
    }
}
