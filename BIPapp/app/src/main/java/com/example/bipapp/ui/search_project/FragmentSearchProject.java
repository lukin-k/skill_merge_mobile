package com.example.bipapp.ui.search_project;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.bipapp.MainActivity;
import com.example.bipapp.R;
import com.example.bipapp.client.ClientMain;
import com.example.bipapp.models.Project;
import com.example.bipapp.models.Skill;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//TODO implentation search

public class FragmentSearchProject extends Fragment {
    private ClientMain mClient;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_project, container, false);
        TextView textView = view.findViewById(R.id.text_home);
        textView.setText("This is home fragment");

        mClient = ((MainActivity) getActivity()).getClientMain();


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        searchTest5();

    }

    public void showSearchResult() {
        Log.v("FragmentSearchProject", "search " + mClient.getFindProjects().size());
        ArrayList<Project> projects = mClient.getFindProjects();
        for (Project project : projects) {
            Log.v("FragmentSearchProject", "search "+ project.getName());
        }
    }

//1. Тест
//Найти первый проект через его имя
    private void searchTest1(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("search_string", "Проект 1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mClient.searchProjects(jsonObject);
    }

//2. Тест
//Найти первый и третий проекты через скилл С++
    private void searchTest2(){
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
        mClient.searchProjects(jsonObject);
    }

//3. Тест
//Найти второй и третий проекты через скилл JS
    private void searchTest3(){
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();
            JSONObject skill = new JSONObject();
            skill.put("skill_type", "JS");
            skill.put("skill_level", "");
            jsonArray.put(skill);
            jsonObject.put("skills", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mClient.searchProjects(jsonObject);
    }

//4. Тест
//Найти только третий проект через два скилла
    private void searchTest4(){
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();
            JSONObject skill = new JSONObject();
            skill.put("skill_type", "C++");
            skill.put("skill_level", "");
            jsonArray.put(skill);
            JSONObject skill2 = new JSONObject();
            skill2.put("skill_type", "JS");
            skill2.put("skill_level", "");
            jsonArray.put(skill2);
            jsonObject.put("skills", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mClient.searchProjects(jsonObject);
    }
//5. Тест
//Найти первый и третий проект через поисковую строку описания
    private void searchTest5(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("search_string", "SECRET_PROJECT_STRING");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mClient.searchProjects(jsonObject);
    }

//6. Тест
//Найти третий через поисковую строку описания
    private void searchTest6(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("search_string", "SECRET_PROJECT_STRINGDUMB_DESCR_CHAR");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mClient.searchProjects(jsonObject);
    }

}
