package com.java.news.news.newsList;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.java.news.data.NewsEntity;

import java.util.List;

/*
 * Created by ljf on 2019-9-5.
 */

public class NewsListFragment extends Fragment implements NewsListContract.View {


    NewsListPresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setNewsList(List<NewsEntity> newsList) {

    }

    @Override
    public void appendNewsList(List<NewsEntity> newsList) {

    }

    @Override
    public void onError() {

    }
}
