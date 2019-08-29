package com.java.news.http;
/*
 * Created by ljf on 2019-8-29.
 */

import com.java.news.data.NewsDetail;
import com.java.news.data.NewsSummary;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitManager {
    private String baseUrl = "https://api2.newsminer.net/";
    private NewsService mNewsService;
    private static RetrofitManager mInstance;
    private RetrofitManager() {
        mNewsService = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(NewsService.class);
    }

    public static RetrofitManager getInstance(){
        if (mInstance == null)
            mInstance = new RetrofitManager();
        return mInstance;
    }

    public List<NewsDetail> getNewsList(String pageSize, String keyword, String catagory){
        Call<Model> model = mNewsService.getNewsList("15", "2019-07-01", "2019-07-03", keyword, catagory);
        final List<NewsDetail> newsList = null;

        model.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                response.body().setList(newsList);
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {

            }
        });
        return newsList;
    }
    public static void main(String[] args){
        RetrofitManager m = RetrofitManager.getInstance();
        List<NewsDetail> nd = m.getNewsList("15", "特朗普", "科技");
        if (nd == null) System.out.println("fail");
    }

}
