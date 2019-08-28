package com.java.news.mybutton;

public class NewsItem {
    private String name;
    private int imageId;

    public NewsItem(String name, int imageId){
        this.name = name;
        this.imageId = imageId;
    }

    public String getName(){
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
