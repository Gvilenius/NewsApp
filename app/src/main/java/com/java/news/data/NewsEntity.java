package com.java.news.data;
/*
 * Created by ljf on 2019-8-29.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class NewsEntity extends RealmObject{

    @PrimaryKey
    private String newsID;


    private String content;
    private String publishTime;
    private String title;
    private String publisher;
    private String category;
    private RealmList<String> imgUrls;

    @Ignore
    private String image;

    public String getImage() {
        return image;
    }

    public RealmList<String> getImgUrls(){
        if (imgUrls == null) {
            String pattern =  "http.*\\.((jpe?g)|(png))";
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(image);
            RealmList<String> urls = new RealmList<>();
            while(m.find()){
                urls.add(m.group(0));
            }
            setImgUrls(urls);
        }
        return imgUrls;
    }

    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getCategory() {
        return category;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public String getNewsID(){
        return newsID;
    }

    public String getContent() {
        return content;
    }

    public void setNewsID(String newsID) {
        this.newsID = newsID;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setImgUrls(RealmList<String> imgUrls) {
        this.imgUrls = imgUrls;
    }
//    private String crawlTime;
//    public String getCrawlTime() {
//        return crawlTime;
//    }
//    private List<keyword> keywords;
//    private List<keyword> when;
//    private List<person> persons;
//    private List<person> organizations;
//    private List<location> locations;
//    private List<keyword> where;
//    private List<keyword> who;
//
//    private static class location{
//        private float lng;
//        private int count;
//        private String linkedURL;
//        private float lat;
//        private String mention;
//    }
//    private static class person{
//        private int count;
//        private String linkedURL;
//        private String mention;
//    }
//    private static class keyword{
//        private String word;
//        private String score;
//    }

    //    private String language;
//    public String getLanguage() {
//        return language;
//    }

}
