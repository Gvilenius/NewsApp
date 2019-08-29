package com.java.news.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.java.news.R;

public class NewsDetailActivity extends AppCompatActivity {
    TextView title;
    TextView info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        title=findViewById(R.id.news_detail_title);
        info=findViewById(R.id.news_detail_info);

        Intent intent=getIntent();
        String newsTitle = intent.getStringExtra("news_detail_title");
        String newsInfo = intent.getStringExtra("news_detail_info");
        title.setText(newsTitle);
        info.setText(newsInfo);
    }
}
