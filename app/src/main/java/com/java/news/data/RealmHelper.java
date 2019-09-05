package com.java.news.data;
/*
 * Created by ljf on 2019-8-31.
 */


import android.content.Context;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class RealmHelper {
    private static final String DB_NAME = "myRealm.realm";
    private Realm mRealm;
    private static RealmHelper instance;
    private NewsRecSystem mRec;
    public static void init(Context context){
        Realm.init(context);
        RealmConfiguration config = new  RealmConfiguration.Builder()
                .name("myRealm.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        Stetho.initialize(
                Stetho.newInitializerBuilder(context)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(context))
                        .enableWebKitInspector(RealmInspectorModulesProvider
                                .builder(context)
                                .withDeleteIfMigrationNeeded(true)
                                .build())
                        .build());

        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });


    }

    public Boolean dislike(NewsEntity news){
        List<NewsEntity> dislikeNews = mRealm.where(NewsEntity.class).equalTo("isDislike", true).findAll();
        return mRec.dislike(dislikeNews, news);
    }

    public void insertDislike(NewsEntity news){
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                news.setDislike(true);
            }
        });
    }

    public void deleteDislike(NewsEntity news){
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                news.setDislike(false);
            }
        });
    }



    public RealmHelper() {
        mRealm = Realm.getDefaultInstance();
        mRec = NewsRecSystem.getInstance();
    }

    public List<NewsEntity> getNewsByRecommend(){
        List<NewsEntity> source = mRealm.where(NewsEntity.class).findAll();
        List<NewsEntity> his = getReadNews();
        List<NewsEntity> favor = getFavorNews();
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

    public List<NewsEntity> getNewsByKeyword(final String keyword, int pageIndex, int pageSize){
        int start = pageSize*pageIndex,
                end = pageSize*pageIndex + pageSize;
        List<NewsEntity> newsList = new ArrayList<>();
        RealmResults<NewsEntity> query=  mRealm.where(NewsEntity.class).like("content", "*"+keyword+"*").findAll();
        System.out.println(query.size());
        for (int i = start; i < end; ++i){
            if (i >= query.size()) break;
            newsList.add(query.get(i));
        }
        return newsList;
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
                            if (mRealm.where(NewsEntity.class).equalTo("newsID", news.getNewsID()).findFirst() != null)
                                continue;
                            news.getImgUrls();
                            realm.copyToRealmOrUpdate(news);
                        }
                    }
                });
    }

    public List<NewsEntity> getNewsListByPage(int pageIndex, int pageSize){
        int start = pageSize*pageIndex,
                end = pageSize*pageIndex + pageSize;
        List<NewsEntity> newsList = new ArrayList<>();
        List<NewsEntity> query =  mRealm.where(NewsEntity.class)
                .sort("publishTime")
                .findAll();
        for (int i = start; i < end; ++i){
            if (i >= query.size()) break;
            newsList.add(query.get(i));
        }
        return newsList;
    }

    public List<NewsEntity> getAllNews(){
        return mRealm.where(NewsEntity.class).findAll();
    }

    public List<NewsEntity> getFavorNews(){
        return mRealm.where(NewsEntity.class).equalTo("isFavor", true).findAll();
    }
    public List<NewsEntity> getReadNews(){
        return mRealm.where(NewsEntity.class).equalTo("isRead", true).findAll();
    }

    public void deleteAllNews(){
        mRealm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){
                realm.where(NewsEntity.class).findAll().deleteAllFromRealm();
            }
        });
    }


    public void insertOrUpdateNews(final NewsEntity news){
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(news);
            }
        });
    }

    public void insertFavor(NewsEntity news){
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                news.setFavor(true);
            }
        });
    }

    public void insertReadHis(NewsEntity news){
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                news.setRead(true);
            }
        });
    }

    public void deleteFavor(NewsEntity news){
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                news.setFavor(false);
    }
});
    }

    public void deleteReadHis(NewsEntity news){
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                news.setRead(false);
            }
        });
    }



//    public void deleteNewsHis(String newsID){
//        RealmAsyncTask realmAsyncTask = mRealm.executeTransactionAsync(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                RealmResults<NewsEntity> news = realm.where(NewsEntity.class).findAll();
//                news.deleteFirstFromRealm();
//            }
//        }, new Realm.Transaction.OnSuccess() {
//            @Override
//            public void onSuccess() {
////                UIUtils.showToast("已取消收藏");
//            }
//        }, new Realm.Transaction.OnError() {
//            @Override
//            public void onError(Throwable error) {
////                UIUtils.showToast("已添加收藏");
//            }
//        });
//    }

//    public void deleteAllNewsHis(){
//        mRealm.executeTransaction(new Realm.Transaction(){
//            @Override
//            public void execute(Realm realm){
//                mRealm.where(NewsHisEntity.class).findAll().deleteAllFromRealm();
//            }
//        });
//    }

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
