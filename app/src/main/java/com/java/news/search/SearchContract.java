package com.java.news.search;
/*
 * Created by ljf on 2019-9-5.
 */

import com.java.news.base.BasePresenter;
import com.java.news.base.BaseView;
import com.java.news.data.NewsEntity;

import java.util.List;

public interface SearchContract {
    public interface View extends BaseView {
        void setNewsList(List<NewsEntity> newsList);
    }

    public interface Presenter extends BasePresenter{
        void refresh();
    }
}
