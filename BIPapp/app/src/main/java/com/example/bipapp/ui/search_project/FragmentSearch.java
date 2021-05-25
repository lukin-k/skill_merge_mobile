package com.example.bipapp.ui.search_project;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;

import com.example.bipapp.R;
import com.example.bipapp.adapters.AdapterRecyclerProjectTags;
import com.example.bipapp.adapters.AdapterRecyclerSkillsSelected;
import com.example.bipapp.client.ClientMain;
import com.example.bipapp.models.Skill;
import com.example.bipapp.ui.IFragmentHost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentSearch extends Fragment implements IFragmentHost {
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

        Button buttonReset = view.findViewById(R.id.button_search_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClient.resetLastSearch();
                setLastSearch();
            }
        });

        Button buttonSearch = view.findViewById(R.id.button_search);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editSearch = view.findViewById(R.id.edit_search);
                EditText editSearchUserName = view.findViewById(R.id.edit_search_username);

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("search_string", editSearch.getText().toString());
                    jsonObject.put("tag", mAdapterRecyclerProjectTags.getSelectedTag());
                    jsonObject.put("skills", getSelectedSkills());
                    jsonObject.put("participant", editSearchUserName.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mClient.searchProjects(jsonObject);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setLastSearch();
    }

    private void setLastSearch() {
        View view = getView();
        JSONObject lastSearch = mClient.getLastSearch();

        EditText editSearch = view.findViewById(R.id.edit_search);
        try {
            String search = lastSearch.getString("search_string");
            editSearch.setText(search);
        } catch (JSONException e) {
            editSearch.setText("");
        }

        EditText editSearchUserName = view.findViewById(R.id.edit_search_username);
        try {
            String username = lastSearch.getString("participant");
            editSearchUserName.setText(username);
        } catch (JSONException e) {
            editSearchUserName.setText("");
        }

        try {
            JSONArray jsonArray = lastSearch.getJSONArray("skills");
            ArrayList<Skill> skills = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject map = jsonArray.getJSONObject(i);
                skills.add(new Skill(map));
            }
            mAdapterRecyclerSkills.setSelectedSkills(skills);
            mAdapterRecyclerSkills.notifyDataSetChanged();
        } catch (JSONException e) {
            mAdapterRecyclerSkills.setSelectedSkills(new ArrayList<Skill>());
            mAdapterRecyclerSkills.notifyDataSetChanged();
        }

        try {
            String tag = lastSearch.getString("tag");
            mAdapterRecyclerProjectTags.setSelectedTag(tag);
            mAdapterRecyclerProjectTags.notifyDataSetChanged();
        } catch (JSONException e) {
            mAdapterRecyclerProjectTags.setSelectedTag("");
            mAdapterRecyclerProjectTags.notifyDataSetChanged();
        }
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

    @Override
    public boolean popLastFragment() {
        return false;
    }
}
