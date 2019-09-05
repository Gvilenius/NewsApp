package com.java.news.news.newsList;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.View.*;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.java.news.R;
import com.java.news.data.NewsEntity;
import com.java.news.favorites.FavorActivity;
import com.java.news.history.HistoryActivity;
import com.java.news.myitems.ClassAdapter;
import com.java.news.myitems.TouTiaoTwoActivity;
import com.java.news.settings.SettingActivity;
import com.wyt.searchbox.SearchFragment;
import com.wyt.searchbox.custom.IOnSearchClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class NewsActivity extends AppCompatActivity implements NewsListContract.View, OnClickListener, IOnSearchClickListener {
    private NewsListContract.Presenter mPresenter;
    private NewsListFragment mFOne;
    Fragment a;

    // 分类栏信息
    @BindView(R.id.class_view) GridView classView;
    @BindView(R.id.class_scroll) HorizontalScrollView scrollView;
    @BindView(R.id.action_a) FloatingActionButton actionA;
    @BindView(R.id.action_b) FloatingActionButton actionB;
    @BindView(R.id.action_c) FloatingActionButton actionC;
    ClassAdapter classAdapter;
    static public ArrayList<String> classesMy = new ArrayList<>(Arrays.asList("推荐","财经","科技","社会","汽车","文化","教育","娱乐","军事","健康","体育"));
    static public ArrayList<String> classesAdd =  new ArrayList<>();
    int savePosition=0;
    int itemWidth;
    SearchFragment searchFragment = SearchFragment.newInstance();

    //菜单
    @BindView(R.id.search_icon) ImageView searchIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        //开始
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, new NewsFragment()).commit();

        // 新闻分类选项栏
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
        actionA.setOnClickListener(this);
        actionC.setOnClickListener(this);
        actionB.setOnClickListener(this);

        searchFragment.setOnSearchClickListener(this);

        searchIcon.setOnClickListener(this);

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


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.action_a:
                intent = new Intent(NewsActivity.this, FavorActivity.class);
                startActivity(intent);
                break;
            case R.id.action_b:
                intent = new Intent(NewsActivity.this, HistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.action_c:
                intent = new Intent(NewsActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.search_icon:
                searchFragment.showFragment(getSupportFragmentManager(),SearchFragment.TAG);
            default:
                return;
        }
    }

    @Override
    public void OnSearchClick(String keyword) {
        //这里处理逻辑
        Toast.makeText(NewsActivity.this, keyword, Toast.LENGTH_SHORT).show();
    }
}

