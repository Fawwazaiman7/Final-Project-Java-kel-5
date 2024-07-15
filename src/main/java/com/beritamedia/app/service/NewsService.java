package com.beritamedia.app.service;

import com.beritamedia.app.model.News;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class NewsService {

    private static final String KOMPAS_URL = "https://api.kompas.com/..."; // ganti dengan URL yang benar
    private static final String CNN_URL = "https://api.cnnindonesia.com/..."; // ganti dengan URL yang benar
    private static final String DETIK_URL = "https://api.detik.com/..."; // ganti dengan URL yang benar
    private static final String LIPUTAN_URL = "https://api.liputan.com/..."; // ganti dengan URL yang benar

    private List<News> fetchNewsFromApi(String apiUrl) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(apiUrl);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                ObjectMapper mapper = new ObjectMapper();
                return Arrays.asList(mapper.readValue(jsonResponse, News[].class));
            }
        }
    }

    public List<News> fetchAllNews() {
        List<News> allNews = new ArrayList<>();
        try {
            allNews.addAll(fetchNewsFromApi(KOMPAS_URL));
            allNews.addAll(fetchNewsFromApi(CNN_URL));
            allNews.addAll(fetchNewsFromApi(DETIK_URL));
            allNews.addAll(fetchNewsFromApi(LIPUTAN_URL));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allNews;
    }
}
