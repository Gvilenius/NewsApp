package com.java.news;
/*
 * Created by ljf on 2019-8-30.
 */

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.inspector.protocol.module.HeapProfiler;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.OkHttpClient;

public class NewsApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
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
    }
}
