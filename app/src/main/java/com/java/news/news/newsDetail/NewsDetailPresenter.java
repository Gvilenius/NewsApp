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
    public void addHis(NewsEntity news) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                news.setRead(true);
            }
        });
//        dbHelper.insertOrUpdateNews(news);
    }

    @Override
    public void addFavor(NewsEntity news) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                news.setFavor(true);
            }
        });
//        dbHelper.insertOrUpdateNews(news);
    }

    @Override
    public void removeHis(NewsEntity news) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                news.setRead(false);
            }
        });
//        dbHelper.insertOrUpdateNews(news);
    }

    @Override
    public void removeFavor(NewsEntity news) {

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                news.setFavor(false);
            }
        });
//        dbHelper.insertOrUpdateNews(news);
    }

    public NewsEntity getNews(String id)
    {
        return dbHelper.getNewsByID(id);
    }

}
