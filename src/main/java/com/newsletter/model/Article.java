package com.newsletter.model;

public class Article {
    private String category;
    private String title;
    private String description;
    private String author;
    private String urlToImage;
    private String publishedAt;

        public Article() {}

    public Article(String category, String title, String description, 
                  String author, String urlToImage, String publishedAt) {
        this.category = category;
        this.title = title;
        this.description = description;
        this.author = author;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    // Getters
    public String getCategory() { return category; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getAuthor() { return author; }
    public String getUrlToImage() { return urlToImage; }
    public String getPublishedAt() { return publishedAt; }
}