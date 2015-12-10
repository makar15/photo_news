package com.example.makarov.photonews.network;


import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

    private final String mAccessToken = "175770414.98d6195.7e44bb27685141c5ba834cb7dcd67625";

    private final String mAuthUrl;
    private final String mTokenUrl;

    private List<String> mUrlImages;
    private HttpURLConnection urlConnection = null;
    private URL mUrlSavePhotosTag;

    public AuthenticationNetwork() throws IOException, JSONException {
        mAuthUrl = getGenerationAuthUrl();
        mTokenUrl = getGenerationTokenUrl();

        mUrlImages = new ArrayList<>();
        mUrlSavePhotosTag = getGenerationUrlAccessPhotosTag();
        new ParseTask().execute();
    }

    //URL аутентификации пользователя APIinstagram
    private String getGenerationAuthUrl() {
        return AUTH_URL + "?client_id=" + INSTAGRAM_CLIENT_ID + "&redirect_uri=" + CALLBACK_URL +
                "&response_type=code&display=touch&scope=likes+comments+relationships";
    }

    //URL для маркера доступа пользователя APIinstagram
    private String getGenerationTokenUrl() {
        return TOKEN_URL + "?client_id=" + INSTAGRAM_CLIENT_ID + "&client_secret=" +
                INSTAGRAM_CLIENT_SECRET + "&redirect_uri=" + CALLBACK_URL + "&grant_type=authorization_code";
    }

    public List<String> getUrlImages() {
        return mUrlImages;
    }


    //получить URL для загрузки изображений по тэгу
    public URL getGenerationUrlAccessPhotosTag() throws MalformedURLException {

        String urlString = VERSION_API_URL+"/tags/" + "двоевкадрелучшечемодин" +
                "/media/recent/?access_token=" + mAccessToken;
        return new URL(urlString);
    }

    //открыть соединения для получения потока данных
    public void openHttpUrlConnection(URL url) throws IOException {
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();
    }

    //получить входящий поток данных
    public String getStreamUrl(URL url) throws IOException {
        InputStream inputStream= url.openConnection().getInputStream();
        return streamToString(inputStream);
    }

    //преобразовать поток в строку
    private String streamToString(InputStream is) throws IOException {
        String str = "";

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

            str = stringBuilder.toString();
        }

        return str;
    }

    //преобразование строку в JSON
    public JSONArray getStringToJSONArray(String response) throws JSONException {
        JSONObject jsonObject=(JSONObject)new JSONTokener(response).nextValue();
        return jsonObject.getJSONArray("data");
    }

    //получить URL картинки из JSON
    public String getUrlImage(JSONArray jsonArray, int indexImage) throws JSONException {
        JSONObject imageJsonObject=
                jsonArray.getJSONObject(indexImage).getJSONObject("images").getJSONObject("standard_resolution");
        return imageJsonObject.getString("url");
    }


    public class ParseTask extends AsyncTask<Void, Void, String>{

        private String resultJson = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... params) {
            try {

                openHttpUrlConnection(mUrlSavePhotosTag);
                resultJson = getUrlImage(getStringToJSONArray(getStreamUrl(mUrlSavePhotosTag)),0);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return resultJson;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            mUrlImages.add(result);
        }
    }
}
