package com.beritamedia.app;

import com.beritamedia.app.model.NewsItem;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Map;

public class NewsCell extends ListCell<NewsItem> {
    private HBox content;
    private Text title;
    private Text description;
    private ImageView imageView;
    private Map<String, Image> imageCache;

    public NewsCell(Map<String, Image> imageCache) {
        super();
        this.imageCache = imageCache;
        title = new Text();
        description = new Text();
        imageView = new ImageView();
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        VBox vBox = new VBox(title, description);
        content = new HBox(imageView, vBox);
        content.setSpacing(10);
    }

    @Override
    protected void updateItem(NewsItem item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            title.setText(item.getTitle());
            description.setText(item.getDescription());
            if (!item.getThumbnail().isEmpty()) {
                Image image = imageCache.get(item.getThumbnail());
                if (image == null) {
                    image = new Image(item.getThumbnail(), true);
                    imageCache.put(item.getThumbnail(), image);
                }
                imageView.setImage(image);
            } else {
                imageView.setImage(null);
            }
            setGraphic(content);
        } else {
            setGraphic(null);
        }
    }
}
