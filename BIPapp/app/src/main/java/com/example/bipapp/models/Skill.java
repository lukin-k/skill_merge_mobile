package com.example.bipapp.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Skill {
    private final String SKILL_TYPE = "skill_type";
    private final String SKILL_LEVEL = "skill_level";

    private String mType;
    private String mLevel;

    public Skill(JSONObject map) throws JSONException {
        update(map);
    }

    private void update(JSONObject jsonObject) throws JSONException {
        mType = jsonObject.getString(SKILL_TYPE);
        mLevel = jsonObject.getString(SKILL_LEVEL);
    }

    public JSONObject getJsonSkill(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(SKILL_TYPE, mType);
            jsonObject.put(SKILL_LEVEL, mLevel);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }


    public String getType() {
        return mType;
    }

    public String getLevel() {
        return mLevel;
    }
}
