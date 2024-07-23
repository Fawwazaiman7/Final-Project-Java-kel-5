package com.beritamedia.app.model;

public class Bookmark {
    private final String title;
    private final String link;

    public Bookmark(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }
}
