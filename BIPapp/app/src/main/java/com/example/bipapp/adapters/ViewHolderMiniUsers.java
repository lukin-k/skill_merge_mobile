package com.example.bipapp.adapters;

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
import com.example.bipapp.models.User;

public abstract class ViewHolderMiniUsers extends RecyclerView.ViewHolder {
    private ImageView mImagePhoto;
    private TextView mTextFullname;
    private TextView mTextAge;
    private Button mButtonPositive;
    private Button mButtonNegative;
    private AdapterRecyclerSkillsNonSelected mAdapterRecyclerSkills;

    protected User mUser;

    public ViewHolderMiniUsers(@NonNull View itemView) {
        super(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogUserData();
            }
        });

        mImagePhoto = itemView.findViewById(R.id.image_photo);
        mTextFullname = itemView.findViewById(R.id.text_fullname);
        mTextAge = itemView.findViewById(R.id.text_age);

        mButtonPositive = itemView.findViewById(R.id.button_user_positive);
        mButtonPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPositive();
            }
        });

        mButtonNegative = itemView.findViewById(R.id.button_user_negative);
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
        mTextFullname.setText(mUser.getFullName());
        mTextAge.setText("" + mUser.getAge());
        mAdapterRecyclerSkills.setSkills(mUser.getSkills());
        mAdapterRecyclerSkills.notifyDataSetChanged();
    }

    private void showDialogUserData() {
        Context context = itemView.getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.fragment_user_info, null);
        TextView textFullName = view.findViewById(R.id.text_fullname);
        textFullName.setText(mUser.getFullName());

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
