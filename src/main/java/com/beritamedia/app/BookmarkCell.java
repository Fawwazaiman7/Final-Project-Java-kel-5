package com.beritamedia.app;

import com.beritamedia.app.controller.NewsDetailController;
import com.beritamedia.app.model.Bookmark;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class BookmarkCell extends ListCell<Bookmark> {
    private HBox content;
    private Hyperlink link;
    private Button deleteButton;
    private HostServices hostServices;

    public BookmarkCell(HostServices hostServices) {
        super();
        this.hostServices = hostServices;
        link = new Hyperlink();
        link.setOnAction(event -> {
            openNewsDetail(link.getUserData().toString(), link.getText());
        });
        deleteButton = new Button("Hapus");
        deleteButton.setStyle("-fx-background-color: #FF3333; -fx-text-fill: white;");
        deleteButton.setOnAction(event -> {
            getListView().getItems().remove(getItem());
            deleteFromDatabase(getItem());
        });

        content = new HBox(link, deleteButton);
        content.setSpacing(10);
    }

    private void openNewsDetail(String url, String title) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/beritamedia/app/NewsDetailView.fxml"));
            AnchorPane root = loader.load();

            NewsDetailController detailController = loader.getController();
            detailController.loadBookmarkNews(url, title);

            Stage stage = new Stage();
            stage.setTitle(title);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteFromDatabase(Bookmark bookmark) {
        // Code to delete bookmark from the database
    }

    @Override
    protected void updateItem(Bookmark item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            link.setText(item.getTitle());
            link.setUserData(item.getLink());
            setGraphic(content);
        } else {
            setGraphic(null);
        }
    }
}
