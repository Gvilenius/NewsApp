package com.java.news.http;
/*
 * Created by ljf on 2019-8-29.
 */

import java.util.List;
import com.java.news.data.*;

public class NewsResponse {
    private String pageSize;
    private String total;
    private List<NewsEntity> data;
    public List<NewsEntity> getNewsList(){
        return data;
    }
}
