package com.java.news.http;
/*
 * Created by ljf on 2019-8-29.
 */

import android.annotation.SuppressLint;

import com.java.news.data.NewsEntity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
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
            synchronized (RetrofitManager.class) {
                mInstance = new RetrofitManager();
            }
        return mInstance;
    }

    public Observable<NewsResponse> fetchNewsList(String pageSize, String keyword, String catagory){
        return mNewsService.getNewsList(pageSize, "", "", "", "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    @SuppressLint("CheckResult")
    public static void main(String[] args){
        RetrofitManager.getInstance().fetchNewsList("10", "特朗普", "科技").subscribe(new Consumer<NewsResponse>(){
            @Override
            public void accept(NewsResponse value) {
                List<NewsEntity> newsList = value.getNewsList();
                for (NewsEntity news: newsList){
                    System.out.println(news.getNewsID());
                }
//                To handle the data here, for exmple
            }
        });
    }
}
