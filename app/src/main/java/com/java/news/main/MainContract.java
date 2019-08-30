package com.java.news.main;
import com.java.news.base.*;

/*
 * Created by ljf on 2019-8-20.
 */

public interface MainContract {
    interface View extends BaseView{
        void switch2News();
        void switch2Favorites();
        void switch2Settings();
    }

    interface Presenter extends BasePresenter{
        void switchNavigation(int id);
    }
}
