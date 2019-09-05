package com.java.news;
/*
 * Created by ljf on 2019-8-30.
 */

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.inspector.protocol.module.HeapProfiler;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.iflytek.cloud.Setting;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.java.news.data.NewsEntity;
import com.java.news.data.RealmHelper;
import com.java.news.utils.TTSUtils;
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
        appContext = getApplicationContext();
        RealmHelper.init(this);

//        SpeechUtility.createUtility(this, SpeechConstant.APPID+ "=5d70b26a");//=号后面写自己应用的APPID
//        Setting.setShowLog(true);
//
//        TTSUtils.getInstance().speak("hello，我想说的话在这里");
    }
    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        Realm.getDefaultInstance().close();
        super.onTerminate();
    }
}
