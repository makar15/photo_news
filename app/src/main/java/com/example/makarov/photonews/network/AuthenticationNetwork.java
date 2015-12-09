package com.example.makarov.photonews.network;


import android.content.Context;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by makarov on 08.12.15.
 */
public class AuthenticationNetwork {

    private static final String AUTH_URL = "https://api.instagram.com/oauth/authorize/";
    private static final String TOKEN_URL = "https://api.instagram.com/oauth/access_token";

    public static final String VERSION_API_URL = "https://api.instagram.com/v1";
    public static final String INSTAGRAM_CLIENT_ID = "e5dfb4aea090409f84010545da79f75a";
    public static final String INSTAGRAM_CLIENT_SECRET = "79debcc250604d31884c133e19a95165";
    public static final String CALLBACK_URL = "http://vk.com/id37778527";

    private final String mAuthUrl;
    private final String mTokenUrl;

    private String mAccessToken;
    private InstagramSession mSession;
    private InstagramDialog instagramDialog;

    public AuthenticationNetwork(Context context) {
        mAuthUrl = generationAuthUrl();
        mTokenUrl = generationTokenUrl();

        mSession = new InstagramSession(context);
        instagramDialog = new InstagramDialog(context, mAuthUrl);

        getAccessToken();

    }

    private String generationAuthUrl() {
        return AUTH_URL + "?client_id=" + INSTAGRAM_CLIENT_ID + "&redirect_uri=" + CALLBACK_URL +
                "&response_type=code&display=touch&scope=likes+comments+relationships";
    }

    private String generationTokenUrl() {
        return TOKEN_URL + "?client_id=" + INSTAGRAM_CLIENT_ID + "&client_secret=" +
                INSTAGRAM_CLIENT_SECRET + "&redirect_uri=" + CALLBACK_URL + "&grant_type=authorization_code";
    }

    public String getAuthUrl() {
        return mAuthUrl;
    }

    public String getTokenUrl() {
        return mTokenUrl;
    }

    private void getAccessToken() {
        new Thread() {
            @Override
            public void run() {

                try {
                    URL url = new URL(mTokenUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);

                    OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                    writer.write("client_id=" + INSTAGRAM_CLIENT_ID +
                            "&client_secret=" + INSTAGRAM_CLIENT_SECRET +
                            "&grant_type=authorization_code" +
                            "&redirect_uri=" + CALLBACK_URL +
                            "&code=" + instagramDialog.getRequestToken());
                    writer.flush();

                    String response = streamToString(urlConnection.getInputStream());

                    JSONObject jsonObj = (JSONObject) new JSONTokener(response).nextValue();

                    mAccessToken = jsonObj.getString("access_token");
                    String id = jsonObj.getJSONObject("user").getString("id");
                    String user = jsonObj.getJSONObject("user").getString("username");
                    String name = jsonObj.getJSONObject("user").getString("full_name");

                    mSession.storeAccessToken(mAccessToken, id, user, name);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public String streamToString(InputStream is) throws IOException {
        String string = "";

        if(is != null){
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            try{
                BufferedReader reader=new BufferedReader(
                        new InputStreamReader(is));

                while((line = reader.readLine()) != null){
                    stringBuilder.append(line);
                }

                reader.close();
            } finally{
                is.close();
            }

            string = stringBuilder.toString();
        }

        return string;
    }
}
