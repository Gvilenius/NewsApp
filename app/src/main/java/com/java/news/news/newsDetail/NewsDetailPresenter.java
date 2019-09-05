package com.java.news.news.newsDetail;
/*
 * Created by ljf on 2019-9-5.
 */

import com.java.news.data.NewsEntity;
import com.java.news.data.NewsFavorEntity;
import com.java.news.data.NewsHisEntity;
import com.java.news.data.RealmHelper;

public class NewsDetailPresenter implements NewsDetailContract.Presenter {
    private NewsDetailContract.View mView;
    private RealmHelper dbHelper = RealmHelper.getInstance();
    public NewsDetailPresenter(NewsDetailContract.View view){
        mView = view;
    }
    @Override
    public void addHis(NewsEntity news) {
        news.setRead(true);
        dbHelper.updateNewsHis(news);
    }

    @Override
    public void addFavor(NewsEntity news) {
        news.setFavor(true);
        dbHelper.updateFavor(news);
    }

    @Override
    public void removeHis(NewsEntity news) {
        news.setRead(false);
        dbHelper.updateNewsHis(news);
    }

    @Override
    public void removeFavor(NewsEntity news) {
        news.setFavor(false);
        dbHelper.updateFavor(news);
    }


}
