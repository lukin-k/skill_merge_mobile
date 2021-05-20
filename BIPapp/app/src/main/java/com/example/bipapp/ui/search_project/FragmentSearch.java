package com.example.bipapp.ui.search_project;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.bipapp.R;
import com.example.bipapp.client.ClientMain;

//TODO back onece request exit? second go to singin activity
public class FragmentSearch extends Fragment {
    private ClientMain mClient;
    private FragmentSearchProjects mFragmentSearchProjects;
    private FragmentManager mFragmentManager;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        mClient = ClientMain.getClient();


        mFragmentSearchProjects = new FragmentSearchProjects();
        mFragmentManager = getChildFragmentManager();

        mFragmentManager.beginTransaction()
                .add(R.id.frame_container_search, mFragmentSearchProjects)
                .commit();
        return view;
    }
}
