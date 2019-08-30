package com.java.news.news.newsList;
/*
 * Created by ljf on 2019-8-30.
 */

import com.java.news.base.BasePresenter;
import com.java.news.base.BaseView;
import com.java.news.data.NewsSummary;


import java.util.List;

public interface NewsListContract {
    interface View extends BaseView<Presenter> {
        //显示新闻列表
        void setNewsList(List<NewsSummary> newsList);
        //上拉追加列表
        void appendNewsList(List<NewsSummary> newsList);
        void onError();
    }

    interface Presenter extends BasePresenter {
        //上拉获取更多
        void getMoreNews();
        //下拉刷新
        void refreshNews();
        //设置关键字
        void keyword(String keyword);
    }
}
