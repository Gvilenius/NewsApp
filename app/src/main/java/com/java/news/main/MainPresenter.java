package com.java.news.main;
/*
 * Created by ljf on 2019-8-28.
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mMainVeiw;

    public MainPresenter(MainContract.View mainview){
        mMainVeiw = mainview;
    }

    @Override
    public void switchNavigation(int id) {
//        switch (id){
//
//        }
    }

    @Override
    public void start() {

    }
}
