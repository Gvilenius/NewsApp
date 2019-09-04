package com.java.news.news.newsDetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.java.news.R;
import com.java.news.data.NewsEntity;
import com.java.news.data.RealmHelper;

public class NewsDetailActivity extends AppCompatActivity {
    TextView title;
    TextView publisher;
    TextView time;
    ImageView image1,image2;
    TextView info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        title=findViewById(R.id.news_detail_title);
        publisher=findViewById(R.id.news_detail_publisher);
        time=findViewById(R.id.news_detail_time);
        info=findViewById(R.id.news_detail_info);
        image1=findViewById(R.id.news_detail_image1);
        image2=findViewById(R.id.news_detail_image2);

        Intent intent=getIntent();
        String newsID = intent.getStringExtra("NewsID");

        NewsEntity news= RealmHelper.getInstance().getNewsByID(newsID);
        title.setText(news.getTitle());
        publisher.setText(news.getPublisher());
        time.setText(news.getPublishTime());
        info.setText(news.getContent());

        int numOfImg=news.getImgUrls().size();
        System.out.println(news.getImgUrls());
        System.out.println(numOfImg);
        if(numOfImg>0)
        {
            Glide.with(this).load(news.getImgUrls().first()).error( R.drawable.heart ).dontAnimate().into(image1);
            System.out.println(news.getImgUrls().first());
            if(numOfImg>1)
                Glide.with(this).load(news.getImgUrls().last()).dontAnimate().into(image2);
            else
                image2.setVisibility(View.GONE);
        }
        else
        {
            image1.setVisibility(View.GONE);
            image2.setVisibility(View.GONE);
        }
    }
}
