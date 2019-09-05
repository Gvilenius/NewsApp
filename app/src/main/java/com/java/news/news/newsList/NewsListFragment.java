package com.java.news.news.newsList;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.java.news.R;
import com.java.news.data.NewsEntity;
import com.java.news.myitems.MyData;
import com.java.news.myitems.RefreshAdapter;

import java.util.ArrayList;
import java.util.List;

public class NewsListFragment extends Fragment implements NewsListContract.View{
    private static final String TAG = "NewsListFragment";
    // 刷新栏信息
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    List<MyData> mDatas = new ArrayList<>();
    private RefreshAdapter mRefreshAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    String mType;
    NewsListPresenter mNewsPresenter;

    public static Fragment newInstance(String type) {
        Bundle args = new Bundle();
        NewsListFragment fragment = new NewsListFragment();
        args.putString("category", type);
        System.out.println(type);System.out.println(type);System.out.println(type);System.out.println(type);System.out.println(type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println(getArguments());
        mType = getArguments().getString("category");
        System.out.println(mType);
        mNewsPresenter = new NewsListPresenter(this,mType,"");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_newslist,container,false);
        mSwipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView=view.findViewById(R.id.news_list);
        initView();
        initData();
        initListener();
        return view;
    }

    //刷新部分初始化
    private void initView() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLUE,Color.GREEN);
    }

    private void initData() {
        initRecylerView();
        mNewsPresenter.refresh();
    }

    private void initRecylerView() {
        mRefreshAdapter = new RefreshAdapter(getActivity().getApplicationContext(),mDatas);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
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
                        mNewsPresenter.refresh();
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

                            mNewsPresenter.loadMore();
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
            newData.hasSeen=o.getRead();
            newData.mClass=o.getCategory();
            mDatas.add(newData);
        }
        if(newsList.size()>0)
            mRefreshAdapter.changeMoreStatus(mRefreshAdapter.PULLUP_LOAD_MORE);
        else
            mRefreshAdapter.changeMoreStatus(mRefreshAdapter.NO_LOAD_MORE);
    }
}
