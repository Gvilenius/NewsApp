package com.java.news;
/*
 * Created by ljf on 2019-8-30.
 */

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.inspector.protocol.module.HeapProfiler;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.java.news.data.NewsEntity;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import okhttp3.OkHttpClient;

public class NewsApplication extends Application {
    public static Context appContext;
    @Override
    public void onCreate(){
        super.onCreate();
        appContext = this;
        Realm.init(this);
        RealmConfiguration config = new  RealmConfiguration.Builder()
                .name("myRealm.realm")
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider
                                .builder(this)
                                .withDeleteIfMigrationNeeded(true)
                                .build())
                        .build());

        Realm.getDefaultInstance()
                .executeTransaction(
                        new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
//                                RealmResults<NewsEntity> news = realm.where(NewsEntity.class).equalTo("newsID" ,"201710220358bae1eb1b6c864689b8735067a16027d4").findAll();
//                                news.deleteAllFromRealm();
                                realm.deleteAll();
                            }
                        });
    }
    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        Realm.getDefaultInstance().close();
        super.onTerminate();
    }
}
