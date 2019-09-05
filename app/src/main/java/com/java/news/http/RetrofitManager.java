package com.java.news.http;
/*
 * Created by ljf on 2019-8-29.
 */

import android.annotation.SuppressLint;
import android.content.Context;

import com.java.news.NewsApplication;
import com.java.news.data.NewsEntity;
import com.java.news.main.MainActivity;

import java.io.File;
import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private String baseUrl = "https://api2.newsminer.net/";
    private NewsService mNewsService;
    private static RetrofitManager mInstance;
    private RetrofitManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        File cacheDir = new File(NewsApplication.appContext.getCacheDir(), "response");
        //缓存的最大尺寸10m
        Cache cache = new Cache(cacheDir, 1024 * 1024 * 10);
        builder.cache(cache);
        builder.addInterceptor(new CacheInterceptor());
        OkHttpClient client = builder.build();

        mNewsService = new Retrofit.Builder()
                .client(client)
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
        return mNewsService.getNewsList(pageSize, "", "2019-09-06", keyword, catagory)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @SuppressLint("CheckResult")
    public static void main(String[] args){

    }
}
