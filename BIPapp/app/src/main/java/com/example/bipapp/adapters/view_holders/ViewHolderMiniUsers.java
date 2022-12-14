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
import com.example.bipapp.models.User;

public abstract class ViewHolderMiniUsers extends RecyclerView.ViewHolder {
    private final ImageView mImagePhoto;
    private final TextView mTextFullname;
    private final Button mButtonPositive;
    private final Button mButtonNegative;
    private final boolean isInitiator;
    private final AdapterRecyclerSkillsNonSelected mAdapterRecyclerSkills;

    protected User mUser;

    public ViewHolderMiniUsers(@NonNull View itemView, boolean isInitiator) {
        super(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogUserData();
            }
        });

        this.isInitiator = isInitiator;
        mImagePhoto = itemView.findViewById(R.id.image_photo);
        mTextFullname = itemView.findViewById(R.id.text_fullname);

        mButtonPositive = itemView.findViewById(R.id.button_positive);
        mButtonPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPositive();
            }
        });

        mButtonNegative = itemView.findViewById(R.id.button_negative);
        mButtonNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNegative();
            }
        });

        RecyclerView recyclerSkills = itemView.findViewById(R.id.recycler_skills);
        LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerSkills.setLayoutManager(layoutManager);
        mAdapterRecyclerSkills = new AdapterRecyclerSkillsNonSelected();
        recyclerSkills.setAdapter(mAdapterRecyclerSkills);
    }

    protected abstract void onClickPositive();

    protected abstract void onClickNegative();

    public void onBind(User user) {
        mUser = user;
        mImagePhoto.setImageBitmap(mUser.getPhoto());
        mTextFullname.setText(mUser.getFullName());
        mAdapterRecyclerSkills.setSkills(mUser.getSkills());
        mAdapterRecyclerSkills.notifyDataSetChanged();

        if (isInitiator) {
            if (ClientMain.getClient().getUser().getUserName().equals(user.getUserName())) {
                mButtonNegative.setVisibility(View.GONE);
            } else {
                setVisibleNegative();
            }
        }
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

    public void setVisiblePositive() {
        mButtonPositive.setVisibility(View.VISIBLE);
    }

    public void setVisibleNegative() {
        mButtonNegative.setVisibility(View.VISIBLE);
    }

    public void setTextPositive(String text) {
        mButtonPositive.setText(text);
    }

    public void setTextNegative(String text) {
        mButtonNegative.setText(text);
    }
}
