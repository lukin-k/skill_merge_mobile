package com.example.bipapp.ui.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bipapp.MainActivity;
import com.example.bipapp.R;
import com.example.bipapp.client.ClientMain;
import com.example.bipapp.models.User;

public class FragmentUserInfo extends Fragment {
    private ClientMain mClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setClient(ClientMain client) {
        mClient = client;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
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

        TextView textFullName = view.findViewById(R.id.text_fullname);
        textFullName.setText(user.getFullName());

        TextView textAge = view.findViewById(R.id.text_age);
        textAge.setText("" + user.getAge());

        TextView textBiography = view.findViewById(R.id.text_biography);
        textBiography.setText(user.getBiography());

        //TODO show skills and photo
    }
}