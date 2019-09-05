package com.java.news.news.newsDetail;
/*
 * Created by ljf on 2019-8-30.
 */

import com.java.news.base.BasePresenter;
import com.java.news.base.BaseView;
import com.java.news.data.NewsEntity;

public interface NewsDetailContract {
    public interface View extends BaseView{

    }

    public interface Presenter extends BasePresenter{
        void addHis(NewsEntity news);
        void addFavor(NewsEntity news);
        void removeHis(NewsEntity news);
        void removeFavor(NewsEntity news);
    }
}
