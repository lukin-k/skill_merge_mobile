package com.example.bipapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Project {
    private String mName;
    private String mId;
    private String mDescription;
    private User mInitiator;
    private ArrayList<User> mParticipants;
    private ArrayList<User> mVolunteer;
    private ArrayList<Skill> mSkills;
    private String mTag;

    public Project(JSONObject jsonObject) {
        mParticipants = new ArrayList<>();
        mVolunteer = new ArrayList<>();
        mSkills = new ArrayList<>();
        update(jsonObject);
    }

    //TODO test update
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
            for (int i = 0; i<jsonArray.length(); ++i){
                mParticipants.add(new User(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("project_skills");
            for (int i = 0; i<jsonArray.length(); ++i){
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
}
