package com.example.bipapp.api;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.bipapp.client.ETypePacket;
import com.example.bipapp.client.Packet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class API {
    private final static String TAG = "API";
    private final static String SERVER_URL = "http://192.168.0.104:8000/core_backend/";
    private final static String ACTION_CREATE_USER = "create_user/";
    private final static String ACTION_LOGIN = "login/";

    private final static String ACTION_GET_ALL_SKILLS = "get_all_skills/";
    private final static String ACTION_GET_USER_INFO = "get_user_info/";
    private final static String ACTION_CHANGE_USER_INFO = "change_user_info/";

    private final static String ACTION_GET_ALL_PROJECT_TAGS = "get_all_project_tags/";
    private final static String ACTION_CREATE_PROJECT = "create_project/";
    private final static String ACTION_SEARCH_PROJECTS = "search_projects/";

    private final static String ACTION_SUBSCRIBE_PROJECT = "subscribe_project/";
    private final static String ACTION_UNSUBSCRIBE_PROJECT = "unsubscribe_project/";
    private final static String ACTION_ACCEPT_VOLUNTEER = "accept_volunteer_project/";
    private final static String ACTION_LEAVE_PROJECT = "leave_project/";

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

    public static boolean isTokenActive() {
        //TODO do implementation
        return true;
    }

    public static boolean refreshToken() {
        //TODO do implementation
        return true;
    }

    public static void setToken(String newToken) {
        sToken = newToken;
        setTokenPreferences();
    }

    public static Packet signIn(String login, String password) {
        Packet packet = sign(login, password);

        if (packet.getTypePacket() == ETypePacket.BAD) {
            return packet;
        }

        try {
            packet.setUrl(new URL(SERVER_URL + ACTION_LOGIN));
        } catch (MalformedURLException e) {
            packet.setTypePacket(ETypePacket.BAD);
        }
        packet.setTypePacket(ETypePacket.SIGN_IN);

        return packet;
    }

    public static Packet signUp(String login, String password) {
        Packet packet = sign(login, password);

        if (packet.getTypePacket() == ETypePacket.BAD) {
            return packet;
        }

        try {
            packet.setUrl(new URL(SERVER_URL + ACTION_CREATE_USER));
        } catch (MalformedURLException e) {
            packet.setTypePacket(ETypePacket.BAD);
        }
        packet.setTypePacket(ETypePacket.SIGN_UP);

        return packet;
    }

    private static Packet sign(String login, String password) {
        Packet packet = new Packet(ETypePacket.BAD);
        if (login.length() <= 0 || password.length() <= 0) {
            try {
                packet.setJsonObject(createError("Empty login or password"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return packet;
        }

        packet.setTypePacket(ETypePacket.TMP);

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", login);
            jsonObject.put("password", password);
            packet.setJsonObject(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return packet;
    }

    public static Packet getUserInfo() {
        Packet packet = new Packet(ETypePacket.BAD);

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", sToken);
            packet.setJsonObject(jsonObject);
            packet.setTypePacket(ETypePacket.GET_USER_INFO);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (packet.getTypePacket() == ETypePacket.BAD) {
            return packet;
        }

        try {
            packet.setUrl(new URL(SERVER_URL + ACTION_GET_USER_INFO));
        } catch (MalformedURLException e) {
            packet.setTypePacket(ETypePacket.BAD);
        }

        return packet;
    }

    public static Packet changeUserInfo(JSONObject jsonObject) {
        Packet packet = new Packet(ETypePacket.CHANGE_USER_INFO);
        try {
            packet.setUrl(new URL(SERVER_URL + ACTION_CHANGE_USER_INFO));
            jsonObject.put("token", sToken);
        } catch (MalformedURLException | JSONException e) {
            e.printStackTrace();
        }

        packet.setJsonObject(jsonObject);
        return packet;
    }

    public static Packet createProject(JSONObject jsonObject) {
        Packet packet = new Packet(ETypePacket.CREATE_PROJECT);
        try {
            packet.setUrl(new URL(SERVER_URL + ACTION_CREATE_PROJECT));
            jsonObject.put("token", sToken);
        } catch (MalformedURLException | JSONException e) {
            e.printStackTrace();
        }

        packet.setJsonObject(jsonObject);
        return packet;
    }

    public static Packet getAllSkills() {
        Packet packet = new Packet(ETypePacket.BAD);
        try {
            packet.setTypePacket(ETypePacket.GET_ALL_SKILLS);
            packet.setUrl(new URL(SERVER_URL + ACTION_GET_ALL_SKILLS));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", sToken);
            packet.setJsonObject(jsonObject);
        } catch (MalformedURLException | JSONException e) {
            e.printStackTrace();
        }

        return packet;
    }

    public static Packet getAllProjectTags() {
        Packet packet = new Packet(ETypePacket.BAD);
        try {
            packet.setTypePacket(ETypePacket.GET_ALL_PROJECT_TAGS);
            packet.setUrl(new URL(SERVER_URL + ACTION_GET_ALL_PROJECT_TAGS));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", sToken);
            packet.setJsonObject(jsonObject);
        } catch (MalformedURLException | JSONException e) {
            e.printStackTrace();
        }

        return packet;
    }

    public static Packet searchProjects(JSONObject jsonObject) {
        Packet packet = new Packet(ETypePacket.SEARCH_PROJECTS);
        try {
            packet.setUrl(new URL(SERVER_URL + ACTION_SEARCH_PROJECTS));
            jsonObject.put("token", sToken);
        } catch (MalformedURLException | JSONException e) {
            e.printStackTrace();
        }

        packet.setJsonObject(jsonObject);
        return packet;
    }

    public static Packet subscribeProject(String projectId) {
        Packet packet = new Packet(ETypePacket.SUBSCRIBE_PROJECT);
        JSONObject jsonObject = new JSONObject();
        try {
            packet.setUrl(new URL(SERVER_URL + ACTION_SUBSCRIBE_PROJECT));
            jsonObject.put("token", sToken);
            jsonObject.put("project_uuid", projectId);
        } catch (MalformedURLException | JSONException e) {
            e.printStackTrace();
        }

        packet.setJsonObject(jsonObject);
        return packet;
    }

    public static Packet unsubscribeProject(String projectId) {
        Packet packet = new Packet(ETypePacket.UNSUBSCRIBE_PROJECT);
        JSONObject jsonObject = new JSONObject();
        try {
            packet.setUrl(new URL(SERVER_URL + ACTION_UNSUBSCRIBE_PROJECT));
            jsonObject.put("token", sToken);
            jsonObject.put("project_uuid", projectId);
        } catch (MalformedURLException | JSONException e) {
            e.printStackTrace();
        }

        packet.setJsonObject(jsonObject);
        return packet;
    }

    public static Packet acceptVolunteer(String projectId, String username) {
        Packet packet = new Packet(ETypePacket.ACCEPT_VOLUNTEER);
        JSONObject jsonObject = new JSONObject();
        try {
            packet.setUrl(new URL(SERVER_URL + ACTION_ACCEPT_VOLUNTEER));
            jsonObject.put("token", sToken);
            jsonObject.put("project_uuid", projectId);
            jsonObject.put("username", username);
        } catch (MalformedURLException | JSONException e) {
            e.printStackTrace();
        }

        packet.setJsonObject(jsonObject);
        return packet;
    }

    public static Packet leaveProject(String projectId) {
        Packet packet = new Packet(ETypePacket.LEAVE_PROJECT);
        JSONObject jsonObject = new JSONObject();
        try {
            packet.setUrl(new URL(SERVER_URL + ACTION_LEAVE_PROJECT));
            jsonObject.put("token", sToken);
            jsonObject.put("project_uuid", projectId);
        } catch (MalformedURLException | JSONException e) {
            e.printStackTrace();
        }

        packet.setJsonObject(jsonObject);
        return packet;
    }


    //TODO взять отсюда csrf токен
//    private void kek() {
//        try {
//
//            HttpURLConnection urlConnection = null;
//            InputStream is = null;
//            JSONObject jObj = null;
//
//            URL url = new URL("urlString");
//
//            urlConnection = (HttpURLConnection) url.openConnection();
//
//            String csrfToken = null;
//
//            if (site.getCookieManager().getCookieStore().getCookies().size() > 0) {
//                //While joining the Cookies, use ',' or ';' as needed. Most of the server are using ';'
//                urlConnection.setRequestProperty("Cookie",
//                        TextUtils.join(";", site.getCookieManager().getCookieStore().getCookies()));
//
//                for (HttpCookie cookie : site.getCookieManager().getCookieStore().getCookies()) {
//                    if (cookie.getName().equals("csrfToken")) {
//                        csrfToken = cookie.getValue();
//                        urlConnection.setRequestProperty("X-CSRF-Token", csrfToken);
//                    }
//                }
//            }
//
//            if ((params != null) && !params.isEmpty()) { // To put your posts params AND the csrf Cookie
//                urlConnection.setDoOutput(true);
//                urlConnection.setChunkedStreamingMode(0);
//                urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
//                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + "UTF-8");
//
//                OutputStream output = urlConnection.getOutputStream();
//                output.write(params.getBytes("UTF-8"));
//
//                if (csrfToken != null) {
//                    String token = "&csrfToken=" + csrfToken;
//                    output.write(token.getBytes("UTF-8"));
//                }
//
//                output.close();
//            } else {
//                OutputStream output = urlConnection.getOutputStream();
//                output.write(params.getBytes("UTF-8"));
//
//                if (csrfToken != null) {
//                    String token = "csrfToken=" + csrfToken;
//                    output.write(token.getBytes("UTF-8"));
//                }
//
//                output.close();
//
//            }
//
//            is = urlConnection.getInputStream();
//
//            int status = urlConnection.getResponseCode();
//
//            if (status == HttpURLConnection.HTTP_OK) {
//
//                /**
//                 * Do your job
//                 */
//
//            }
//
//        } catch (IllegalArgumentException | NullPointerException | UnsupportedEncodingException | SocketTimeoutException | IOExceptione) {
//            e.printStackTrace();
//        } finally {
//            if (is != null) {
//                is.close();
//            }
//            urlConnection.disconnect();
//        }
//    }

    public static Packet sendPacket(Packet packet) throws IOException, JSONException {
        HttpURLConnection connection = createConnection(packet.getUrl());
        addJsonToConnection(connection, packet.getJsonObject());
        JSONObject response = getResponseFromConnection(connection);
        connection.disconnect();

        return new Packet(packet.getTypePacket(), null, response);
    }

    private static HttpURLConnection createConnection(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");

        return connection;
    }

    private static void addJsonToConnection(HttpURLConnection connection, JSONObject jsonObject) throws IOException {
        Log.v(TAG, jsonObject.toString());
        OutputStream outputStream = connection.getOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        outputStreamWriter.write(jsonObject.toString());
        outputStreamWriter.flush();
        outputStreamWriter.close();
    }

    private static JSONObject getResponseFromConnection(HttpURLConnection connection) throws IOException, JSONException {
        connection.getResponseCode();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return new JSONObject(response.toString());
        }
    }

    private static String getTokenPreferences() {
        return sPreferences.getString(KEY_TOKEN, null);
    }

    private static void setTokenPreferences() {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putString(KEY_TOKEN, sToken);
        editor.apply();
    }

    private static JSONObject createError(String message) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", EStatusCode.NOT_OK.getStrStatusCode());
        jsonObject.put("message", message);

        return jsonObject;
    }
}
