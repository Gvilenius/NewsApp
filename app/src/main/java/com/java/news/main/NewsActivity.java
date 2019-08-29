package com.java.news.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.java.news.R;
import com.java.news.mybutton.MyButton;
import com.java.news.mybutton.NewsAdaptor;
import com.java.news.mybutton.NewsItem;
import com.java.news.mybutton.RefreshableView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class NewsActivity extends AppCompatActivity {
    //刷新栏信息
    RefreshableView refreshableView;
    ListView listView;
    NewsAdaptor adapter;
    List<NewsItem> newsList=new ArrayList<>();
    int numOfItems =10;

    //分类栏信息
    GridView classView;
    SimpleAdapter classAdapter;
    List<Map<String, Object>> data_list;
    String[] classes={"类别1","类别2","类别3","类别4","类别5","类别6","类别7","类别8","类别9","类别10"};

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
                        Intent intent = new Intent(NewsActivity.this, NewsDetailActivity.class);
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

        //新闻分类选项栏
        classView = (GridView) findViewById(R.id.class_view);
        //新建适配器
        data_list = new ArrayList<>();
        for(int i=0;i<classes.length;i++){
            Map<String, Object> map = new HashMap<>();
            map.put("text", classes[i]);
            data_list.add(map);
        }
        String [] from ={"text"};
        int [] to = {R.id.class_text};
        classAdapter = new SimpleAdapter(this, data_list, R.layout.class_item, from, to);

        gridSetting();
        classView.setAdapter(classAdapter);

        classView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        char outChar=(char)(position+'1');
                        String outStr="click on "+outChar;
                        Toast.makeText(NewsActivity.this, outStr,Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
    void gridSetting(){
        int size = classes.length;
        int length = 60;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (size * (length + 4) * density);
        int itemWidth = (int) (length * density);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.FILL_PARENT);
        classView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        classView.setColumnWidth(itemWidth); // 设置列表项宽
        classView.setHorizontalSpacing(5); // 设置列表项水平间距
        classView.setStretchMode(GridView.NO_STRETCH);
        classView.setNumColumns(size); // 设置列数量=列表集合数
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
