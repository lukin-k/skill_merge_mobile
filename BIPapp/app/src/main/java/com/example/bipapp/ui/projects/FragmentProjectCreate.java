package com.example.bipapp.ui.projects;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.bipapp.R;
import com.example.bipapp.client.ClientMain;

import org.json.JSONException;
import org.json.JSONObject;


public class FragmentProjectCreate extends Fragment {
    private ClientMain mClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_create, container, false);

        Button buttonCreate = view.findViewById(R.id.button_create_project);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editProjectName = view.findViewById(R.id.edit_project_name);
                EditText editProjectDescription = view.findViewById(R.id.edit_project_description);
                EditText editProjectTags = view.findViewById(R.id.edit_project_tags);
                //TODO get other fields

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("project_name", editProjectName.getText().toString());
                    jsonObject.put("project_description", editProjectDescription.getText().toString());
                    jsonObject.put("tags", editProjectTags.getText().toString());
                    //TODO set other fields
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mClient.createProject(jsonObject);
            }
        });

        return view;
    }

    public void setClient(ClientMain client) {
        mClient = client;
    }
}