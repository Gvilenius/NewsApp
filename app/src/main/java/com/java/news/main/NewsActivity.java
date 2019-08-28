package com.java.news.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.java.news.R;
import com.java.news.mybutton.MyButton;
import com.java.news.mybutton.NewsAdaptor;
import com.java.news.mybutton.NewsItem;
import com.java.news.mybutton.RefreshableView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewsActivity extends AppCompatActivity {

    RefreshableView refreshableView;
    ListView listView;
    NewsAdaptor adapter;
    List<NewsItem> newsList=new ArrayList<>();
    int numOfItems =10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_news);
        refreshableView = (RefreshableView) findViewById(R.id.refreshable_view);
        listView = (ListView) findViewById(R.id.list_view);
        refreshList();
        adapter = new NewsAdaptor(this,R.layout.news_item, newsList);
        listView.setAdapter(adapter);
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    refreshList();
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshableView.finishRefreshing();
            }
        }, 0);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        char outChar=(char)(position+'0');
                        String outStr="click on "+outChar;
                        Toast.makeText(NewsActivity.this, outStr,Toast.LENGTH_SHORT).show();
                    }
                }
                );

    }
    void refreshList(){
        newsList.clear();
        Random ran=new Random();
        for(int i=0;i<numOfItems;i++)
        {
            String input="Item "+i+": random number ="+ran.nextInt(100);
            newsList.add(new NewsItem(input,R.drawable.ic_launcher_foreground));
        }
    }
}
