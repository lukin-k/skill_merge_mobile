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
    private final static String SERVER_URL = "http://192.168.31.145:8000/core_backend/";
    private final static String ACTION_CREATE_USER = "create_user/";
    private final static String ACTION_LOGIN = "login/";
    private final static String ACTION_GET_USER_INFO = "get_user_info/";

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

    public static Packet singIn(String login, String password) {
        Packet packet = sing(login, password);

        if (packet.getTypePacket() == ETypePacket.BAD) {
            return packet;
        }

        try {
            packet.setUrl(new URL(SERVER_URL + ACTION_LOGIN));
        } catch (MalformedURLException e) {
            packet.setTypePacket(ETypePacket.BAD);
        }
        packet.setTypePacket(ETypePacket.SING_IN);

        return packet;
    }

    public static Packet singUp(String login, String password) {
        Packet packet = sing(login, password);

        if (packet.getTypePacket() == ETypePacket.BAD) {
            return packet;
        }

        try {
            packet.setUrl(new URL(SERVER_URL + ACTION_CREATE_USER));
        } catch (MalformedURLException e) {
            packet.setTypePacket(ETypePacket.BAD);
        }
        packet.setTypePacket(ETypePacket.SING_UP);

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

        Log.v(TAG, packet.getJsonObject().toString());

        return packet;
    }

    private static Packet sing(String login, String password) {
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
