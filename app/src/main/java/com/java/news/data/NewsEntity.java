package com.java.news.data;
/*
 * Created by ljf on 2019-8-29.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class NewsEntity implements RealmModel, News {

        @PrimaryKey
        private String newsID;
        private String content;
        private String publishTime;
        private String title;
        private String publisher;
        private String category;

    private String url;

        private Boolean isFavor;

        public NewsEntity(){
            isDislike = false;
            isFavor = false;
            isRead = false;
        }
        public Boolean getFavor() {
            return isFavor;
        }

        public void setFavor(Boolean favor) {
            isFavor = favor;
        }

        public Boolean getRead() {
            return isRead;
        }

        public void setRead(Boolean read) {
            isRead = read;
        }

        private Boolean isRead;

        public Boolean getDislike() {
            return isDislike;
        }

        public void setDislike(Boolean dislike) {
            isDislike = dislike;
        }

    private Boolean isDislike;

        private RealmList<String> imgUrls;

        @Ignore
        private String image;

        private String video;

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public RealmList<String> getImgUrls(){
            if (imgUrls == null) {
                String pattern =  "http.*?\\.((j[pe]{1,2}g)|(png))";
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



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

        private RealmList<Keyword> keywords;

        public RealmList<Keyword> getKeywords() {
            return keywords;
        }

        public void setKeywords(RealmList<Keyword> keywords) {
            this.keywords = keywords;
        }

}
