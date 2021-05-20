package com.example.bipapp.ui.search_project;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;

import com.example.bipapp.R;
import com.example.bipapp.adapters.AdapterRecyclerProjectTags;
import com.example.bipapp.adapters.AdapterRecyclerSkillsSelected;
import com.example.bipapp.client.ClientMain;
import com.example.bipapp.models.Skill;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//TODO back onece request exit? second go to singin activity
public class FragmentSearch extends Fragment {
    private ClientMain mClient;
    private AdapterRecyclerProjectTags mAdapterRecyclerProjectTags;
    private AdapterRecyclerSkillsSelected mAdapterRecyclerSkills;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mClient = ClientMain.getClient();
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        RecyclerView recyclerSkills = view.findViewById(R.id.recycler_skills);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerSkills.setLayoutManager(layoutManager);

        mAdapterRecyclerSkills = new AdapterRecyclerSkillsSelected();
        mAdapterRecyclerSkills.setSkills(mClient.getAllSkillsList());
        recyclerSkills.setAdapter(mAdapterRecyclerSkills);

        RecyclerView recyclerTags = view.findViewById(R.id.recycler_project_tags);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerTags.setLayoutManager(layoutManager);

        mAdapterRecyclerProjectTags = new AdapterRecyclerProjectTags();
        mAdapterRecyclerProjectTags.setTags(mClient.getAllProjectTagsList());
        recyclerTags.setAdapter(mAdapterRecyclerProjectTags);

        //TODO add button reset

        Button buttonSearch = view.findViewById(R.id.button_search);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editSearch = view.findViewById(R.id.edit_search);

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("search_string", editSearch.getText().toString());
                    jsonObject.put("tag", mAdapterRecyclerProjectTags.getSelectedTag());
                    jsonObject.put("skills", getSelectedSkills());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.v("Search", jsonObject.toString());

                mClient.searchProjects(jsonObject);
            }
        });

        return view;
    }

    private JSONArray getSelectedSkills() {
        JSONArray jsonArray = new JSONArray();
        ArrayList<Skill> skills = mAdapterRecyclerSkills.getSkills();
        boolean[] selectedSkills = mAdapterRecyclerSkills.getSelectedSkills();

        for (int i = 0; i < selectedSkills.length; i++) {
            if (selectedSkills[i]) {
                jsonArray.put(skills.get(i).getJsonSkill());
            }
        }

        return jsonArray;
    }
}
