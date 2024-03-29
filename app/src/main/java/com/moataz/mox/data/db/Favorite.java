package com.moataz.mox.data.db;

import java.io.Serializable;

public class Favorite implements Serializable {
    private int id;
    private String title;
    private String image;
    private String link;
    private String author;

    public Favorite() {
    }

    public Favorite(int id, String title, String image , String link, String author) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.link = link;
        this.author = author;
    }

    public Favorite( String title, String image , String link , String author) {
        this.title = title;
        this.image = image;
        this.link = link;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getLink() {
        return link;
    }

    public String getAuthor() {
        return author;
    }

}
