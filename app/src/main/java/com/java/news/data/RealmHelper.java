package com.java.news.data;
/*
 * Created by ljf on 2019-8-31.
 */


import java.util.List;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class RealmHelper {
    private static final String DB_NAME = "myRealm.realm";
    private Realm mRealm;
    private static RealmHelper instance;

    public RealmHelper() {
        mRealm = Realm.getDefaultInstance();
    }

    public static RealmHelper getInstance(){
        if (instance == null){
            synchronized (RealmHelper.class){
                instance = new RealmHelper();
            }
        }
        return instance;
    }

    public NewsEntity getNewsByID(String newsID){
        return mRealm.where(NewsEntity.class).equalTo("newsID", newsID).findFirst();
    }
    public void insertNewsList(final List<NewsEntity> newsList){
        mRealm.executeTransaction(
                new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        for (NewsEntity news : newsList) {
                            realm.copyToRealmOrUpdate(news);
                        }
                    }
                });
    }

    public void insertFavorate(final NewsEntity news){
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(news);
            }
        });
    }

    public void deleteFavorate(final String newsID){

        RealmAsyncTask realmAsyncTask = mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<NewsEntity> news = realm.where(NewsEntity.class).findAll();
                news.deleteFirstFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
//                UIUtils.showToast("已取消收藏");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
//                UIUtils.showToast("已添加收藏");
            }
        });
    }

    public void insertReadHis(){

    }

    public void insertSearchHis(){

    }

    public void deleteAllSearchHis(){

    }


}
