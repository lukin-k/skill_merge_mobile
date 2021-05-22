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
    private final ArrayList<Skill> mAllSkillsType;
    private final ArrayList<String> mAllSkillsLevel;
    private final ArrayList<String> mAllProjectTags;
    private final ArrayList<Project> mFindProjects;

    private boolean mSearch;


    private final IClientMainCallback mClientMainCallback;

    private ClientMain(IClientMainCallback clientCallback) {
        super(clientCallback);
        mClientMainCallback = clientCallback;
        mAllSkillsType = new ArrayList<>();
        mAllSkillsLevel = new ArrayList<>();
        mAllProjectTags = new ArrayList<>();
        mFindProjects = new ArrayList<>();
        mSearch = false;
    }

    public static void createClient(IClientMainCallback clientCallback) {
        mClient = new ClientMain(clientCallback);
    }

    public static ClientMain getClient() {
        return (ClientMain) mClient;
    }

    protected void handleInPacket(Packet packet) throws JSONException {
        JSONObject jsonObject = packet.getJsonObject();
        EStatusCode statusCode = EStatusCode.getStatusCode(jsonObject.getString("status"));
        if (statusCode == EStatusCode.OK) {
            ETypePacket typePacket = packet.getTypePacket();
            switch (typePacket) {
                case GET_ALL_SKILLS:
                    saveAllSkills(jsonObject.getJSONArray("data"));
//                    Log.v("json skills", jsonObject.toString());
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
                    getMyProjects();
                    return;
                case SEARCH_PROJECTS:
                    saveFindProjects(jsonObject.getJSONArray("data"));
                    mClientMainCallback.showSearchResult();
                    return;
                case SUBSCRIBE_PROJECT:
                case UNSUBSCRIBE_PROJECT:
                case ACCEPT_VOLUNTEER:
                case LEAVE_PROJECT:
                    Project project = new Project(jsonObject.getJSONObject("data"));
                    getProjectInfo(project);
                    return;
            }
        } else {
            mClientMainCallback.showMessage("Error", jsonObject.getString("message"));
        }
    }

    private void saveFindProjects(JSONArray projects) {
        mFindProjects.clear();
        ArrayList<Project> participantProjects = new ArrayList<>();
        String username = mUser.getUserName();
        for (int i = 0; i < projects.length(); ++i) {
            try {
                Project project = new Project(projects.getJSONObject(i));
                if (username.equals(project.getInitiator().getUserName())) {
                    mFindProjects.add(project);
                } else {
                    participantProjects.add(project);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        mFindProjects.addAll(participantProjects);
    }

    private void saveAllSkills(JSONArray skills) {
        ArrayList<String> allSkillsType = new ArrayList<>();
        for (int i = 0; i < skills.length(); ++i) {
            try {
                String skillType = skills.getJSONObject(i).getString("skill_type");
                if (allSkillsType.contains(skillType)) {
                    break;
                }
                allSkillsType.add(skillType);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        mAllSkillsLevel.clear();
        int countSkills = allSkillsType.size();
        for (int i = 0; i < skills.length(); i += countSkills) {
            try {
                String skillLevel = skills.getJSONObject(i).getString("skill_level");
                mAllSkillsLevel.add(skillLevel);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        mAllSkillsType.clear();
        for (int i = 0; i < allSkillsType.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("skill_type", allSkillsType.get(i));
                jsonObject.put("skill_level", "");
                mAllSkillsType.add(new Skill(jsonObject));
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
        ArrayList<Skill> copyAllSkills = new ArrayList<>();
        for (int i = 0; i < mAllSkillsType.size(); i++) {
            copyAllSkills.add(new Skill(mAllSkillsType.get(i)));
        }
        return copyAllSkills;
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
        mSearch = true;
        mOutPackets.add(API.searchProjects(jsonObject));
    }

    public void getMyProjects() {
        JSONObject jsonObjectInitiator = new JSONObject();
        try {
            jsonObjectInitiator.put("participant", mUser.getUserName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Packet packet = API.searchProjects(jsonObjectInitiator);
        mOutPackets.add(packet);
    }

    public void getProjectInfo(Project project) {
        mClientMainCallback.showProject(project);
    }

    public void subscribeProject(String projectId) {
        mOutPackets.add(API.subscribeProject(projectId));
    }

    public void unsubscribeProject(String projectId) {
        mOutPackets.add(API.unsubscribeProject(projectId));
    }

    public void acceptVolunteer(String projectId, String userName) {
        mOutPackets.add(API.acceptVolunteer(projectId, userName));
    }

    public void leaveProject(String projectId) {
        mOutPackets.add(API.leaveProject(projectId));
    }

    public boolean isUserExist() {
        return mUser != null;
    }

    public User getUser() {
        return mUser;
    }

    public ArrayList<String> getAllSkillsLevel() {
        return mAllSkillsLevel;
    }

    public boolean isSearch() {
        boolean tmp = mSearch;
        mSearch = false;
        return tmp;
    }


}
