package com.example.test.model;

public class News {

    String date;
    String link;
    String image_link;
    String title;
    String resume;
    String image_content;
    String image_content_type;

    public News(String date, String link, String image_link, String title, String resume, String image_content, String image_content_type) {
        this.date = date;
        this.link = link;
        this.image_link = image_link;
        this.title = title;
        this.resume = resume;
        this.image_content = image_content;
        this.image_content_type = image_content_type;

    }
}
