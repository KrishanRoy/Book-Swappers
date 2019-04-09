package com.example.krishanroy.bookswappers.ui.model;

public class TextMessage {
    private String message;
    private String name;
    private String imageUrl;

    public TextMessage(String message, String name, String imageUrl) {
        this.message = message;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
