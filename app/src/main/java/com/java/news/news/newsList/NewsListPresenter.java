package com.java.news.news.newsList;
/*
 * Created by ljf on 2019-8-30.
 */

import com.java.news.http.RetrofitManager;

public class NewsListPresenter implements NewsListContract.Presenter {

    private NewsListContract.View mNewsListView;
    private String mCatogory;
    private String mKeyword;
    public NewsListPresenter(NewsListContract.View newsListView, String category, String keyword){
        mNewsListView = newsListView;
        mCatogory = category;
        mKeyword = keyword;

        mNewsListView.setPresenter(this);
    }
    @Override
    public void getMoreNews() {
    }

    @Override
    public void refreshNews() {

    }

    @Override
    public void keyword(String keyword) {

    }

    private void fetchNews(){
        RetrofitManager.getInstance();

    }
}
