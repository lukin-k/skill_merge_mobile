package com.example.bipapp.adapters.view_holders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bipapp.R;
import com.example.bipapp.client.ClientMain;
import com.example.bipapp.models.Project;
import com.example.bipapp.models.User;

public class ViewHolderSubscription extends RecyclerView.ViewHolder {
    private final TextView mTextProjectName;
    private final TextView mTextProjectTag;
    private final ImageView mImageInitiatorPhoto;
    private final TextView mTextInitiatorFullname;
    private String mProjectId;

    public ViewHolderSubscription(@NonNull View itemView) {
        super(itemView);

        mTextProjectName = itemView.findViewById(R.id.text_project_name);
        mTextProjectTag = itemView.findViewById(R.id.text_project_tag);
        mImageInitiatorPhoto = itemView.findViewById(R.id.image_initiator_photo);
        mTextInitiatorFullname = itemView.findViewById(R.id.text_initiator_fullname);
        Button buttonUnsubscribe = itemView.findViewById(R.id.button_unsubscribe);

        buttonUnsubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientMain.getClient().unsubscribeSubscription(mProjectId);
            }
        });
    }


    public void onBind(Project project) {
        mProjectId = project.getId();
        mTextProjectName.setText(project.getName());
        setInitiator(project.getInitiator());
        mTextProjectTag.setText(project.getTag());
    }

    private void setInitiator(User initiator) {
        mImageInitiatorPhoto.setImageBitmap(initiator.getPhoto());
        mTextInitiatorFullname.setText(initiator.getFullName());
    }
}
