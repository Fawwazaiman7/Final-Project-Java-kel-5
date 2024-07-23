package com.beritamedia.app.controller;

import com.beritamedia.app.BookmarkCell;
import com.beritamedia.app.model.Bookmark;
import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.sql.*;

public class BookmarksController {

    @FXML
    private ListView<Bookmark> bookmarkListView;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button clearButton;

    private HostServices hostServices;
    private final String dbUrl = "jdbc:mysql://localhost:3306/bookmark"; // Update with your database URL
    private final String dbUser = "root"; // Update with your database user
    private final String dbPassword = ""; // Update with your database password

    // Default constructor needed for JavaFX
    public BookmarksController() {
    }

    // Constructor to set HostServices
    public BookmarksController(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    @FXML
    public void initialize() {
        // Load bookmarks from database
        ObservableList<Bookmark> bookmarks = loadBookmarksFromDatabase();
        bookmarkListView.setItems(bookmarks);

        // Set custom cell factory to the ListView
        bookmarkListView.setCellFactory(param -> new BookmarkCell(hostServices));

        // Set focus to the root pane to avoid blocking any specific item in the ListView
        rootPane.requestFocus();

        // Set clear button action
        clearButton.setOnAction(event -> clearAllBookmarks());
    }

    private void clearAllBookmarks() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Apakah Anda yakin ingin mengosongkan semua bookmark?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            // Clear ListView
            bookmarkListView.getItems().clear();

            // Clear database
            clearDatabase();
        }
    }

    private void clearDatabase() {
        String query = "DELETE FROM bookmark";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ObservableList<Bookmark> loadBookmarksFromDatabase() {
        ObservableList<Bookmark> bookmarks = FXCollections.observableArrayList();
        String query = "SELECT title, link_berita FROM bookmark";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String title = rs.getString("title");
                String link = rs.getString("link_berita");
                bookmarks.add(new Bookmark(title, link));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while loading bookmarks: " + e.getMessage());
        }

        return bookmarks;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
