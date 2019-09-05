package com.java.news.history;
/*
 * Created by ljf on 2019-9-5.
 */

import com.java.news.data.NewsEntity;
import com.java.news.data.RealmHelper;
import com.java.news.favorites.FavorContract;

import java.util.List;

public class HistoryPresenter implements FavorContract.Presenter {
    private FavorContract.View mView;
    private RealmHelper dbHelper = RealmHelper.getInstance();
    public HistoryPresenter(FavorContract.View view){
        mView = view;
    }

    @Override
    public void refresh(){
        List<NewsEntity> newsList = dbHelper.getReadNews();
        mView.setNewsList(newsList);
    }
}
