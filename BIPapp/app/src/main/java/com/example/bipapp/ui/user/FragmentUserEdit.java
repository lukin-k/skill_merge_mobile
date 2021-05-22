package com.example.bipapp.ui.user;

import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bipapp.R;
import com.example.bipapp.adapters.AdapterRecyclerSkillsSelected;
import com.example.bipapp.client.ClientMain;
import com.example.bipapp.models.Skill;
import com.example.bipapp.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//TODO back to UserInfo

public class FragmentUserEdit extends Fragment {
    private ClientMain mClient;
    private AdapterRecyclerSkillsSelected mAdapterRecyclerSkills;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mClient = ClientMain.getClient();
        View view = inflater.inflate(R.layout.fragment_user_edit, container, false);

        RecyclerView recyclerSkills = view.findViewById(R.id.recycler_skills);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerSkills.setLayoutManager(layoutManager);

        mAdapterRecyclerSkills = new AdapterRecyclerSkillsSelected();
        recyclerSkills.setAdapter(mAdapterRecyclerSkills);

        Button buttonSave = view.findViewById(R.id.button_save_edit_user);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editFullName = view.findViewById(R.id.edit_fullname);
                EditText editAge = view.findViewById(R.id.edit_age);
                EditText editBiography = view.findViewById(R.id.edit_biography);

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("fullname", editFullName.getText().toString());
                    jsonObject.put("age", Integer.parseInt(editAge.getText().toString()));
                    jsonObject.put("biography", editBiography.getText().toString());
                    jsonObject.put("skills", getSelectedSkills());
                    //TODO set photo
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mClient.changeUserInfo(jsonObject);
            }
        });

        ImageView ibCameraImage = view.findViewById(R.id.image_photo);
        ibCameraImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder selectSourceDialogueBuilder = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle);
                selectSourceDialogueBuilder.setMessage(getResources().getString(R.string.title_select_source_photo));
                selectSourceDialogueBuilder.setCancelable(true);

                selectSourceDialogueBuilder.setPositiveButton(
                        getResources().getString(R.string.title_source_gallery),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getContext(), "gallery", Toast.LENGTH_SHORT).show();
                            }
                        });
                selectSourceDialogueBuilder.setNegativeButton(
                        getResources().getString(R.string.title_source_camera),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getContext(), "camera", Toast.LENGTH_SHORT).show();
                            }
                        });

                selectSourceDialogueBuilder.create().show();
                //TODO save tmp photo
            }
        });
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        setUserInfo();
    }

    private void setUserInfo() {
        User user = mClient.getUser();
        View view = getView();

        EditText editFullName = view.findViewById(R.id.edit_fullname);
        editFullName.setText(user.getFullName());

        EditText editAge = view.findViewById(R.id.edit_age);
        editAge.setText("" + user.getAge());

        EditText editBiography = view.findViewById(R.id.edit_biography);
        editBiography.setText(user.getBiography());

        ImageView imagePhoto = view.findViewById(R.id.image_photo);
        if (user.getPhoto() == null) {
            imagePhoto.setImageResource(getResources().getIdentifier("test_photo", "drawable", getActivity().getPackageName()));
        } else {
            imagePhoto.setImageBitmap(user.getPhoto());
        }

        mAdapterRecyclerSkills.setSkills(mClient.getAllSkillsList());
        mAdapterRecyclerSkills.setSkillsLevels(mClient.getAllSkillsLevel());
        mAdapterRecyclerSkills.setSelectedSkills(user.getSkills());
        mAdapterRecyclerSkills.notifyDataSetChanged();
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