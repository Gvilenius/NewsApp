package com.java.news.news.newsList;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.java.news.R;
import com.java.news.main.MainActivity;
import com.java.news.main.SettingActivity;
import com.java.news.myitems.ClassAdaptor;
import com.java.news.myitems.CustomPopupWindow;
import com.java.news.myitems.NewsAdaptor;
import com.java.news.myitems.NewsItem;
import com.java.news.myitems.RefreshableView;
import com.java.news.news.newsDetail.NewsDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class NewsActivity extends AppCompatActivity {
    // 刷新栏信息
    RefreshableView refreshableView;
    ListView listView;
    NewsAdaptor adapter;
    List<NewsItem> newsList = new ArrayList<>();
    int numOfItems = 10;

    // 分类栏信息
    GridView classView;
    HorizontalScrollView scrollView;
    ClassAdaptor classAdapter;
    List<Map<String, Object>> data_list;
    static public String[] classes = {"类别1", "类别2", "类别3", "类别4", "类别5", "类别6", "类别7", "类别8", "类别9", "类别10"};
    CustomPopupWindow mPop;
    GridView mPopGridView;
    int itemWidth;

    //菜单
    ImageView mPopupMenu;
    TextView save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_news);
        refreshableView = (RefreshableView) findViewById(R.id.refreshable_view);
        listView = (ListView) findViewById(R.id.list_view);
        refreshList();
        adapter = new NewsAdaptor(this, R.layout.news_item, newsList);
        listView.setAdapter(adapter);
        refreshableView.setOnRefreshListener(
                new RefreshableView.PullToRefreshListener() {
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
                },
                0);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(
                            AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(NewsActivity.this, NewsDetailActivity.class);
                        String newsTitle = "News " + (char) (position + '0');
                        String newsInfo =
                                "This is the information in the news "
                                        + (char) (position + '0')
                                        + ".\nI use it for test.\nTo see what will happen...";
                        intent.putExtra("news_detail_title", newsTitle);
                        intent.putExtra("news_detail_info", newsInfo);
                        startActivity(intent);
                        //                        char outChar=(char)(position+'0');
                        //                        String outStr="click on "+outChar;
                        //                        Toast.makeText(NewsActivity.this,
                        // outStr,Toast.LENGTH_SHORT).show();
                    }
                });

        // 新闻分类选项栏
        classView = findViewById(R.id.class_view);
        scrollView=findViewById(R.id.class_scroll);
        // 新建适配器
        data_list = new ArrayList<>();
        for (int i = 0; i < classes.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("text", classes[i]);
            data_list.add(map);
        }
        String[] from = {"text"};
        int[] to = {R.id.class_text};
        classAdapter = new ClassAdaptor(this, data_list, R.layout.class_item, from, to);

        gridSetting();
        classView.setAdapter(classAdapter);

        classView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(
                            AdapterView<?> parent, View view, int position, long id) {
                        classChoose(position);
                        char outChar = (char) (position + '1');
                        String outStr = "click on " + outChar;
                        Toast.makeText(NewsActivity.this, outStr, Toast.LENGTH_SHORT).show();
                    }
                });
        //让第一个变色
//        classView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                save=classView.getChildAt(0).findViewById(R.id.class_text);
//                save.setTextColor(getResources().getColor(R.color.colorClassChoose));
//            }
//        });
        //分类栏弹出部分
        mPop=new CustomPopupWindow(this);
        mPopGridView=mPop.gridView;
        mPopGridView.setAdapter(classAdapter);
        mPopGridView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(
                            AdapterView<?> parent, View view, int position, long id) {
                        classChoose(position);
                        char outChar = (char) (position + '1');
                        String outStr = "click on " + outChar;
                        Toast.makeText(NewsActivity.this, outStr, Toast.LENGTH_SHORT).show();
                        scrollToPosition(position);
                        mPop.dismiss();
                    }
                });

        //菜单部分
        mPopupMenu = findViewById(R.id.menu_imageView);
        mPopupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu();
            }
        });

    }

    void gridSetting() {
        int size = classes.length;
        int length = 60;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (size * (length + 4) * density);
        itemWidth = (int) (length * density);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(gridviewWidth, LinearLayout.LayoutParams.FILL_PARENT);
        classView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        classView.setColumnWidth(itemWidth); // 设置列表项宽
        classView.setHorizontalSpacing(0); // 设置列表项水平间距
        classView.setStretchMode(GridView.NO_STRETCH);
        classView.setNumColumns(size); // 设置列数量=列表集合数
    }

    void refreshList() {
        newsList.clear();
        Random ran = new Random();
        for (int i = 0; i < numOfItems; i++) {
            String input = "Item " + i + ": random number =" + ran.nextInt(100);
            newsList.add(new NewsItem(input, R.drawable.ic_launcher_foreground));
        }
    }
    //弹出菜单方法
    void showPopupMenu(){
        PopupMenu popupMenu = new PopupMenu(this,mPopupMenu);
        popupMenu.inflate(R.menu.my_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.option_favorite:
                        Intent intentFavorite = new Intent(NewsActivity.this, FavoriteActivity.class);
                        startActivity(intentFavorite);
                        return true;
                    case R.id.option_setting:
                        Intent intentSetting = new Intent(NewsActivity.this, SettingActivity.class);
                        startActivity(intentSetting);
                        return true;
                    default:
                        //do nothing
                }
                return false;
            }
        });
        popupMenu.show();
    }

    void classChoose(int position)
    {
        classAdapter.setPosition(position);
//        System.out.println(classView.getCount());
//        for(int i=0;i<classView.getCount();i++){
//            View v=classView.getChildAt(i);
//            if (position == i) {
//                save=v.findViewById(R.id.class_text);
//                save.setTextColor(getResources().getColor(R.color.colorClassChoose));
//            } else {
//                save=v.findViewById(R.id.class_text);
//                save.setTextColor(getResources().getColor(R.color.colorClassNotChoose));
//            }
//        }
    }

    public void classChoosePage(View view)
    {
        mPop.showAtLocation(NewsActivity.this.findViewById(R.id.news_view), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    void scrollToPosition(int position)
    {
        scrollView.scrollTo(position*itemWidth,0);
    }
}
