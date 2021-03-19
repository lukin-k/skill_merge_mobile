package com.example.bipapp.api;

import android.content.SharedPreferences;

public class API {
    public final static String KeyToken = "lol";
    public final static String PREFERENCES_NAME = "TokenStorage";
    private final static String KEY_TOKEN = "token";
    private static SharedPreferences sPreferences;
    private static String sToken;

    public static void setPreferences(SharedPreferences preferences) {
        sPreferences = preferences;
        sToken = getTokenPreferences();
    }

    public static boolean isTokenExist() {
        if (sToken == null) {
            return false;
        }

        return true;
    }

    public static boolean isTokenActive(){
        //TODO do implementation
        return true;
    }

    public static boolean refreshToken(){
        //TODO do implementation
        return true;
    }

    public static boolean singIn(String login, String password){
        if(login.length() <=0 || password.length()<=0){
            return false;
        }
        //TODO do implementation get token
        sToken = "самбисты, каратисты по одному, остальные можно кучей";
        //TODO do implementation get token

        setTokenPreferences();
        return true;
    }

    public static boolean singUp(String login, String password){
        if(login.length() <=0 || password.length()<=0){
            return false;
        }
        //TODO do implementation get token
        sToken = "самбисты, каратисты по одному, остальные можно кучей";
        //TODO do implementation get token

        setTokenPreferences();
        return true;
    }




    private static String getTokenPreferences() {
        return sPreferences.getString(KEY_TOKEN, null);
    }

    private static void setTokenPreferences(){
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putString(KEY_TOKEN, sToken);
        editor.commit();
    }


}
