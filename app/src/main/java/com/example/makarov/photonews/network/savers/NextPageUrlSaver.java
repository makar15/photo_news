package com.example.makarov.photonews.network.savers;

/**
 * При сетевом запросе сохраняем url на следующие MediaPosts,
 * и ставим флаг что следующая страничка существует и сохранена.
 * По запросу на последующий url адрес, проверяем флаг и
 * в случае true - страничка сохранена и  её можно получить
 */
public interface NextPageUrlSaver {

    void setUrl(String url);

    String getUrl();

    boolean isNextLoading();
}
