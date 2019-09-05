package com.java.news.news.newsList;
/*
 * Created by ljf on 2019-8-30.
 */

import android.annotation.SuppressLint;

import com.java.news.data.NewsEntity;
import com.java.news.data.RealmHelper;
import com.java.news.http.NewsResponse;
import com.java.news.http.RetrofitManager;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class NewsListPresenter implements NewsListContract.Presenter {
    private final String PAGE_SIZE = "200";
    private final RealmHelper dbHelper = RealmHelper.getInstance();


    private NewsListContract.View mNewsListView;
    private String mCatogory;
    private String mKeyword;
    private int mCurrentPage;
    public NewsListPresenter(NewsListContract.View newsListView, String category, String keyword){
        mNewsListView = newsListView;
        mCatogory = category;
        mKeyword = keyword;
        mCurrentPage = 0;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadMore() {
        List<NewsEntity> newsList= dbHelper.getNewsListByPage(mCurrentPage, 10);
        mNewsListView.appendNewsList(newsList);
        mCurrentPage += 1;
    }

    @SuppressLint("CheckResult")
    @Override
    public void refresh() {
        RetrofitManager.getInstance().fetchNewsList(PAGE_SIZE, mKeyword, mCatogory)
        .subscribe(new Observer<NewsResponse>(){
            private Disposable disposable;
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(NewsResponse value){
                List<NewsEntity> newsList = value.getNewsList();
                dbHelper.insertNewsList(newsList);
                mNewsListView.setNewsList(newsList.subList(0, 10));
            }
            @Override
            public void onError(Throwable e) {
                System.out.println("Error");
                mCurrentPage = 0;
                loadMore();
            }

            @Override
            public void onComplete() {
                mCurrentPage = 1;
                System.out.println("刷新完成");
            }
        });

    }

    @Override
    public void keyword(String keyword) {
        mKeyword = keyword;
    }

    @Override
    public void switch2NewsDetail(NewsEntity news) {
//        Intent intent = new Intent(mNewsListView, NewsDetailActivity.class);
    }
}
