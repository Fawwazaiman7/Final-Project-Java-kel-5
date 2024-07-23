package com.beritamedia.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/beritamedia/app/TampilanUtama.fxml"));
            Parent root = loader.load();



            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/com/beritamedia/app/logo.png")));

            primaryStage.setTitle("BeritaKita");
            primaryStage.setMaximized(true);
            primaryStage.centerOnScreen(); // Posisikan jendela di tengah layar
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
