package com.example.bipapp.client;


import com.example.bipapp.models.Project;

public interface IClientMainCallback extends IClientCallback {
    void showUserInfo();

    void showSearchResult();

    void showProject(Project project);
}
