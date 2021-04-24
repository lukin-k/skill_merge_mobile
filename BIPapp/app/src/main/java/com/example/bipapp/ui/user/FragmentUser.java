package com.example.bipapp.ui.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bipapp.MainActivity;
import com.example.bipapp.R;
import com.example.bipapp.client.Client;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentUser extends Fragment {
    private Client mClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
//        TextView textView = view.findViewById(R.id.text_user);
//        textView.setText("This is my user fragment");


        Log.v("test", "onCreateView");
        mClient = ((MainActivity) getActivity()).getClient();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v("test", "onResume");

        //TODO create User class to save user info

//        if(!User.isExist){
        mClient.getUserInfo();
//        }
    }

    public void showUserInfo(JSONObject jsonObject){
        View view = getView();
        TextView textFullName = view.findViewById(R.id.text_fullname);
        try {
            Log.v("test", jsonObject.toString());
            String s = jsonObject.getString("fullname");
            Log.v("test", s);
            textFullName.setText(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}