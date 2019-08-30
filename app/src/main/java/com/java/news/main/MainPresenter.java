package com.java.news.main;
/*
 * Created by ljf on 2019-8-28.
 */

import com.java.news.base.BaseView;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mMainView;
    public MainPresenter(MainContract.View mainView){
        mMainView = mainView;
    }

    @Override
    public void switchNavigation(int id) {}
}
