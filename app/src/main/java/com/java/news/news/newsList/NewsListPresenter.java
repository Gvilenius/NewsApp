package com.java.news.news.newsList;
/*
 * Created by ljf on 2019-8-30.
 */

import android.annotation.SuppressLint;

import com.java.news.data.NewsEntity;
import com.java.news.http.NewsResponse;
import com.java.news.http.RetrofitManager;

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
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadMore() {
        Observable<NewsResponse> o = RetrofitManager.getInstance().fetchNewsList(PAGE_SIZE, mKeyword, mCatogory);
        o.subscribe(new Consumer<NewsResponse>(){
            @Override
            public void accept(NewsResponse value) throws Exception{
                List<NewsEntity> newsList = value.getNewsList();

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
                List<NewsEntity> newsList = value.getNewsList();

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
    public void switch2NewsDetail(NewsEntity news) {
//        Intent intent = new Intent(mNewsListView, NewsDetailActivity.class);
    }

}
