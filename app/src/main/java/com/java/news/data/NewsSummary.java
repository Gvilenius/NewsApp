package com.java.news.data;
/*
 * Created by ljf on 2019-8-29.
 */

import java.util.List;

public class NewsSummary
{


    private String newsID;
    private String publishTime;
    private String language;
    private String titile;
    private String crawlTime;
    private String publisher;
    private String catagory;


    public String getLanguage() {
        return language;
    }

    public String getTitile() {
        return titile;
    }

    public String getCrawlTime() {
        return crawlTime;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getCatagory() {
        return catagory;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public String getNewsID(){
        return newsID;
    }

}