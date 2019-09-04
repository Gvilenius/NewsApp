package com.java.news.data;

import io.realm.RealmObject;

/*
 * Created by ljf on 2019-9-4.
 */

public class SearchHis extends RealmObject {
    String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
