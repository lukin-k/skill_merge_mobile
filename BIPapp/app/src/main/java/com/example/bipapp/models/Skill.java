package com.example.bipapp.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Skill {
    private String mType;
    private String mLevel;

    public Skill(JSONObject map) throws JSONException {
        update(map);
    }

    private void update(JSONObject map) throws JSONException {
        mType = map.getString("skill_type");
        mLevel = map.getString("skill_level");
    }


    public String getType() {
        return mType;
    }

    public String getLevel() {
        return mLevel;
    }
}
