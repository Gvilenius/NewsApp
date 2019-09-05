package com.java.news.news.newsList;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.java.news.R;
import com.java.news.data.NewsEntity;
import com.java.news.favorites.FavorActivity;
import com.java.news.history.HistoryActivity;
import com.java.news.myitems.ClassAdapter;
import com.java.news.myitems.TouTiaoTwoActivity;
import com.java.news.settings.SettingActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements NewsListContract.View{
    private NewsListContract.Presenter mPresenter;
    private NewsFragment mFOne;
    Fragment a;

    // 分类栏信息
    GridView classView;
    HorizontalScrollView scrollView;
    ClassAdapter classAdapter;
    static public ArrayList<String> classesMy = new ArrayList<>(Arrays.asList("推荐","财经","科技","社会","汽车","文化","教育","娱乐","军事","健康","体育"));
    static public ArrayList<String> classesAdd =  new ArrayList<>();
    int savePosition=0;
    int itemWidth;

    //菜单
    ImageView mPopupMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_news);

        //开始
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, new AllNewsFragment()).commit();

        // 新闻分类选项栏
        classView = findViewById(R.id.class_view);
        scrollView=findViewById(R.id.class_scroll);
        classAdapter = new ClassAdapter(this, classesMy);
        gridSetting();
        classView.setAdapter(classAdapter);

        classView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(
                            AdapterView<?> parent, View view, int position, long id) {
                        classChoose(position);
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
        int size = classesMy.size();
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

    //弹出菜单方法
    void showPopupMenu(){
        PopupMenu popupMenu = new PopupMenu(this,mPopupMenu);
        popupMenu.inflate(R.menu.my_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.option_favorite:
                        Intent intentFavorite = new Intent(NewsActivity.this, FavorActivity.class);
                        startActivity(intentFavorite);
                        return true;
                    case R.id.option_history:
                        Intent intentHistory = new Intent(NewsActivity.this, HistoryActivity.class);
                        startActivity(intentHistory);
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
        savePosition=position;
    }

    public void classChoosePage(View view)
    {
//        mPop.showAtLocation(NewsActivity.this.findViewById(R.id.news_view), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
        Intent intent = new Intent(this, TouTiaoTwoActivity.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    if(intent!=null){
                        int pos= intent.getIntExtra("choosePosition",0);
                        scrollToPosition(pos);
                        classChoose(pos);
                    }
                }
                break;
        }
    }

    void scrollToPosition(int position)
    {
        scrollView.scrollTo(position*itemWidth,0);
    }


    @Override
    public void setNewsList(List<NewsEntity> newsList) {

    }

    @Override
    public void appendNewsList(List<NewsEntity> newsList) {

    }

    @Override
    public void onError() {

    }


}

