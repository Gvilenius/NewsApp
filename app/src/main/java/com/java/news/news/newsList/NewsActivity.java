package com.java.news.news.newsList;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.java.news.R;
import com.java.news.data.NewsEntity;
import com.java.news.favorites.FavorActivity;
import com.java.news.myitems.ClassAdaptor;
import com.java.news.myitems.CustomPopupWindow;
import com.java.news.myitems.MyData;
import com.java.news.myitems.RefreshAdapter;
import com.java.news.myitems.TouTiaoTwoActivity;
import com.java.news.settings.SettingActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class NewsActivity extends AppCompatActivity implements NewsListContract.View{
    private NewsListContract.Presenter mPresenter;

    // 刷新栏信息
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    List<MyData> mDatas = new ArrayList<>();
    List<MyData> showDatas = new ArrayList<>();
    private RefreshAdapter mRefreshAdapter;
    private LinearLayoutManager mLinearLayoutManager;
//    SwipeRefreshLayout mSwipeRefreshWidget;
//    RecyclerView mRecyclerView;
////    SimpleAdapter adapter;
//    LinearLayoutManager mLayoutManager;
//    List<NewsItem> newsList = new ArrayList<>();
//    int numOfItems = 10;

    // 分类栏信息
    GridView classView;
    HorizontalScrollView scrollView;
    ClassAdaptor classAdapter;
    List<Map<String, Object>> data_list;
    static public ArrayList<String> classesMy = new ArrayList<>(Arrays.asList("推荐","财经","科技","社会","汽车","文化","教育","娱乐","军事","健康","体育"));
    static public ArrayList<String> classesAdd =  new ArrayList<>();
    int savePosition=0;
    CustomPopupWindow mPop;
    GridView mPopGridView;
    int itemWidth;

    //菜单
    ImageView mPopupMenu;
    TextView save;

//    @Override
//    public void onRefresh() {//刷新
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                pageNumber = 0;//分页加载数据的变量
////                waitList.clear();//清空数据集合
////                PostData();//加载数据
//                mSwipeRefreshWidget.setRefreshing(false);//刷新旋转动画停止
//            }
//        }, 1000);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_news);

        mPresenter = new NewsListPresenter(this,"","");

        mRecyclerView = findViewById(R.id.news_list);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        initView();
        initData();
        initListener();

        // 新闻分类选项栏
        classView = findViewById(R.id.class_view);
        scrollView=findViewById(R.id.class_scroll);
        classAdapter = new ClassAdaptor(this, classesMy);
        gridSetting();
        classView.setAdapter(classAdapter);

        classView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(
                            AdapterView<?> parent, View view, int position, long id) {
                        classChoose(position);
//                        char outChar = (char) (position + '1');
//                        String outStr = "click on " + outChar;
//                        Toast.makeText(NewsActivity.this, outStr, Toast.LENGTH_SHORT).show();
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

//    void refreshList() {
//        newsList.clear();
//        Random ran = new Random();
//        for (int i = 0; i < numOfItems; i++) {
//            String input = "Item " + i + ": random number =" + ran.nextInt(100);
//            newsList.add(new NewsItem(input, R.drawable.ic_launcher_foreground));
//        }
//    }
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
        fillData();
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

    //刷新部分初始化
    private void initView() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLUE,Color.GREEN);
    }

    private void initData() {
        mPresenter.refresh();
        initRecylerView();
    }

    private void initRecylerView() {
        mRefreshAdapter = new RefreshAdapter(this,showDatas);
        mLinearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mRefreshAdapter);
    }

    private void initListener() {
        initPullRefresh();
        initLoadMoreListener();
    }

    private void initPullRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.refresh();
                        //刷新完成
                        mSwipeRefreshLayout.setRefreshing(false);
//                        Toast.makeText(NewsActivity.this, "更新了 "+headDatas.size()+" 条目数据", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        });
    }

    private void initLoadMoreListener() {
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem ;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                //判断RecyclerView的状态 是空闲时，同时，是最后一个可见的ITEM时才加载
                if(newState==RecyclerView.SCROLL_STATE_IDLE&&lastVisibleItem+1==mRefreshAdapter.getItemCount()){

                    //设置正在加载更多
                    mRefreshAdapter.changeMoreStatus(mRefreshAdapter.LOADING_MORE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            mPresenter.loadMore();
//                            Toast.makeText(NewsActivity.this, "更新了 "+footerDatas.size()+" 条目数据", Toast.LENGTH_SHORT).show();
                        }
                    }, 1000);


                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //最后一个可见的ITEM
                lastVisibleItem=layoutManager.findLastVisibleItemPosition();
            }
        });

    }

    @Override
    public void setNewsList(List<NewsEntity> newsList) {
        mDatas.clear();
        addData(newsList);
        fillData();
    }

    @Override
    public void appendNewsList(List<NewsEntity> newsList) {
        addData(newsList);
        fillData();
    }

    @Override
    public void onError() {

    }

    void addData(List<NewsEntity> newsList)
    {
        for(NewsEntity o:newsList)
        {

            MyData newData=new MyData();
//            System.out.println(o.getTitle());
            newData.mTitle=o.getTitle();
            if(!o.getImgUrls().isEmpty())
                newData.mURL=o.getImgUrls().first();
            else
                newData.mURL="null";
            newData.mID=o.getNewsID();
            newData.hasSeen=o.getRead();
            newData.mClass=o.getCategory();
            mDatas.add(newData);
//            System.out.println(o.getImgUrls().size());
        }
        if(newsList.size()>0)
            mRefreshAdapter.changeMoreStatus(mRefreshAdapter.PULLUP_LOAD_MORE);
        else
            mRefreshAdapter.changeMoreStatus(mRefreshAdapter.NO_LOAD_MORE);
    }
    void fillData()
    {
        showDatas.clear();
        if(savePosition==0) {
            for (MyData o : mDatas)
                showDatas.add(o);
        }
        else {
            for (MyData o : mDatas) {
                if (classesMy.get(savePosition).equals(o.mClass))
                    showDatas.add(o);
            }
        }
        mRefreshAdapter.refreshShow();
    }
}

