package com.example.bipapp.client;

import android.os.Build;
import android.support.annotation.RequiresApi;

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
    private final IClientMainCallback mClientMainCallback;

    private User mUser;
    private final ArrayList<Skill> mAllSkillsType;
    private final ArrayList<String> mAllSkillsLevel;
    private final ArrayList<String> mAllProjectTags;
    private final ArrayList<Project> mFindProjects;

    private boolean mSearch;
    private JSONObject mLastSearch;


    private ClientMain(IClientMainCallback clientCallback) {
        super(clientCallback);
        mClientMainCallback = clientCallback;
        mAllSkillsType = new ArrayList<>();
        mAllSkillsLevel = new ArrayList<>();
        mAllProjectTags = new ArrayList<>();
        mFindProjects = new ArrayList<>();
        mSearch = false;
        mLastSearch = new JSONObject();
    }

    public static void createClient(IClientMainCallback clientCallback) {
        mClient = new ClientMain(clientCallback);
    }

    public static ClientMain getClient() {
        return (ClientMain) mClient;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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
                case DELETE_PROJECT:
                    getMyProjects();
                    return;
                case SEARCH_PROJECTS:
                    saveFindProjects(jsonObject.getJSONArray("data"));
                    mClientMainCallback.showSearchResult();
                    return;
                case SUBSCRIBE_PROJECT:
                case UNSUBSCRIBE_PROJECT:
                case ACCEPT_VOLUNTEER:
                case DELETE_VOLUNTEER:
                case LEAVE_PROJECT:
                case DELETE_PARTICIPANT:
                case UPDATE_PROJECT:
                    Project project = new Project(jsonObject.getJSONObject("data"));
                    showProjectInfo(project);
                    return;
                case GET_SUBSCRIBERS:
                    JSONArray jsonArraySubscribers = jsonObject.getJSONObject("data").getJSONArray("subscribers");
                    ArrayList<Project> subscribersProjects = new ArrayList<>();
                    ArrayList<User> subscribers = new ArrayList<>();
                    for (int i = 0; i < jsonArraySubscribers.length(); ++i) {
                        JSONObject jsonObjectSubscribers = jsonArraySubscribers.getJSONObject(i);
                        subscribersProjects.add(new Project(jsonObjectSubscribers.getJSONObject("project")));
                        subscribers.add(new User(jsonObjectSubscribers.getJSONObject("subscriber")));
                    }

                    JSONArray jsonArraySubscriptions = jsonObject.getJSONObject("data").getJSONArray("subscriptions");
                    ArrayList<Project> subscriptions = new ArrayList<>();
                    for (int i = 0; i < jsonArraySubscriptions.length(); ++i) {
                        subscriptions.add(new Project(jsonArraySubscriptions.getJSONObject(i)));
                    }

                    mClientMainCallback.showSubscribers(subscribersProjects, subscribers, subscriptions);
                    return;
                case UNSUBSCRIBE_SUBSCRIPTION:
                case ACCEPT_SUBSCRIBER:
                case DELETE_SUBSCRIBER:
                    getSubscribers();
                    return;
            }
        } else {
            mClientMainCallback.showMessage("Error", jsonObject.getString("message"));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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

    public void updateProject(JSONObject jsonObject) {
        mOutPackets.add(API.updateProject(jsonObject));
    }

    public void deleteProject(String projectId) {
        mOutPackets.add(API.deleteProject(projectId));
    }

    public void searchProjects(JSONObject jsonObject) {
        mSearch = true;
        mLastSearch = jsonObject;
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

    public void showProjectInfo(Project project) {
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

    public void deleteVolunteer(String projectId, String userName) {
        mOutPackets.add(API.deleteVolunteer(projectId, userName));
    }

    public void deleteParticipant(String projectId, String userName) {
        mOutPackets.add(API.deleteParticipant(projectId, userName));
    }

    public void getSubscribers() {
        mOutPackets.add(API.getSubscribers());
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

    public JSONObject getLastSearch() {
        return mLastSearch;
    }

    public void resetLastSearch() {
        mLastSearch = new JSONObject();
    }


    public void showUserEdit() {
        mClientMainCallback.showUserEdit();
    }

    public void showProjectCreate() {
        mClientMainCallback.showProjectCreate();
    }

    public void showProjectUpdate(Project project) {
        mClientMainCallback.showProjectUpdate(project);
    }

    public void unsubscribeSubscription(String projectId) {
        Packet packet = API.unsubscribeProject(projectId);
        packet.setTypePacket(ETypePacket.UNSUBSCRIBE_SUBSCRIPTION);
        mOutPackets.add(packet);
    }

    public void acceptSubscriber(String projectId, String userName) {
        Packet packet = API.acceptVolunteer(projectId, userName);
        packet.setTypePacket(ETypePacket.ACCEPT_SUBSCRIBER);
        mOutPackets.add(packet);
    }

    public void deleteSubscriber(String projectId, String userName) {
        Packet packet = API.deleteVolunteer(projectId, userName);
        packet.setTypePacket(ETypePacket.DELETE_SUBSCRIBER);
        mOutPackets.add(packet);
    }
}
