package com.java.news.http;
/*
 * Created by ljf on 2019-8-29.
 */

import java.util.List;
import com.java.news.data.*;

public class Model {
    private String pageSize;
    private String total;
    private List<NewsDetail> data;
    public void setList(List<NewsDetail> l){
        l = data;
        for (NewsDetail news: data){
            System.out.println(news.getNewsID());
        }
    }
}
