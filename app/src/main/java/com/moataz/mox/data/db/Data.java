package com.moataz.mox.data.db;

public class Data {
    private String title;
    private String ImageUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public Data(String imageUrl, String title) {
        this.title = title;
        ImageUrl = imageUrl;
    }
}
