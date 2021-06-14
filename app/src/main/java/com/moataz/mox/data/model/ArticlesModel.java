package com.moataz.mox.data.model;

public class ArticlesModel {

    private String urlToImage;
    private String title;
    private String details;
    private String pages;
    private String author;
    private String content;
    private String url;

    public ArticlesModel(String urlToImage, String title, String description, String name, String author, String content, String url) {
        this.urlToImage = urlToImage;
        this.title = title;
        this.details = description;
        this.pages = name;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
