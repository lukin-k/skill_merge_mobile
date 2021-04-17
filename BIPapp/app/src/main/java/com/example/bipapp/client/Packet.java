package com.example.bipapp.client;

import org.json.JSONObject;

import java.net.URL;

public class Packet {
    private ETypePacket mTypePacket;
    private URL mUrl;
    private JSONObject mJsonObject;

    public Packet(ETypePacket typePacket) {
        mTypePacket = typePacket;
    }

    public Packet(ETypePacket typePacket, URL url, JSONObject jsonObject) {
        mTypePacket = typePacket;
        mUrl = url;
        mJsonObject = jsonObject;
    }

    public ETypePacket getTypePacket() {
        return mTypePacket;
    }

    public URL getUrl() {
        return mUrl;
    }

    public JSONObject getJsonObject() {
        return mJsonObject;
    }

    public void setTypePacket(ETypePacket typePacket) {
        mTypePacket = typePacket;
    }

    public void setUrl(URL url) {
        mUrl = url;
    }

    public void setJsonObject(JSONObject jsonObject) {
        mJsonObject = jsonObject;
    }
}
