package com.java.news.news.newsList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.java.news.R;
import com.java.news.myitems.NewsAdaptor;
import com.java.news.myitems.NewsItem;
import com.java.news.news.newsDetail.NewsDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FavoriteActivity extends AppCompatActivity {
    ListView listView;
    NewsAdaptor adapter;
    List<NewsItem> newsList=new ArrayList<>();
    int numOfItems =10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        listView = (ListView) findViewById(R.id.favorite_view);
        setList();
        adapter = new NewsAdaptor(this,R.layout.news_item, newsList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(FavoriteActivity.this, NewsDetailActivity.class);
                        String newsTitle = "News "+(char)(position+'0');
                        String newsInfo = "This is the information in the news "+(char)(position+'0')+".\nI use it for test.\nTo see what will happen...";
                        intent.putExtra("news_detail_title", newsTitle);
                        intent.putExtra("news_detail_info", newsInfo);
                        startActivity(intent);
//                        char outChar=(char)(position+'0');
//                        String outStr="click on "+outChar;
//                        Toast.makeText(NewsActivity.this, outStr,Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    void setList(){
        newsList.clear();
        Random ran=new Random();
        for(int i=0;i<numOfItems;i++)
        {
            String input="Item "+i+": random number ="+ran.nextInt(100);
            newsList.add(new NewsItem(input,R.drawable.ic_launcher_foreground));
        }
    }
}
