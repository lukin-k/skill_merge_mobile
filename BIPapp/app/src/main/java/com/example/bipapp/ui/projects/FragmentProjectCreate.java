package com.example.bipapp.ui.projects;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class FragmentProjectCreate extends Fragment {
    private ClientMain mClient;
    private AdapterRecyclerProjectTags mAdapterRecyclerProjectTags;
    private AdapterRecyclerSkillsSelected mAdapterRecyclerSkills;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mClient = ClientMain.getClient();
        View view = inflater.inflate(R.layout.fragment_project_create, container, false);

        RecyclerView recyclerTags = view.findViewById(R.id.recycler_project_tags);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerTags.setLayoutManager(layoutManager);

        mAdapterRecyclerProjectTags = new AdapterRecyclerProjectTags();
        mAdapterRecyclerProjectTags.setTags(mClient.getAllProjectTagsList());
        recyclerTags.setAdapter(mAdapterRecyclerProjectTags);

        RecyclerView recyclerSkills = view.findViewById(R.id.recycler_skills);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerSkills.setLayoutManager(layoutManager);

        mAdapterRecyclerSkills = new AdapterRecyclerSkillsSelected();
        mAdapterRecyclerSkills.setSkills(mClient.getAllSkillsList());
        mAdapterRecyclerSkills.setSkillsLevels(mClient.getAllSkillsLevel());
        recyclerSkills.setAdapter(mAdapterRecyclerSkills);

        Button buttonCreate = view.findViewById(R.id.button_create_project);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editProjectName = view.findViewById(R.id.edit_project_name);
                EditText editProjectDescription = view.findViewById(R.id.edit_project_description);

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("project_name", editProjectName.getText().toString());
                    jsonObject.put("project_description", editProjectDescription.getText().toString());
                    jsonObject.put("tags", mAdapterRecyclerProjectTags.getSelectedTag());
                    jsonObject.put("skills", getSelectedSkills());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mClient.createProject(jsonObject);
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