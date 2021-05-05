package com.example.bipapp.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Project {
    private String mProjectName;
    private long mProjectId;
    private String mProjectDescription;
//TODO set suitable types
//    private int mInitiator;
//    private int mParticipants;
//    private int mVolunteer;
//    private int mProjectSkills;
    private String mProjectTags;

    public Project(JSONObject jsonObject) {
        update(jsonObject);
    }

    public void update(JSONObject jsonObject) {
        try {
            mProjectName = jsonObject.getString("project_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            mProjectId = jsonObject.getLong("project_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            mProjectDescription = jsonObject.getString("project_description");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            mProjectTags = jsonObject.getString("project_tags");
        } catch (JSONException e) {
            e.printStackTrace();
        }

//TODO mInitiator
//mParticipants
//mVolunteer
//mProjectSkills
    }
}
