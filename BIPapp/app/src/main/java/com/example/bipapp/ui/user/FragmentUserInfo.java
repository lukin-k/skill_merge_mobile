package com.example.bipapp.ui.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bipapp.R;
import com.example.bipapp.adapters.AdapterRecyclerSkillsNonSelected;
import com.example.bipapp.client.ClientMain;
import com.example.bipapp.models.User;

public class FragmentUserInfo extends Fragment {
    private ClientMain mClient;
    private AdapterRecyclerSkillsNonSelected mAdapterRecyclerSkills;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mClient = ClientMain.getClient();
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        RecyclerView recyclerSkills = view.findViewById(R.id.recycler_skills);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerSkills.setLayoutManager(layoutManager);

        mAdapterRecyclerSkills = new AdapterRecyclerSkillsNonSelected();
        recyclerSkills.setAdapter(mAdapterRecyclerSkills);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        showUserInfo();
    }

    private void showUserInfo() {
        User user = mClient.getUser();
        View view = getView();

        TextView textFullname = view.findViewById(R.id.text_fullname);
        textFullname.setText(user.getFullName());

        TextView textUserame = view.findViewById(R.id.text_username);
        textUserame.setText(user.getUserName());

        TextView textAge = view.findViewById(R.id.text_age);
        textAge.setText("" + user.getAge());

        TextView textBiography = view.findViewById(R.id.text_biography);
        textBiography.setText(user.getBiography());

        ImageView ivUserPhoto = view.findViewById(R.id.image_photo);
        if (user.getPhoto() == null) {
            ivUserPhoto.setImageResource(getResources().getIdentifier("test_photo", "drawable", getActivity().getPackageName()));
        } else {
            ivUserPhoto.setImageBitmap(user.getPhoto());
        }

        mAdapterRecyclerSkills.setSkills(user.getSkills());
        mAdapterRecyclerSkills.notifyDataSetChanged();
    }
}