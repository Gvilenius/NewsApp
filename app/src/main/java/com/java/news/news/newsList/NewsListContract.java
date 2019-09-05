package com.java.news.news.newsList;
/*
 * Created by ljf on 2019-8-30.
 */

import com.java.news.base.BasePresenter;
import com.java.news.base.BaseView;
import com.java.news.data.NewsEntity;


import java.util.List;

public interface NewsListContract {
    interface View extends BaseView{
        //显示新闻列表
        void setNewsList(List<NewsEntity> newsList);
        //上拉追加列表
        void appendNewsList(List<NewsEntity> newsList);
        void onError();
    }

    interface Presenter extends BasePresenter {
        //上拉获取更多
        void loadMore();
        //下拉刷新
        void refresh();
        //设置关键字
        void keyword(String keyword);

        void switch2NewsDetail(NewsEntity news);
    }

}
