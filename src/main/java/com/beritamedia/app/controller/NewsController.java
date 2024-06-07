package com.beritamedia.app.controller;

import com.beritamedia.app.model.News;
import com.beritamedia.app.service.NewsService;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.List;

public class NewsController {

    @FXML
    private ListView<String> newsListView;

    private NewsService newsService;

    @FXML
    public void initialize() {
        newsService = new NewsService();
    }

    @FXML
    private void fetchNews() throws IOException {
        List<News> newsList = newsService.fetchAllNews();
        newsListView.getItems().clear();
        for (News news : newsList) {
            newsListView.getItems().add(news.getTitle());
        }
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

    @FXML
    private void handleAllNews() throws IOException {
        fetchNews();
    }

    @FXML
    private void handleTechnologyNews() {
        // Implement fetching technology news
    }

    @FXML
    private void handleBusinessNews() {
        // Implement fetching business news
    }

    @FXML
    private void handleEntertainmentNews() {
        // Implement fetching entertainment news
    }

    @FXML
    private void handleAbout() {
        // Implement showing about dialog
    }
}
