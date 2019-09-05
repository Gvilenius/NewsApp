package com.java.news.favorites;
/*
 * Created by ljf on 2019-9-5.
 */

import com.java.news.base.BasePresenter;
import com.java.news.base.BaseView;

public interface FavorContract {
    public interface View extends BaseView {

    }

    public interface Presenter extends BasePresenter{
        void refresh();
    }
}
