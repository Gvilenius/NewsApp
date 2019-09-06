package com.java.news.news.newsDetail;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.java.news.NewsApplication;
import com.java.news.R;
import com.java.news.data.NewsEntity;
import com.java.news.myitems.NightMode;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import butterknife.BindView;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

@RuntimePermissions
public class NewsDetailActivity extends AppCompatActivity implements NewsDetailContract.View {
    TextView title;
    TextView publisher;
    TextView time;
    ImageView image1,image2;
    ImageView fav,notFav;
    boolean isFavorite;
    NewsEntity news;
    private JzvdStd jzVideoPlayerStandard;

    TextView info;

    NewsDetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(NightMode.getDeleg());
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_news_detail);
        title=findViewById(R.id.news_detail_title);
        publisher=findViewById(R.id.news_detail_publisher);
        time=findViewById(R.id.news_detail_time);
        info=findViewById(R.id.news_detail_info);
        image1=findViewById(R.id.news_detail_image1);
        image2=findViewById(R.id.news_detail_image2);
        fav=findViewById(R.id.fav_button);
        notFav=findViewById(R.id.notFav_button);

        Intent intent=getIntent();
        String newsID = intent.getStringExtra("NewsID");


        mPresenter=new NewsDetailPresenter(this);
//        System.out.println(newsID);
        news= mPresenter.getNews(newsID);
        isFavorite=news.getFavor();
        setFavButton();

//        System.out.println(0);
        mPresenter.addToHis(news);
//        System.out.println(1);
        title.setText(news.getTitle());
        publisher.setText(news.getPublisher());
        time.setText(news.getPublishTime());
        info.setText(news.getContent());

//        System.out.println(2);
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

//        System.out.println(3);

        String video_url = news.getVideo();
        jzVideoPlayerStandard = findViewById(R.id.jz_video_player);
        if(video_url.length()!=0)
            jzVideoPlayerStandard.setUp(video_url, "video");
        else
            jzVideoPlayerStandard.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    public void favorateClick(View view)
    {
        isFavorite=!isFavorite;
        if(isFavorite)
            mPresenter.favor(news);
        else
            mPresenter.unFavor(news);
        setFavButton();
    }

    @NeedsPermission(WRITE_EXTERNAL_STORAGE)
    public void shareClick(View view)
    {
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myNews/";
        Uri uri = null;
        Bitmap bitmap = ((GlideBitmapDrawable) (image1).getDrawable()).getBitmap();

//        Calendar now = new GregorianCalendar();
//        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
//        String fileName = simpleDate.format(now.getTime());
//
//
//        try {
//            File file = new File(dir + fileName + ".jpg");
//            FileOutputStream out = new FileOutputStream(file);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
//            out.flush();
//            out.close();
//            uri = Uri.fromFile(file);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        String content = news.getContent();
        if (content.length() > 100) content = "概要:" + content.substring(0, 100) + "...";


        if(uri != null){
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setType("image/*");
            shareIntent.putExtra("sms_body", content);
        }else{
            shareIntent.setType("text/plain");
        }


        shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        shareIntent.putExtra(Intent.EXTRA_TEXT, news.getUrl());

        startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    private void setFavButton()
    {
        if(isFavorite)
        {
            fav.setVisibility(View.VISIBLE);
            notFav.setVisibility(View.GONE);
        }
        else
        {
            fav.setVisibility(View.GONE);
            notFav.setVisibility(View.VISIBLE);
        }
    }


}
