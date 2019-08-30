package com.java.news.data;
/*
 * Created by ljf on 2019-8-29.
 */

import java.util.List;

public class NewsDetail extends NewsSummary{
    private String content;
    private List<keyword> keywords;
    private List<keyword> when;
    private List<person> persons;
    private List<person> organizations;
    private List<location> locations;
    private List<keyword> where;
    private List<keyword> who;

    private static class location{
        private float lng;
        private int count;
        private String linkedURL;
        private float lat;
        private String mention;
    }
    private static class person{
        private int count;
        private String linkedURL;
        private String mention;
    }
    private static class keyword{
        private String word;
        private String score;
    }
}
