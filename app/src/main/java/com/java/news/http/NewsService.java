package com.java.news.http;
/*
 * Created by ljf on 2019-8-29.
 */

import com.java.news.data.NewsEntity;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsService {

    @GET("svc/news/queryNewsList")
    Observable<NewsResponse> getNewsList (@Query("size") String size,
                                          @Query("startDate") String startDate,
                                          @Query("endDate") String endDate,
                                          @Query("words") String words,
                                          @Query("categories") String categories);
}
