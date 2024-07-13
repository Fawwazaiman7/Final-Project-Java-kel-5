package com.beritamedia.app.controller;

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
import java.util.ResourceBundle;

public class NewsDetailController implements Initializable {

    @FXML
    private WebView webView;
    @FXML
    private Button backButton;
    @FXML
    private Button bookmarkButton;
    @FXML
    private ImageView backIcon;
    @FXML
    private ImageView bookmarkIcon;
    @FXML
    private ImageView openBrowserIcon;

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

    public void loadNews(String url) {
        Platform.runLater(() -> {
            try {
                WebEngine webEngine = webView.getEngine();
                webEngine.setOnError(event -> {
                    showAlert("Error loading content", event.getMessage());
                });
                webEngine.load(url);
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error loading content", e.getMessage());
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
        // Implement bookmark logic here
        showAlert("Bookmark", "This feature is not implemented yet.");
    }

    @FXML
    private void handleOpenBrowser() {
        try {
            Desktop.getDesktop().browse(new URI(webView.getEngine().getLocation()));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Unable to open browser.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
