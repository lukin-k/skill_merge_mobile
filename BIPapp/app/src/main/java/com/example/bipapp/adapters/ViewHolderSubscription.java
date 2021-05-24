package com.example.bipapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bipapp.R;
import com.example.bipapp.client.ClientMain;
import com.example.bipapp.models.Project;
import com.example.bipapp.models.User;

public class ViewHolderSubscription extends RecyclerView.ViewHolder {
    private final TextView mTextName;
    private final TextView mTextTag;
    private final ImageView mImageInitiatorPhoto;
    private final TextView mTextInitiatorFullname;
    private Project mProject;
    private FragmentManager mFragmentManager;

    public void setFragmentManager(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    public ViewHolderSubscription(@NonNull View itemView) {
        super(itemView);

//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDialogProjectData();
//            }
//        });

        mTextName = itemView.findViewById(R.id.text_project_name);
        mTextTag = itemView.findViewById(R.id.text_project_tag);
        mImageInitiatorPhoto = itemView.findViewById(R.id.image_initiator_photo);
        mTextInitiatorFullname = itemView.findViewById(R.id.text_initiator_fullname);
        Button buttonUnsubscribe = itemView.findViewById(R.id.button_unsubscribe);

        buttonUnsubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO ClientMain.getClient().unsubscribeProjectSubscribers();
            }
        });
    }


    public void onBind(Project project) {
        mProject = project;
        mTextName.setText(mProject.getName());
        setInitiator(mProject.getInitiator());
        mTextTag.setText(mProject.getTag());
    }

    private void setInitiator(User initiator) {
        mImageInitiatorPhoto.setImageBitmap(initiator.getPhoto());
        mTextInitiatorFullname.setText(initiator.getFullName());
    }

//    private void showDialogProjectData() {
//        Context context = itemView.getContext();
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = layoutInflater.inflate(R.layout.fragment_project_info, null);
//
//        AdapterPagerMiniUsers adapterPager = new AdapterPagerMiniUsers(mFragmentManager, context.getResources(), mProject);
//        ViewPager viewPager = view.findViewById(R.id.pager_mini_users);
//        viewPager.setAdapter(adapterPager);
//
//        View panelSkills = view.findViewById(R.id.project_skills);
//        RecyclerView recyclerSkills = panelSkills.findViewById(R.id.recycler_skills);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
//        recyclerSkills.setLayoutManager(layoutManager);
//        AdapterRecyclerSkillsNonSelected adapterRecyclerSkills = new AdapterRecyclerSkillsNonSelected();
//        adapterRecyclerSkills.setSkills(mProject.getSkills());
//        recyclerSkills.setAdapter(adapterRecyclerSkills);
//
//        view.findViewById(R.id.fab_update_project).setVisibility(View.GONE);
//
//        TextView textName = view.findViewById(R.id.text_project_name);
//        textName.setText(mProject.getName());
//
//        TextView textDescription = view.findViewById(R.id.text_project_description);
//        textDescription.setText(mProject.getDescription());
//
//        TextView textTag = view.findViewById(R.id.text_project_tag);
//        textTag.setText(mProject.getTag());
//
//        setInitiator(view, mProject.getInitiator());
//
//        Button buttonAction = view.findViewById(R.id.button_project_action);
//        buttonAction.setVisibility(View.GONE);
//
//
//        AlertDialog dialog = builder.setView(view).create();
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.cancel();
//            }
//        });
//        dialog.show();
//    }
//
//    private void setInitiator(View view, User initiator) {
//
//        ImageView imageInitiatorPhoto = view.findViewById(R.id.image_initiator_photo);
//        imageInitiatorPhoto.setImageBitmap(initiator.getPhoto());
//
//        TextView textInitiatorFullname = view.findViewById(R.id.text_initiator_fullname);
//        textInitiatorFullname.setText(initiator.getFullName());
//    }
}
