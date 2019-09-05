package com.java.news.data;
/*
 * Created by ljf on 2019-9-5.
 */

import io.realm.RealmObject;

public class Keyword extends RealmObject {
    public String word;
    public Double score;
}