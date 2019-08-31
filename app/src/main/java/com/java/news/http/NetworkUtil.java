package com.java.news.http;
/*
 * Created by ljf on 2019-8-31.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.java.news.NewsApplication;

public class NetworkUtil {
    public static boolean isNetAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) NewsApplication.appContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    Log.i("有网", "当前网络名称：" + info.getTypeName());
                    return true;
                }
            }
        }
        return false;
    }
}