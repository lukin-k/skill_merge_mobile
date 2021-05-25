package com.example.bipapp.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bipapp.R;
import com.example.bipapp.adapters.view_holders.ViewHolderSkill;
import com.example.bipapp.models.Skill;

import java.util.ArrayList;

public class AdapterRecyclerSkillsSelected extends RecyclerView.Adapter<ViewHolderSkill> {
    private ArrayList<Skill> mSkills;
    private String[] mSkillsLevels;
    private boolean[] mSelectedSkills;

    private Context mContext;

    public void setSkills(ArrayList<Skill> skills) {
        mSkills = skills;
        mSelectedSkills = new boolean[skills.size()];
    }

    public void setSkillsLevels(ArrayList<String> skillsLevels) {
        mSkillsLevels = skillsLevels.toArray(new String[0]);
    }

    public void setSelectedSkills(ArrayList<Skill> selectedSkills) {
        boolean[] newSelected = new boolean[mSkills.size()];
        for (int i = 0; i < mSkills.size(); i++) {
            Skill skill = mSkills.get(i);
            for (Skill selectedSkill : selectedSkills) {
                if (selectedSkill.getType().equals(skill.getType())) {
                    skill.setLevel(selectedSkill.getLevel());
                    newSelected[i] = true;
                    break;
                }
            }
        }

        mSelectedSkills = newSelected;
    }

    public AdapterRecyclerSkillsSelected() {
        mSkills = new ArrayList<>();
        mSelectedSkills = new boolean[0];
        mSkillsLevels = new String[0];
        mContext = null;
    }

    @NonNull
    @Override
    public ViewHolderSkill onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recycler_skill, viewGroup, false);
        ViewHolderSkill viewHolderSkill = new ViewHolderSkill(view) {
            @Override
            public void onClickSkill() {
                int i = getAdapterPosition();
                if (mSkillsLevels.length > 0) {
                    if (mSelectedSkills[i]) {
                        mSkills.get(i).setLevel("");
                        mSelectedSkills[i] = !mSelectedSkills[i];
                        onBind(mSkills.get(i), mSelectedSkills[i]);
                    } else {
                        showDialogSelectLevel(this);
                    }
                } else {
                    mSelectedSkills[i] = !mSelectedSkills[i];
                    onBind(mSkills.get(i), mSelectedSkills[i]);
                }
            }
        };

        if (mContext == null) {
            mContext = view.getContext();
        }
        return viewHolderSkill;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSkill viewHolderSkill, int i) {
        viewHolderSkill.onBind(mSkills.get(i), mSelectedSkills[i]);
    }

    @Override
    public int getItemCount() {
        return mSkills.size();
    }

    public boolean[] getSelectedSkills() {
        return mSelectedSkills;
    }

    public ArrayList<Skill> getSkills() {
        return mSkills;
    }

    private void showDialogSelectLevel(ViewHolderSkill viewHolderSkill) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(mContext.getResources().getString(R.string.title_choose_level_skill))
                .setItems(mSkillsLevels, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int i = viewHolderSkill.getAdapterPosition();
                        mSkills.get(i).setLevel(mSkillsLevels[which]);
                        mSelectedSkills[i] = !mSelectedSkills[i];
                        viewHolderSkill.onBind(mSkills.get(i), mSelectedSkills[i]);
                    }
                })
                .create()
                .show();
    }
}
