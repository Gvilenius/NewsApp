package com.java.news.data;
/*
 * Created by ljf on 2019-9-5.
 */
import android.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.sqrt;

public class NewsRecSystem {
    private static NewsRecSystem mInstance;
    public static NewsRecSystem getInstance(){
        if (mInstance == null){
            synchronized (NewsRecSystem.class) {
                mInstance = new NewsRecSystem();
            }
        }
        return mInstance;
    }

    private final double wRead = 1, wFavor = 5.0, wCata = 10.0;
    /*
     * 文本向量
     */
    private class Vector{
        Map<String, Double> vec;
        public Vector(){
            vec = new HashMap<>();
        }

        public  Vector(NewsEntity news){
            this();
            add(news, 1, wCata);
        }

        public void add(NewsEntity news, double w, double wCata){
            for (Keyword keyword: news.getKeywords()){
                if (! vec.containsKey(keyword.word))
                    vec.put(keyword.word, 0.0);
                vec.put(keyword.word, vec.get(keyword.word) + w*keyword.score);

                String cataTag = "c_" + news.getCategory();

                if(!vec.containsKey(cataTag))
                    vec.put(cataTag,0.0);
                vec.put(cataTag, vec.get(cataTag) + w * wCata);
            }
        }
        double norm2(){
            double result = 0.0;
            for (double v: vec.values()){
                result += v*v;
            }
            return sqrt(result);
        }
    }

    private double cosineSimilarity(Vector a,Vector b, double n1, double n2)
    {
        double result=0.0;
        for(String key : b.vec.keySet())
            if(a.vec.containsKey(key))
                result += a.vec.get(key)*b.vec.get(key);
        return result / (n1*n2);
    }

    public Boolean dislike(List<NewsEntity> dislikeNews, NewsEntity news){
        Vector User = new Vector();

        for(NewsEntity data: dislikeNews)
            User.add(data, 1, 10);

        Vector vnews = new Vector(news);

        return cosineSimilarity(User, vnews, User.norm2(), vnews.norm2()) > 0.8;

    }

    public List<NewsEntity> recommendSort(List<NewsEntity> source, List<NewsEntity> his, List<NewsEntity> favor)
    {
        List<NewsEntity> result = new ArrayList<NewsEntity>();
        Vector User = new Vector();

        for(NewsEntity data: his)
            User.add(data, wRead, wCata);

        for(NewsEntity data: favor)
            User.add(data, wFavor, wCata);

        double userNorm2 = User.norm2();


        List<Pair<Double,NewsEntity>> sortList = new ArrayList<>();
        for(NewsEntity news : source) {
            Vector newsVec = new Vector(news);
            double newsNorm2 = newsVec.norm2();
            if (newsNorm2 == 0) continue;;
            double dist = cosineSimilarity(User, newsVec,  userNorm2, newsNorm2 );
            sortList.add(new Pair<Double,NewsEntity>(dist,news));
        }

        Collections.sort(sortList,new Comparator<Pair<Double,NewsEntity>>(){
            public int compare(Pair<Double,NewsEntity> arg0, Pair<Double,NewsEntity> arg1) {
                return arg1.first.compareTo(arg0.first);
            }
        });

        for (Pair<Double, NewsEntity> p : sortList)
            result.add(p.second);

        return result;
    }

}
