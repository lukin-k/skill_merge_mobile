package com.example.bipapp.client;

import android.util.Log;

import com.example.bipapp.api.API;
import com.example.bipapp.api.EStatusCode;
import com.example.bipapp.models.Project;
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
    private ArrayList<String> mAllProjectTags;
    private ArrayList<Project> mFindProjects;

    private final IClientMainCallback mClientMainCallback;

    public ClientMain(IClientMainCallback clientCallback) {
        super(clientCallback);
        mClientMainCallback = clientCallback;
        mAllSkills = new ArrayList<>();
        mAllProjectTags = new ArrayList<>();
        mFindProjects = new ArrayList<>();
    }


    protected void handleInPacket(Packet packet) throws JSONException {
        JSONObject jsonObject = packet.getJsonObject();
        EStatusCode statusCode = EStatusCode.getStatusCode(jsonObject.getString("status"));
        if (statusCode == EStatusCode.OK) {
            ETypePacket typePacket = packet.getTypePacket();
            switch (typePacket) {
                case GET_ALL_SKILLS:
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
                case GET_ALL_PROJECT_TAGS:
                    saveAllProjectTags(jsonObject.getJSONObject("data").getJSONArray("tags"));
                    return;
                case CREATE_PROJECT:
//                    mClientMainCallback.showMyProjects();
                    JSONObject jsonObjectInitiator = new JSONObject();
                    try {
                        jsonObjectInitiator.put("initiator", mUser.getUserName());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    getMyProjects(jsonObjectInitiator);
                    return;
                case GET_MY_PROJECTS:
                    Log.v(TAG, "GET_MY_PROJECTS " + jsonObject.toString());
                    saveFindProjects(jsonObject.getJSONArray("data"));
                    mClientMainCallback.showMyProjects();
                    return;
                case SEARCH_PROJECTS:
                    Log.v(TAG, "SEARCH_PROJECTS " + jsonObject.toString());
                    saveFindProjects(jsonObject.getJSONArray("data"));
                    mClientMainCallback.showSearchResult();
                    return;
            }
        } else {
            mClientMainCallback.showMessage("Error", jsonObject.getString("message"));
        }
    }

    private void saveFindProjects(JSONArray projects) {
        mFindProjects.clear();
        for (int i = 0; i < projects.length(); ++i) {
            try {
                mFindProjects.add(new Project(projects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
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

    private void saveAllProjectTags(JSONArray tags) {
        mAllProjectTags.clear();
        for (int i = 0; i < tags.length(); ++i) {
            try {
                mAllProjectTags.add(tags.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void getAllSkillsRequest() {
        mOutPackets.add(API.getAllSkills());
    }

    public ArrayList<Skill> getAllSkillsList() {
        return mAllSkills;
    }

    public void getAllProjectTagsRequest() {
        mOutPackets.add(API.getAllProjectTags());
    }

    public ArrayList<String> getAllProjectTagsList() {
        return mAllProjectTags;
    }

    public ArrayList<Project> getFindProjects() {
        return mFindProjects;
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

    public void searchProjects(JSONObject jsonObject) {
        mOutPackets.add(API.searchProject(jsonObject));
    }

    public void getMyProjects(JSONObject jsonObject) {
        Packet packet = API.searchProject(jsonObject);
        packet.setTypePacket(ETypePacket.GET_MY_PROJECTS);
        mOutPackets.add(packet);
    }

    public boolean isUserExist() {
        return mUser != null;
    }

    public User getUser() {
        return mUser;
    }

}
