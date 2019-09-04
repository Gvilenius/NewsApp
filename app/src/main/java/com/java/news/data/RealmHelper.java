package com.java.news.data;
/*
 * Created by ljf on 2019-8-31.
 */


import java.util.List;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;

public class RealmHelper {
    private static final String DB_NAME = "myRealm.realm";
    private Realm mRealm;
    private static RealmHelper instance;
    private NewsRecSystem mRec;
    public RealmHelper() {
        mRealm = Realm.getDefaultInstance();
        mRec = NewsRecSystem.getInstance();
    }

    public List<NewsEntity> getNewsByRecommend(){
        List<NewsEntity> source = mRealm.where(NewsEntity.class).findAll();
        List<NewsHisEntity> his = mRealm.where(NewsHisEntity.class).findAll();
        List<NewsFavorEntity> favor = mRealm.where(NewsFavorEntity.class).findAll();
        return mRec.recommendSort(source, his, favor);
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
                            news.getImgUrls();
                            realm.copyToRealmOrUpdate(news);
                        }
                    }
                });
    }
    public void deleteAllNews(){
        mRealm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){
                mRealm.where(NewsEntity.class).findAll().deleteAllFromRealm();
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


    public void insertNewsHis(final NewsHisEntity newsHis){
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(newsHis);
            }
        });
    }

    public void deleteNewsHis(){

    }

    public void deleteAllNewsHis(){
        mRealm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){
                mRealm.where(NewsHisEntity.class).findAll().deleteAllFromRealm();
            }
        });
    }

    public void insertSearchHis(final SearchHisEntity searchHis){
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(searchHis);
            }
        });
    }



    public void deleteAllSearchHis(){
        mRealm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){
                mRealm.where(SearchHisEntity.class).findAll().deleteAllFromRealm();
            }
        });
    }

    public void deleteSearchHis(){

    }

}
