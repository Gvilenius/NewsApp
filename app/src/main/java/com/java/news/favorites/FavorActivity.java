package com.java.news.favorites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.java.news.R;
import com.java.news.data.NewsEntity;
import com.java.news.myitems.FavoriteAdapter;
import com.java.news.myitems.MyData;
import com.java.news.myitems.NightMode;
import com.java.news.news.newsDetail.NewsDetailActivity;

import java.util.ArrayList;
import java.util.List;

//import com.java.news.myitems.NewsAdaptor;

public class FavorActivity extends AppCompatActivity implements FavorContract.View{

    FavorPresenter mPresenter;
    ListView mListView;
    FavoriteAdapter mAdapter;
    List<MyData> mDatas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(NightMode.getDeleg());
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_favorite);
        mPresenter=new FavorPresenter(this);
        mListView=findViewById(R.id.favorite_view);
        mDatas=new ArrayList<>();

        mPresenter.refresh();
        mAdapter=new FavoriteAdapter(FavorActivity.this,mDatas);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(
                            AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(FavorActivity.this, NewsDetailActivity.class);
                        String message = mDatas.get(position).mID;
                        intent.putExtra("NewsID", message);
                        startActivity(intent);
//                        char outChar = (char) (position + '1');
//                        String outStr = "click on " + outChar;
//                        Toast.makeText(NewsActivity.this, outStr, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void setNewsList(List<NewsEntity> newsList) {
        for(NewsEntity o:newsList)
        {
            MyData newData=new MyData();
            newData.mTitle=o.getTitle();
            if(o.getImgUrls().isEmpty())
                newData.mURL="null";
            else
                newData.mURL=o.getImgUrls().first();
            newData.mID=o.getNewsID();
            mDatas.add(newData);
        }
    }
}
