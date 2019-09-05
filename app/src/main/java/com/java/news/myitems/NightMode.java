package com.java.news.myitems;

import androidx.appcompat.app.AppCompatDelegate;

import com.java.news.news.newsList.MyListener2;

public class NightMode {
    static private boolean isNightMode=false;
    static public MyListener2 newsLis=null;
    static public int getDeleg()
    {
       return (isNightMode? AppCompatDelegate.MODE_NIGHT_YES:AppCompatDelegate.MODE_NIGHT_NO);
    }
    static public void changeMode()
    {
        isNightMode=!isNightMode;
    }
    static public boolean getMode()
    {
        return isNightMode;
    }
}
