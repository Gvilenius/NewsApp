package com.java.news.data;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/*
 * Created by ljf on 2019-9-4.
 */

public class NewsFavorEntity extends RealmObject {


    @PrimaryKey
    private String newsID;

    private RealmList<String> ImgUrls;

    private String title;

    public  NewsFavorEntity(){}
    public NewsFavorEntity(NewsEntity news){
        newsID = news.getNewsID();
        ImgUrls = news.getImgUrls();
        title = news.getTitle();
    }

    public String getNewsID() {
        return newsID;
    }

    public void setNewsID(String newsID) {
        this.newsID = newsID;
    }

    public RealmList<String> getImgUrls() {
        return ImgUrls;
    }

    public void setImgUrls(RealmList<String> imgUrls) {
        ImgUrls = imgUrls;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
