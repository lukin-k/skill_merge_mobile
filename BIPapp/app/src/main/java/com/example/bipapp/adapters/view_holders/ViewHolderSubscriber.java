package com.example.bipapp.adapters.view_holders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bipapp.R;
import com.example.bipapp.adapters.AdapterRecyclerSkillsNonSelected;
import com.example.bipapp.client.ClientMain;
import com.example.bipapp.models.Project;
import com.example.bipapp.models.User;

public class ViewHolderSubscriber extends RecyclerView.ViewHolder {
    private final TextView mTextProjectName;
    private final TextView mTextProjectTag;

    private final ImageView mImagePhoto;
    private final TextView mTextFullname;
    private final AdapterRecyclerSkillsNonSelected mAdapterRecyclerSkills;

    private String mProjectId;
    private User mUser;

    public ViewHolderSubscriber(@NonNull View itemView) {
        super(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogUserData();
            }
        });

        mTextProjectName = itemView.findViewById(R.id.text_project_name);
        mTextProjectTag = itemView.findViewById(R.id.text_project_tag);

        mImagePhoto = itemView.findViewById(R.id.image_photo);
        mTextFullname = itemView.findViewById(R.id.text_fullname);

        Button buttonPositive = itemView.findViewById(R.id.button_positive);
        buttonPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientMain.getClient().acceptSubscriber(mProjectId, mUser.getUserName());
            }
        });
        buttonPositive.setVisibility(View.VISIBLE);
        buttonPositive.setText(itemView.getContext().getResources().getString(R.string.button_accept));

        Button buttonNegative = itemView.findViewById(R.id.button_negative);
        buttonNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientMain.getClient().deleteSubscriber(mProjectId, mUser.getUserName());
            }
        });
        buttonNegative.setVisibility(View.VISIBLE);
        buttonNegative.setText(itemView.getContext().getResources().getString(R.string.button_delete));

        RecyclerView recyclerSkills = itemView.findViewById(R.id.recycler_skills);
        LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerSkills.setLayoutManager(layoutManager);
        mAdapterRecyclerSkills = new AdapterRecyclerSkillsNonSelected();
        recyclerSkills.setAdapter(mAdapterRecyclerSkills);
    }

    public void onBind(Project project, User user) {
        mProjectId = project.getId();
        mTextProjectName.setText(project.getName());
        mTextProjectTag.setText(project.getTag());

        mUser = user;
        mImagePhoto.setImageBitmap(mUser.getPhoto());
        mTextFullname.setText(mUser.getFullName());
        mAdapterRecyclerSkills.setSkills(mUser.getSkills());
        mAdapterRecyclerSkills.notifyDataSetChanged();
    }

    private void showDialogUserData() {
        Context context = itemView.getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.fragment_user_info, null);

        ImageView imagePhoto = view.findViewById(R.id.image_photo);
        imagePhoto.setImageBitmap(mUser.getPhoto());

        TextView textFullName = view.findViewById(R.id.text_fullname);
        textFullName.setText(mUser.getFullName());
        TextView textUserName = view.findViewById(R.id.text_username);
        textUserName.setText(mUser.getUserName());
        TextView textAge = view.findViewById(R.id.text_age);
        textAge.setText("" + mUser.getAge());
        TextView textBiography = view.findViewById(R.id.text_biography);
        textBiography.setText(mUser.getBiography());

        RecyclerView recyclerSkills = view.findViewById(R.id.recycler_skills);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerSkills.setLayoutManager(layoutManager);
        AdapterRecyclerSkillsNonSelected adapterRecyclerSkills = new AdapterRecyclerSkillsNonSelected();
        adapterRecyclerSkills.setSkills(mUser.getSkills());
        recyclerSkills.setAdapter(adapterRecyclerSkills);

        view.findViewById(R.id.fab_edit_user).setVisibility(View.GONE);

        AlertDialog dialog = builder.setView(view).create();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
}
