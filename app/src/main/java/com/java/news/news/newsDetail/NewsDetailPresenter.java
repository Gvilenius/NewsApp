package com.java.news.news.newsDetail;
/*
 * Created by ljf on 2019-9-5.
 */

import com.java.news.data.NewsEntity;
import com.java.news.data.RealmHelper;

import io.realm.Realm;

public class NewsDetailPresenter implements NewsDetailContract.Presenter {
    private NewsDetailContract.View mView;
    private Realm mRealm = Realm.getDefaultInstance();
    private RealmHelper dbHelper = RealmHelper.getInstance();
    public NewsDetailPresenter(NewsDetailContract.View view){
        mView = view;
    }

    @Override
    public void addToHis(NewsEntity news) {
        dbHelper.insertReadHis(news);
    }

    @Override
    public void favor(NewsEntity news) {
        dbHelper.insertFavor(news);
    }

    @Override
    public void removeFromHis(NewsEntity news) {
        dbHelper.deleteReadHis(news);
    }

    @Override
    public void unFavor(NewsEntity news) {
        dbHelper.deleteFavor(news);
    }

    public NewsEntity getNews(String id)
    {
        return dbHelper.getNewsByID(id);
    }

}
