package com.example.bipapp.client;


import com.example.bipapp.models.Project;

public interface IClientMainCallback extends IClientCallback {
    void showUserInfo();

    void showSearchResult();

    void showProject(Project project);

    void showUserEdit();

    void showProjectCreate();

    void showProjectUpdate(Project project);
}
