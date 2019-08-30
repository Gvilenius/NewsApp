package com.java.news.news.newsList;
/*
 * Created by ljf on 2019-8-30.
 */

import android.annotation.SuppressLint;
import android.content.Intent;

import com.java.news.data.NewsDetail;
import com.java.news.http.NewsResponse;
import com.java.news.http.RetrofitManager;
import com.java.news.main.NewsDetailActivity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class NewsListPresenter implements NewsListContract.Presenter {
    private final String PAGE_SIZE = "20";

    private NewsListContract.View mNewsListView;
    private String mCatogory;
    private String mKeyword;
    public NewsListPresenter(NewsListContract.View newsListView, String category, String keyword){
        mNewsListView = newsListView;
        mCatogory = category;
        mKeyword = keyword;
        mNewsListView.setPresenter(this);
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadMore() {
        Observable<NewsResponse> o = RetrofitManager.getInstance().fetchNewsList(PAGE_SIZE, mKeyword, mCatogory);
        o.subscribe(new Consumer<NewsResponse>(){
            @Override
            public void accept(NewsResponse value) throws Exception{
                List<NewsDetail> newsList = value.getNewsList();

                //To handle the data here, for exmple
                mNewsListView.appendNewsList(newsList);
            }
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public void refresh() {
        Observable<NewsResponse> o = RetrofitManager.getInstance().fetchNewsList(PAGE_SIZE, mKeyword, mCatogory);
        o.subscribe(new Consumer<NewsResponse>(){
            @Override
            public void accept(NewsResponse value) throws Exception{
                List<NewsDetail> newsList = value.getNewsList();

                //To handle the data here, for exmple
                mNewsListView.setNewsList(newsList);
            }
        });
    }

    @Override
    public void keyword(String keyword) {
        mKeyword = keyword;
    }

    @Override
    public void switch2NewsDetail(NewsDetail news) {
//        Intent intent = new Intent(mNewsListView, NewsDetailActivity.class);
    }

}
