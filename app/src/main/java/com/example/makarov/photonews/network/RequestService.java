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
import java.util.concurrent.ExecutionException;

/**
 * Created by makarov on 11.12.15.
 */
public class RequestService {

    private final int NUMBER_SINGLE_QUERY = 20;
    private final String PHOTO_STANDART_RESOLUTION = "standard_resolution";
    private final String PHOTO_LOW_RESOLUTION = "low_resolution";
    private final String PHOTO_THUMBNAIL = "thumbnail";

    private final String VERSION_API_URL = "https://api.instagram.com/v1";
    private final String ACCESS_TOKEN = "175770414.98d6195.7e44bb27685141c5ba834cb7dcd67625";

    private HttpURLConnection urlConnection = null;
    private URL mUrlSavePhotosTag;
    private List<String> mUrlImages;
    private ParseTask parseTask;

    public RequestService() {
        mUrlImages = new ArrayList<>();
    }

    public void requestPhotosTag(String lineTag) throws MalformedURLException {
        mUrlSavePhotosTag = getGenerationUrlAccessPhotosTag(lineTag);
        parseTask = (ParseTask) new ParseTask().execute();

        getResultParsing();
    }

    public List<String> getUrlImages() {
        return mUrlImages;
    }

    //получить URL для загрузки изображений по тэгу
    public URL getGenerationUrlAccessPhotosTag(String lineTag) throws MalformedURLException {

        String urlString = VERSION_API_URL + "/tags/" + lineTag +
                "/media/recent/?access_token=" + ACCESS_TOKEN;
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
        InputStream inputStream = url.openConnection().getInputStream();
        return streamToString(inputStream);
    }

    //преобразовать поток в строку
    private String streamToString(InputStream is) throws IOException {
        String str = "";

        if (is != null) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is));

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                reader.close();
            } finally {
                is.close();
            }

            str = stringBuilder.toString();
        }

        return str;
    }

    //преобразование строку в JSON
    public JSONArray getStringToJSONArray(String response) throws JSONException {
        JSONObject jsonObject = (JSONObject) new JSONTokener(response).nextValue();
        return jsonObject.getJSONArray("data");
    }

    //получить URL картинки из JSON
    public String getUrlImage(JSONArray jsonArray, int indexImage) throws JSONException {
        JSONObject imageJsonObject = jsonArray.getJSONObject(indexImage)
                .getJSONObject("images").getJSONObject(PHOTO_STANDART_RESOLUTION);

        //TODO check NullPointerException imageJsonObject
        return imageJsonObject.getString("url");
    }

    //получить результат парсинга запрошенного тэга, в виде массивы с URL адресами фото
    private void getResultParsing() {

        if (parseTask == null) return;

        try {
            mUrlImages = parseTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }


    public class ParseTask extends AsyncTask<Void, Void, List<String>> {

        private List<String> mUrlImages = new ArrayList<>();
        private String resultJson = "";
        private String response;
        private JSONArray json;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                int count = 0;
                response = getStreamUrl(mUrlSavePhotosTag);
                json = getStringToJSONArray(response);
                openHttpUrlConnection(mUrlSavePhotosTag);

                while (count != NUMBER_SINGLE_QUERY) {
                    resultJson = getUrlImage(json, count);

                    if (resultJson.equals("")) {
                        count += (NUMBER_SINGLE_QUERY - count);
                        break;
                    } else {
                        mUrlImages.add(resultJson);
                        count++;
                    }
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return mUrlImages;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            super.onPostExecute(result);

        }
    }
}
