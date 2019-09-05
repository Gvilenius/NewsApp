package com.java.news.news.newsList;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.java.news.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassActivity extends AppCompatActivity {

    // 分类栏信息
    GridView classView;
    SimpleAdapter classAdapter;
    List<Map<String, Object>> data_list;
    ArrayList<String> classes=NewsActivity.classesMy;
    TextView save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_class);

        // 新闻分类选项栏
        classView = (GridView) findViewById(R.id.chooseClass_view);
        // 新建适配器
        data_list = new ArrayList<>();
        for (int i = 0; i < classes.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("text", classes.get(i));
            data_list.add(map);
        }
        String[] from = {"text"};
        int[] to = {R.id.class_text};
        classAdapter = new SimpleAdapter(this, data_list, R.layout.class_item, from, to);

        classView.setAdapter(classAdapter);

        classView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(
                            AdapterView<?> parent, View view, int position, long id) {
                        save.setTextColor(getResources().getColor(R.color.colorClassNotChoose));
                        save=view.findViewById(R.id.class_text);
                        save.setTextColor(getResources().getColor(R.color.colorClassChoose));
                    }
                });
        //让第一个变色
        classView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                save=classView.getChildAt(0).findViewById(R.id.class_text);
                save.setTextColor(getResources().getColor(R.color.colorClassChoose));
            }
        });
    }


}
