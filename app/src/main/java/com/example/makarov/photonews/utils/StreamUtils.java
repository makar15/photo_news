package com.example.makarov.photonews.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class StreamUtils {

    //открыть соединения для получения потока данных
    public static boolean openHttpUrlConnection(HttpURLConnection urlConnection, URL url) throws IOException {
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();
        return urlConnection.getResponseCode() == 200;
    }

    //получить входящий поток данных
    public static String urlToString(URL url) throws IOException {
        InputStream inputStream = url.openConnection().getInputStream();
        return streamToString(inputStream);
    }

    //преобразовать поток в строку
    private static String streamToString(InputStream is) throws IOException {
        String str = "";

        if (is != null) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

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

}
