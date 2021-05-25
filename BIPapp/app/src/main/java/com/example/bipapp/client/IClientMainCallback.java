package com.example.bipapp.client;


import com.example.bipapp.models.Project;
import com.example.bipapp.models.User;

import java.util.ArrayList;

public interface IClientMainCallback extends IClientCallback {
    void showUserInfo();

    void showSearchResult();

    void showProject(Project project);

    void showUserEdit();

    void showProjectCreate();

    void showProjectUpdate(Project project);

    void showSubscribers(ArrayList<Project> subscribersProjects, ArrayList<User> subscribers, ArrayList<Project> subscriptions);
}