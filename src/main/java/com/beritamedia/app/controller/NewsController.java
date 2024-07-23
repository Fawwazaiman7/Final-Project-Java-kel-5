package com.beritamedia.app.controller;

import com.beritamedia.app.NewsCell;
import com.beritamedia.app.model.NewsItem;
import com.beritamedia.app.controller.BookmarksController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.embed.swing.SwingNode;
import javaswinglogin.Login;
import javaswinglogin.SignUp;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class NewsController {

    @FXML
    private ListView<NewsItem> listView;
    @FXML
    private ImageView refreshIcon;
    @FXML
    private ImageView menuIcon;
    private String userType;
    private String userName;
    @FXML
    private Button btnDaftar;
    @FXML
    private Button btnMasuk;
    @FXML
    private Button btnTerbaru;
    @FXML
    private Button btnEdukasi;
    @FXML
    private Button btnGaya;
    @FXML
    private Button btnEkonomi;
    @FXML
    private Button btnOlahraga;
    @FXML
    private Button btnTeknologi;
    @FXML
    private Button btnHiburan;
    @FXML
    private Button btnTren;
    @FXML
    private MenuButton menuButton;
    @FXML
    private MenuItem menuLogout;
    @FXML
    private MenuItem menuBookmark;

    private boolean isDaftarWindowOpen = false;
    private boolean isLoginWindowOpen = false;

    private static final Map<String, String[]> CATEGORY_MAP = new HashMap<>();
    private final ObservableList<NewsItem> currentNewsItems = FXCollections.observableArrayList();
    private final Map<String, Image> imageCache = new HashMap<>();
    private int currentOffset = 0;
    private static final int PAGE_SIZE = 10;
    private boolean isFetching = false;
    private String currentCategory;

    static {
        CATEGORY_MAP.put("Terbaru", new String[]{"https://api-berita-indonesia.vercel.app/antara/terbaru", "https://api-berita-indonesia.vercel.app/cnbc/terbaru", "https://api-berita-indonesia.vercel.app/cnn/terbaru", "https://api-berita-indonesia.vercel.app/republika/terbaru", "https://api-berita-indonesia.vercel.app/sindonews/terbaru", "https://api-berita-indonesia.vercel.app/tribun/terbaru", "https://api-berita-indonesia.vercel.app/kumparan/terbaru", "https://api-berita-indonesia.vercel.app/jpnn/terbaru", "https://api-berita-indonesia.vercel.app/republika/leisure"});
        CATEGORY_MAP.put("Edukasi", new String[]{"https://api-berita-indonesia.vercel.app/antara/humaniora", "https://api-berita-indonesia.vercel.app/cnbc/opini", "https://api-berita-indonesia.vercel.app/sindonews/sains", "https://api-berita-indonesia.vercel.app/sindonews/edukasi", "https://api-berita-indonesia.vercel.app/tempo/creativelab"});
        CATEGORY_MAP.put("Gaya", new String[]{"https://api-berita-indonesia.vercel.app/antara/lifestyle", "https://api-berita-indonesia.vercel.app/cnbc/lifestyle", "https://api-berita-indonesia.vercel.app/cnn/gayaHidup", "https://api-berita-indonesia.vercel.app/tempo/gaya"});
        CATEGORY_MAP.put("Ekonomi", new String[]{"https://api-berita-indonesia.vercel.app/antara/ekonomi", "https://api-berita-indonesia.vercel.app/cnn/ekonomi", "https://api-berita-indonesia.vercel.app/cnbc/market", "https://api-berita-indonesia.vercel.app/cnbc/investment", "https://api-berita-indonesia.vercel.app/sindonews/ekbis", "https://api-berita-indonesia.vercel.app/tempo/bisnis", "https://api-berita-indonesia.vercel.app/cnbc/entrepreneur"});
        CATEGORY_MAP.put("Olahraga", new String[]{"https://api-berita-indonesia.vercel.app/cnn/olahraga", "https://api-berita-indonesia.vercel.app/republika/bola", "https://api-berita-indonesia.vercel.app/sindonews/sports", "https://api-berita-indonesia.vercel.app/tempo/bola"});
        CATEGORY_MAP.put("Teknologi", new String[]{"https://api-berita-indonesia.vercel.app/antara/tekno", "https://api-berita-indonesia.vercel.app/cnn/teknologi", "https://api-berita-indonesia.vercel.app/cnbc/tech", "https://api-berita-indonesia.vercel.app/sindonews/tekno", "https://api-berita-indonesia.vercel.app/tempo/tekno"});
        CATEGORY_MAP.put("Hiburan", new String[]{"https://api-berita-indonesia.vercel.app/cnn/hiburan", "https://api-berita-indonesia.vercel.app/tempo/seleb"});
        CATEGORY_MAP.put("Trend", new String[]{"https://berita-indo-api-next.vercel.app/api/antara-news/top-news"});
    }

    @FXML
    public void initialize() {
        initializeDropdown();

        menuButton.setVisible(false);

        listView.setItems(currentNewsItems);
        listView.setCellFactory(param -> new NewsCell(imageCache));

        listView.setOnMouseClicked(this::handleNewsItemClick);

        addScrollListener();

        refreshIcon.setOnMouseClicked(e -> handleRefresh());
        menuIcon.setOnMouseClicked(e -> handleMenuClick());
        menuBookmark.setOnAction(event -> handleBookmark());
        menuLogout.setOnAction(event -> handleLogout());

        btnTerbaru.setOnAction(e -> setCategory("Terbaru"));
        btnEdukasi.setOnAction(e -> setCategory("Edukasi"));
        btnGaya.setOnAction(e -> setCategory("Gaya"));
        btnEkonomi.setOnAction(e -> setCategory("Ekonomi"));
        btnOlahraga.setOnAction(e -> setCategory("Olahraga"));
        btnTeknologi.setOnAction(e -> setCategory("Teknologi"));
        btnHiburan.setOnAction(e -> setCategory("Hiburan"));
        btnTren.setOnAction(e -> setCategory("Trend"));

        // Set category to "Terbaru" on initialization
        setCategory("Terbaru");
    }

    @FXML
    private void handleNewsItemClick(MouseEvent event) {
        NewsItem selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/beritamedia/app/NewsDetailView.fxml"));
                AnchorPane root = loader.load();

                NewsDetailController detailController = loader.getController();
                detailController.loadNews(selectedItem); // Kirim objek NewsItem

                Stage stage = new Stage();
                stage.setTitle(selectedItem.getTitle());
                stage.setScene(new Scene(root, 800, 600));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initializeDropdown() {
        System.out.println("Initializing Dropdown...");
        System.out.println("menuButton: " + menuButton);
        System.out.println("menuLogout: " + menuLogout);
        System.out.println("menuBookmark: " + menuBookmark);

        if (menuLogout != null) {
            menuLogout.setOnAction(event -> handleLogout());
        } else {
            System.out.println("menuLogout is null");
        }

        if (menuBookmark != null) {
            menuBookmark.setOnAction(event -> handleBookmark());
        } else {
            System.out.println("menuBookmark is null");
        }

        if ("admin".equals(userType)) {
            if (menuButton != null) {
                menuButton.setText("Admin");
            } else {
                System.out.println("menuButton is null");
            }
        } else {
            if (menuButton != null) {
                menuButton.setText(userName);
            } else {
                System.out.println("menuButton is null");
            }
        }
    }

    private void disableButtons() {
        btnTerbaru.setDisable(true);
        btnEdukasi.setDisable(true);
        btnGaya.setDisable(true);
        btnEkonomi.setDisable(true);
        btnOlahraga.setDisable(true);
        btnTeknologi.setDisable(true);
        btnHiburan.setDisable(true);
        btnTren.setDisable(true);
    }

    private void enableButtons() {
        btnTerbaru.setDisable(false);
        btnEdukasi.setDisable(false);
        btnGaya.setDisable(false);
        btnEkonomi.setDisable(false);
        btnOlahraga.setDisable(false);
        btnTeknologi.setDisable(false);
        btnHiburan.setDisable(false);
        btnTren.setDisable(false);
    }

    private void setButtonsDisabled(boolean disabled) {
        btnTerbaru.setDisable(disabled);
        btnEdukasi.setDisable(disabled);
        btnGaya.setDisable(disabled);
        btnEkonomi.setDisable(disabled);
        btnOlahraga.setDisable(disabled);
        btnTeknologi.setDisable(disabled);
        btnHiburan.setDisable(disabled);
        btnTren.setDisable(disabled);

        BoxBlur blur = new BoxBlur();
        blur.setWidth(disabled ? 5 : 0);
        blur.setHeight(disabled ? 5 : 0);
        blur.setIterations(disabled ? 3 : 0);

        btnTerbaru.setEffect(blur);
        btnEdukasi.setEffect(blur);
        btnGaya.setEffect(blur);
        btnEkonomi.setEffect(blur);
        btnOlahraga.setEffect(blur);
        btnTeknologi.setEffect(blur);
        btnHiburan.setEffect(blur);
        btnTren.setEffect(blur);
    }

    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("Anda telah logout.");
        alert.showAndWait();
        Platform.runLater(() -> {
            btnDaftar.setVisible(true);
            btnMasuk.setVisible(true);
            menuButton.setVisible(false);
        });
    }

    private void handleBookmark() {
        try {
            // Load the Bookmarks.fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/beritamedia/app/Bookmarks.fxml"));
            Parent root = loader.load();

            // Create a new stage for the Bookmarks view
            Stage stage = new Stage();
            stage.setTitle("Bookmarks");
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleDaftarButtonAction() {
        disableButtons();
        SwingUtilities.invokeLater(() -> {
            SignUp.display();
            Platform.runLater(this::enableButtons);
        });
    }

    @FXML
    private void handleMasukAction() {
        disableButtons();
        SwingUtilities.invokeLater(() -> {
            Login.display();
            Platform.runLater(this::enableButtons);
        });
    }


    @FXML
    private void handleRefresh() {
        currentOffset = 0;
        currentNewsItems.clear();
        fetchNewsData();
    }

    public void handleMenuClick() {
        menuButton.show();
    }

    public void setCategory(String category) {
        this.currentCategory = category;
        handleRefresh();
    }

    public void updateUIAfterLogin(String userType, String userName) {
        this.userType = userType;
        this.userName = userName;

        // Mengatur visibilitas tombol
        btnDaftar.setVisible(false);
        btnMasuk.setVisible(false);
        menuButton.setVisible(true);

        // Mengatur teks pada menuButton
        if ("admin".equals(userType)) {
            menuButton.setText("Admin");
        } else {
            menuButton.setText(userName);
        }

        // Mengaktifkan kembali tombol-tombol dan menghapus pemburamannya
        setButtonsDisabled(false);
        System.out.println("UI updated after login.");
    }

    private void fetchNewsData() {
        if (currentCategory == null) {
            return;
        }

        isFetching = true;
        ObservableList<NewsItem> newsItems = FXCollections.observableArrayList();
        String[] sources = CATEGORY_MAP.get(currentCategory);
        boolean isTrendCategory = currentCategory.equals("Trend");

        for (String source : sources) {
            String apiUrl = source.startsWith("http") ? source : "https://api-berita-indonesia.vercel.app/" + source;

            try {
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String response = in.lines().collect(Collectors.joining());
                    in.close();

                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("data")) {
                        Object dataObject = jsonResponse.get("data");

                        if (dataObject instanceof JSONArray articles) {
                            for (int i = currentOffset; i < Math.min(currentOffset + PAGE_SIZE, articles.length()); i++) {
                                JSONObject article = articles.getJSONObject(i);
                                addNewsItem(newsItems, article, isTrendCategory);
                            }
                        } else if (dataObject instanceof JSONObject articlesObject) {
                            for (String key : articlesObject.keySet()) {
                                Object articlesArray = articlesObject.get(key);
                                if (articlesArray instanceof JSONArray articles) {
                                    for (int i = currentOffset; i < Math.min(currentOffset + PAGE_SIZE, articles.length()); i++) {
                                        JSONObject article = articles.getJSONObject(i);
                                        addNewsItem(newsItems, article, isTrendCategory);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    System.err.println("Error fetching data from URL: " + apiUrl + " Response Code: " + responseCode);
                }
            } catch (IOException e) {
                System.err.println("Error fetching data from URL: " + apiUrl);
                System.err.println(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Platform.runLater(() -> {
            currentNewsItems.addAll(newsItems);
            isFetching = false;
        });
        currentOffset += PAGE_SIZE;
    }

    private void fetchMoreNewsData() {
        if (!isFetching) {
            fetchNewsData();
        }
    }

    private void addScrollListener() {
        listView.skinProperty().addListener((obs, oldSkin, newSkin) -> {
            if (newSkin != null) {
                ScrollBar scrollBar = (ScrollBar) listView.lookup(".scroll-bar:vertical");
                if (scrollBar != null) {
                    scrollBar.valueProperty().addListener((observable, oldValue, newValue) -> {
                        if (scrollBar.getMax() == newValue.doubleValue() && !isFetching) {
                            fetchMoreNewsData();
                        }
                    });
                }
            }
        });
    }

    private void addNewsItem(ObservableList<NewsItem> newsItems, JSONObject article, boolean isTrendCategory) {
        String title = article.optString("title", "No Title");
        String link = article.optString("link", "No Link");
        String thumbnail;
        if (isTrendCategory) {
            thumbnail = article.optString("image", "");
        } else {
            thumbnail = article.optString("thumbnail", "");
        }
        String description = article.optString("description", "No Description");
        String pubDate = article.optString("pubDate", "Unknown Date");

        // Use cached image if available, otherwise load and cache it
        Image image = imageCache.get(thumbnail);
        if (image == null && !thumbnail.isEmpty()) {
            image = new Image(thumbnail, true);
            imageCache.put(thumbnail, image);
        }

        newsItems.add(new NewsItem(title, link, thumbnail, description, pubDate));
    }



}


