package com.example.makarov.photonews.network;

import com.example.makarov.photonews.MyApp;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by makarov on 11.12.15.
 */
public class RequestService {

    private final String VERSION_API_URL = "https://api.instagram.com/v1";
    private final String ACCESS_TOKEN = "175770414.98d6195.7e44bb27685141c5ba834cb7dcd67625";

    private URL mUrlSavePhotosTag;
    private List<String> mUrlImages;
    private ParsingTask parsingTask;

    public RequestService() {
        mUrlImages = new ArrayList<>();
    }

    public void requestPhotosTag(String lineTag) throws MalformedURLException {
        mUrlSavePhotosTag = getGenerationUrlAccessPhotosTag(lineTag);
        parsingTask = (ParsingTask) new ParsingTask().execute(mUrlSavePhotosTag);

        getResultParsing();
    }

    public void nextRequestPhotosTag(String lineTag) throws MalformedURLException {
        mUrlSavePhotosTag = getGenerationNextUrlAccessPhotosTag(lineTag);
        parsingTask = (ParsingTask) new ParsingTask().execute(mUrlSavePhotosTag);

        getResultParsing();
    }

    //получить URL для загрузки изображений по тэгу
    public URL getGenerationUrlAccessPhotosTag(String lineTag) throws MalformedURLException {

        String url = VERSION_API_URL + "/tags/" + lineTag +
                "/media/recent/?access_token=" + ACCESS_TOKEN;
        return new URL(url);
    }

    public URL getGenerationNextUrlAccessPhotosTag(String lineTag) throws MalformedURLException {

        String nextUrl = VERSION_API_URL + "/tags/" + lineTag + "/media/recent/?access_token=" +
                ACCESS_TOKEN + "&max_tag_id=" + MyApp.getApp().getParsing().getNextMaxId();
        return new URL(nextUrl);
    }

    //получить результат парсинга запрошенного тэга, в виде массива с URL адресами фото
    private void getResultParsing() {

        if (parsingTask == null) return;

        try {
            mUrlImages = parsingTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public List<String> getUrlImages() {
        return mUrlImages;
    }
}
