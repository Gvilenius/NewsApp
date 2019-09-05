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
import com.java.news.data.RealmHelper;
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
        RealmHelper.init(this);
    }
    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        Realm.getDefaultInstance().close();
        super.onTerminate();
    }
}
