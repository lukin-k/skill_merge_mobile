package com.example.bipapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bipapp.api.API;

public class ActivitySingIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);

        SharedPreferences preferences = getSharedPreferences(API.PREFERENCES_NAME, Context.MODE_PRIVATE);
        API.setPreferences(preferences);


//        if token exist
    //        then check active maybe refresh
    //        if check then go mainActivity
    //        else show error dialog

    }
}