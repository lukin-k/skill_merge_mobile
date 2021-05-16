package com.example.bipapp.ui.search_project;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bipapp.R;
import com.example.bipapp.client.ClientMain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FragmentSearchProjects extends Fragment {
    private ClientMain mClient;

    public void setClient(ClientMain client) {
        mClient = client;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_projects, container, false);
        return view;
    }

    boolean b = false;

    @Override
    public void onResume() {
        super.onResume();
        if(b){
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();
            JSONObject skill = new JSONObject();
            skill.put("skill_type", "C++");
            skill.put("skill_level", "");
            jsonArray.put(skill);
            jsonObject.put("skills", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        b = true;
        mClient.searchProjects(jsonObject);
    }
}