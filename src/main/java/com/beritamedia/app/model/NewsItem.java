package com.beritamedia.app.model;

public class NewsItem {
    private String title;
    private String link;
    private String thumbnail;
    private String description;
    private String pubDate;

    public NewsItem(String title, String link, String thumbnail, String description, String pubDate) {
        this.title = title;
        this.link = link;
        this.thumbnail = thumbnail;
        this.description = description;
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public String getPubDate() {
        return pubDate;
    }
}
