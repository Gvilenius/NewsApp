package com.java.news.data;
/*
 * Created by ljf on 2019-8-31.
 */


import java.util.List;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
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

    public RealmResults<NewsEntity> getNewsByKeyword(final String keyword){
        return mRealm.where(NewsEntity.class).like("content", keyword).findAll();
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

    public void insertFavor(final NewsFavorEntity news){
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(news);
            }
        });
    }


    public void deleteFavor(final String newsID){

        RealmAsyncTask realmAsyncTask = mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<NewsFavorEntity> news = realm.where(NewsFavorEntity.class).findAll();
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


    public void insertReadHis(final NewsHisEntity newsHis){
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(newsHis);
            }
        });
    }

    public void deleteReadHis(){

    }

    public void insertSearchHis(final SearchHis searchHis){
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(searchHis);
            }
        });
    }

    public void deleteSearchHis(){

    }
    public void deleteAllSearchHis(){

    }


}
