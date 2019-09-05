package com.java.news.data;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/*
 * Created by ljf on 2019-9-4.
 */

@RealmClass
public class SearchHisEntity implements RealmModel {
    String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
