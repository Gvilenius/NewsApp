package com.java.news.favorites;
/*
 * Created by ljf on 2019-9-5.
 */

import com.java.news.R;
import com.java.news.data.NewsEntity;
import com.java.news.data.NewsFavorEntity;
import com.java.news.data.RealmHelper;

import java.util.List;

import io.realm.annotations.RealmClass;

public class FavorPresenter implements FavorContract.Presenter {
    private FavorContract.View mView;
    private RealmHelper dbHelper = RealmHelper.getInstance();
    public FavorPresenter(FavorContract.View view){
        mView = view;
    }

    @Override
    public void refresh(){
        List<NewsEntity> newsList = dbHelper.getNewsByRecommend();
        mView.setNewsList(newsList);
    }
}
