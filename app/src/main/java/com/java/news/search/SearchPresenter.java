package com.java.news.search;
/*
 * Created by ljf on 2019-9-5.
 */

import com.java.news.data.NewsEntity;
import com.java.news.data.RealmHelper;
import com.java.news.news.newsList.NewsListContract;

import java.util.List;

public class SearchPresenter implements NewsListContract.Presenter {
    private NewsListContract.View mView;
    private int mCurrentPage;
    private int mPageSize = 10;
    private String keyword;
    private RealmHelper dbHelper = RealmHelper.getInstance();
    public SearchPresenter(NewsListContract.View view, String key){
        mCurrentPage=0;
        mView = view;
        keyword=key;
    }

    @Override
    public void loadMore() {
        List<NewsEntity> newsList = dbHelper.getNewsByKeyword(keyword, mCurrentPage, mPageSize);
        mView.appendNewsList(newsList);
        mCurrentPage++;
    }

    @Override
    public void refresh(){
        List<NewsEntity> newsList = dbHelper.getNewsByKeyword(keyword, mCurrentPage, mPageSize);
        System.out.println(keyword);
        mView.setNewsList(newsList);
        mCurrentPage = 1;
    }

    @Override
    public void keyword(String keyword) {
    }

    @Override
    public NewsEntity getNewsById(String id) {
        return dbHelper.getNewsByID(id);
    }

    @Override
    public void switch2NewsDetail(NewsEntity news) {
    }
}
