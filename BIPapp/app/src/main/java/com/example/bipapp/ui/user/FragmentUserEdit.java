package com.example.bipapp.ui.user;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.bipapp.MainActivity;
import com.example.bipapp.R;
import com.example.bipapp.adapters.AdapterRecyclerSkills;
import com.example.bipapp.client.ClientMain;
import com.example.bipapp.models.Skill;
import com.example.bipapp.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//TODO back button go to show info

public class FragmentUserEdit extends Fragment {
    private ClientMain mClient;
    private AdapterRecyclerSkills mAdapterRecyclerSkills;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_edit, container, false);

        RecyclerView recyclerSkills = view.findViewById(R.id.recycler_skills);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerSkills.setLayoutManager(layoutManager);
        mAdapterRecyclerSkills = new AdapterRecyclerSkills();
        recyclerSkills.setAdapter(mAdapterRecyclerSkills);

        Button buttonSave = view.findViewById(R.id.button_save_edit_user);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editFullName = view.findViewById(R.id.edit_fullname);
                EditText editAge = view.findViewById(R.id.edit_age);
                EditText editBiography = view.findViewById(R.id.edit_biography);
                //TODO get photo

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


        String[] NEED_PERMISSIONS = {Manifest.permission.READ_SMS};
        if (ActivityCompat.checkSelfPermission(getContext(),
            android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(getContext(),
                    android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
         requestPermissions(NEED_PERMISSIONS, REQUEST_CODE_PERMISSION);
    } else {
        Log.e("DB", "PERMISSION GRANTED");
    }
        return view;
    }
    private final int REQUEST_CODE_PERMISSION = 101;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0) {
                Log.v("Per", "" + grantResults);
            }
        }
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

        mAdapterRecyclerSkills.setSkills(mClient.getAllSkillsList());
        mAdapterRecyclerSkills.notifyDataSetChanged();

        //TODO show my skills and photo
    }

    public void setClient(ClientMain client) {
        mClient = client;
    }

    private JSONArray getSelectedSkills(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<Skill> skills = mAdapterRecyclerSkills.getSkills();
        boolean[] selectedSkills = mAdapterRecyclerSkills.getSelectedSkills();

        for (int i = 0; i < selectedSkills.length; i++) {
            if(selectedSkills[i]){
                jsonArray.put(skills.get(i).getJsonSkill());
            }
        }

        return jsonArray;
    }
}