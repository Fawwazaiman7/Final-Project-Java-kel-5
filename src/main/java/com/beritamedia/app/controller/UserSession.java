package com.beritamedia.app.controller;

public class UserSession {
    private static UserSession instance;
    private int userId;

    private UserSession(int userId) {
        this.userId = userId;
    }

    public static UserSession getInstance(int userId) {
        if (instance == null) {
            instance = new UserSession(userId);
        }
        return instance;
    }

    public static UserSession getInstance() {
        if (instance == null) {
            throw new IllegalStateException("User session not initialized");
        }
        return instance;
    }

    public int getUserId() {
        return userId;
    }

    public void clearSession() {
        instance = null;
    }
}


