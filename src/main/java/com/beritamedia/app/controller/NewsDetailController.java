package com.beritamedia.app.controller;

import com.beritamedia.app.model.NewsItem;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.net.URI;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class NewsDetailController implements Initializable {

    @FXML
    private WebView webView;
    @FXML
    private Button backButton;
    @FXML
    private ImageView backIcon;
    @FXML
    private ImageView bookmarkIcon;
    @FXML
    private ImageView openBrowserIcon;
    private final String dbUrl = "jdbc:mysql://localhost:3306/bookmark"; // Update with your database URL
    private final String dbUser = "root"; // Update with your database user
    private final String dbPassword = ""; // Update with your database password
    private NewsItem currentNewsItem;
    private String currentNewsUrl;
    private String currentNewsTitle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Make WebView resize with AnchorPane
        AnchorPane.setTopAnchor(webView, 30.0);
        AnchorPane.setBottomAnchor(webView, 0.0);
        AnchorPane.setLeftAnchor(webView, 0.0);
        AnchorPane.setRightAnchor(webView, 0.0);

        // Load icons
        backIcon.setImage(new Image(getClass().getResourceAsStream("/com/beritamedia/app/back.png")));
        bookmarkIcon.setImage(new Image(getClass().getResourceAsStream("/com/beritamedia/app/bookmark.png")));
        openBrowserIcon.setImage(new Image(getClass().getResourceAsStream("/com/beritamedia/app/browser.png")));
    }

    public void loadNews(NewsItem newsItem) {
        this.currentNewsItem = newsItem;
        Platform.runLater(() -> {
            try {
                WebEngine webEngine = webView.getEngine();
                webEngine.setOnError(event -> {
                    showAlert(null, "Error loading content", event.getMessage());
                });
                webEngine.load(newsItem.getLink());
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(null, "Error loading content", e.getMessage());
            }
        });
    }

    public void loadBookmarkNews(String url, String title) {
        this.currentNewsUrl = url;
        this.currentNewsTitle = title;
        Platform.runLater(() -> {
            try {
                WebEngine webEngine = webView.getEngine();
                webEngine.setOnError(event -> {
                    showAlert(null, "Error loading content", event.getMessage());
                });
                webEngine.load(url);
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(null, "Error loading content", e.getMessage());
            }
        });
    }

    @FXML
    private void handleBack() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleBookmark() {
        if (currentNewsItem == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "No news item to bookmark.");
            return;
        }

        String url = currentNewsItem.getLink();
        String title = currentNewsItem.getTitle();
        int userId = getNextUserId();

        // Periksa apakah bookmark sudah ada
        if (isBookmarkExists(userId, url, dbUrl, dbUser, dbPassword)) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Bookmark already exists.");
            return;
        }

        String query = "INSERT INTO bookmark (user_id, link_berita, title) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, url);
            pstmt.setString(3, title);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Bookmark added successfully!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add bookmark.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while adding the bookmark: " + e.getMessage());
        }
    }

    private int getNextUserId() {
        String query = "SELECT MAX(user_id) FROM bookmark";
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while retrieving the next user ID: " + e.getMessage());
        }
        return 1;  // Default to 1 if no records are found
    }

    private boolean isBookmarkExists(int userId, String url, String dbUrl, String dbUser, String dbPassword) {
        String query = "SELECT COUNT(*) FROM bookmark WHERE user_id = ? AND link_berita = ?";
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, url);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while checking the bookmark: " + e.getMessage());
        }
        return false;
    }

    @FXML
    private void handleOpenBrowser() {
        try {
            Desktop.getDesktop().browse(new URI(webView.getEngine().getLocation()));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.INFORMATION, "Error", "Unable to open browser.");
        }
    }

    private void showAlert(Alert.AlertType information, String title, String message) {
        Alert alert = new Alert(information);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
