package com.java.news.data;

import io.realm.RealmList;
import io.realm.RealmObject;

/*
 * Created by ljf on 2019-9-5.
 */

public interface  News{
    public  RealmList<String> getImgUrls();
    
    public  String getTitle();

    public  String getPublisher();

    public  String getCategory();

    public  String getPublishTime();

    public  String getNewsID();

    public RealmList<Keyword> getKeywords();

}
