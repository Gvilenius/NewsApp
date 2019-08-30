package com.java.news.http;
/*
 * Created by ljf on 2019-8-29.
 */

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private String baseUrl = "https://api2.newsminer.net/";
    private NewsService mNewsService;
    private static RetrofitManager mInstance;
    private RetrofitManager() {
        mNewsService = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(NewsService.class);
    }


    public static RetrofitManager getInstance(){
        if (mInstance == null)
            mInstance = new RetrofitManager();
        return mInstance;
    }

    public Observable<NewsResponse> fetchNewsList(String pageSize, String keyword, String catagory){
        return mNewsService.getNewsList(pageSize, "2019-07-01", "2019-07-03", keyword, catagory)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public static void main(String[] args){

    }
}
