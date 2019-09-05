package com.java.news.favorites;
/*
 * Created by ljf on 2019-9-5.
 */

public class FavorPresenter implements FavorContract.Presenter {
    private FavorContract.View mView;
    public FavorPresenter(FavorContract.View view){
        mView = view;
    }

    @Override
    public void refresh(){

    }
}
