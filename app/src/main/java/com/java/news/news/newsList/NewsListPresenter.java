package com.java.news.news.newsList;
/*
 * Created by ljf on 2019-8-30.
 */

import android.annotation.SuppressLint;

import com.java.news.data.NewsEntity;
import com.java.news.data.RealmHelper;
import com.java.news.http.NewsResponse;
import com.java.news.http.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class NewsListPresenter implements NewsListContract.Presenter {
    private final String PAGE_SIZE = "200";
    private final RealmHelper dbHelper = RealmHelper.getInstance();


    private NewsListContract.View mNewsListView;
    private String mCategory;
    private String mKeyword;
    private int mCurrentPage;
    private int init = 0;
    public NewsListPresenter(NewsListContract.View newsListView, String category, String keyword){
        mNewsListView = newsListView;
        mCategory = category;
        mKeyword = keyword;
        mCurrentPage = 0;

    }

    @SuppressLint("CheckResult")
    @Override
    public void loadMore() {
        List<NewsEntity> newsList = dbHelper.getNewsByRecommend();
        if (mCategory == "推荐") {
            if (newsList.size() > mCurrentPage * 10 + 10)
                newsList = newsList.subList(mCurrentPage * 10, mCurrentPage * 10 + 10);
            else newsList = new ArrayList<>();
            }
        else
            newsList  = dbHelper.getNewsListByPage(mCurrentPage, 10);
        mNewsListView.appendNewsList(newsList);
        mCurrentPage += 1;
    }

    @SuppressLint("CheckResult")
    @Override
    public void refresh() {
        if (init > 0 && mCategory == "推荐") {
            mNewsListView.setNewsList(dbHelper.getNewsByRecommend());
            return;
        }
        String cate = (mCategory == "推荐") ? "" : mCategory;
        RetrofitManager.getInstance().fetchNewsList(PAGE_SIZE, mKeyword, cate)
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
                if (newsList.size() > 10)
                    mNewsListView.setNewsList(newsList.subList(0, 10));
                else
                    mNewsListView.setNewsList(newsList);
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
                System.out.println(RealmHelper.getInstance().getAllNews().size());

            }
        });
        init = 1;

    }

    @Override
    public void keyword(String keyword) {
        mKeyword = keyword;
    }

    @Override
    public NewsEntity getNewsById(String id) {
        return dbHelper.getNewsByID(id);
    }

    @Override
    public void switch2NewsDetail(NewsEntity news) {
//        Intent intent = new Intent(mNewsListView, NewsDetailActivity.class);
    }
}
