package com.java.news.search;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.java.news.R;
import com.java.news.data.NewsEntity;
import com.java.news.myitems.MyData;
import com.java.news.myitems.RefreshAdapter;
import com.java.news.news.newsList.NewsListContract;

import java.util.ArrayList;
import java.util.List;

//import com.java.news.myitems.NewsAdaptor;

public class SearchActivity extends AppCompatActivity implements NewsListContract.View{

    SearchPresenter mPresenter;
    // 刷新栏信息
    RecyclerView mRecyclerView;
    List<MyData> mDatas = new ArrayList<>();
    private RefreshAdapter mRefreshAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent=getIntent();
        String keyword=intent.getStringExtra("keyword");
        mPresenter=new SearchPresenter(this,keyword);
        mRecyclerView=findViewById(R.id.search_list);
        initData();
        initListener();
    }

    private void initData() {
        initRecylerView();
        mPresenter.refresh();
    }

    private void initRecylerView() {
        mRefreshAdapter = new RefreshAdapter(this,mDatas);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mRefreshAdapter);
    }
    private void initListener() {
        initLoadMoreListener();
    }


    private void initLoadMoreListener() {
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                //判断RecyclerView的状态 是空闲时，同时，是最后一个可见的ITEM时才加载
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mRefreshAdapter.getItemCount()) {

                    //设置正在加载更多
                    mRefreshAdapter.changeMoreStatus(mRefreshAdapter.LOADING_MORE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPresenter.loadMore();
//                            mRefreshAdapter.changeMoreStatus(mRefreshAdapter.PULLUP_LOAD_MORE);
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
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public void setNewsList(List<NewsEntity> newsList) {
        mDatas.clear();
        System.out.println(newsList.size());
        addData(newsList);
    }

    @Override
    public void appendNewsList(List<NewsEntity> newsList) {
        addData(newsList);
    }

    @Override
    public void onError() {

    }

    void addData(List<NewsEntity> newsList)
    {
        for(NewsEntity o:newsList)
        {
            MyData newData=new MyData();
            newData.mTitle=o.getTitle();
            if(!o.getImgUrls().isEmpty())
                newData.mURL=o.getImgUrls().first();
            else
                newData.mURL="null";
            newData.mID=o.getNewsID();
            NewsEntity his=mPresenter.getNewsById(newData.mID);
            if(his==null)
                newData.hasSeen=o.getRead();
            else
                newData.hasSeen=his.getRead();
            newData.mClass=o.getCategory();
            mDatas.add(newData);
        }
        if(newsList.size()>0)
            mRefreshAdapter.changeMoreStatus(mRefreshAdapter.PULLUP_LOAD_MORE);
        else
            mRefreshAdapter.changeMoreStatus(mRefreshAdapter.NO_LOAD_MORE);
    }

}
