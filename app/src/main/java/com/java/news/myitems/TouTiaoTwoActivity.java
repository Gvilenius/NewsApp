package com.java.news.myitems;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.java.news.R;
import com.java.news.news.newsList.NewsActivity;

import java.util.ArrayList;

public class TouTiaoTwoActivity extends AppCompatActivity {
    private android.widget.TextView tvtoutiaoonemy;
    private android.widget.TextView tvtoutiaooneedit;
    private RecyclerView rvtoutioaonemy;
    private RecyclerView rvtoutioaoneadd;
    private ArrayList<String> mDataOne;
    private ArrayList<String> mDataTwo;
    private TouTiaoTwoMyAdapter mTouTiaoTwoMyAdapter;
    private TouTiaoTwoAdapter mTouTiaoTwoAdapter;

    boolean isEdit = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tou_tiao_two);
        this.rvtoutioaoneadd = (RecyclerView) findViewById(R.id.rv_toutioa_one_add);
        this.rvtoutioaonemy = (RecyclerView) findViewById(R.id.rv_toutioa_one_my);
        this.tvtoutiaooneedit = (TextView) findViewById(R.id.tv_toutiao_one_edit);
        this.tvtoutiaoonemy = (TextView) findViewById(R.id.tv_toutiao_one_my);

        //初始化第一个列表
        initOneList();
        //初始化第二个列表
        initTwoList();

        //编辑点击
        tvtoutiaooneedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEdit) {
                    //处于编辑状态,点击变成正常状态
                    tvtoutiaooneedit.setText("编辑");
                    tvtoutiaoonemy.setText("点击进入频道");
                    //我的频道右上角图标隐藏
                    mTouTiaoTwoMyAdapter.showDeleteIcon(false);
                    //禁用拖拽
                    //mTouTiaoOneAdapter.disableDragItem();
                } else {
                    //点击变成编辑状态
                    tvtoutiaooneedit.setText("完成");
                    tvtoutiaoonemy.setText("拖拽可以排序");
                    //我的频道右上角图标显示
                    mTouTiaoTwoMyAdapter.showDeleteIcon(true);
                    // 开启拖拽
//                    mTouTiaoOneAdapter.enableDragItem(itemTouchHelper);
                }
                isEdit = !isEdit;
            }
        });

    }
    private void initTwoList() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        mDataTwo = NewsActivity.classesAdd;
        rvtoutioaoneadd.setLayoutManager(gridLayoutManager);

        mTouTiaoTwoAdapter = new TouTiaoTwoAdapter(R.layout.item_toutiao_two, mDataTwo);
        rvtoutioaoneadd.setAdapter(mTouTiaoTwoAdapter);
        //设置添加,移除动画
        rvtoutioaoneadd.setItemAnimator(new DefaultItemAnimator());
        mTouTiaoTwoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                String title = mDataTwo.get(position);
                Log.e("MMM", "onItemClick: "+position+"||"+title );
                //加入到我的频道
                mDataOne.add(title);
                mTouTiaoTwoMyAdapter.notifyItemChanged(mDataOne.size()-1);
                //mTouTiaoTwoAdapter.notifyItemInserted(mDataOne.size()-1);
//
                mDataTwo.remove(position);
                mTouTiaoTwoAdapter.notifyItemRemoved(position);
                //mTouTiaoTwoAdapter.notifyDataSetChanged();
            }
        });
    }
    private void initOneList() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        mDataOne = NewsActivity.classesMy;
        rvtoutioaonemy.setLayoutManager(gridLayoutManager);

        mTouTiaoTwoMyAdapter = new TouTiaoTwoMyAdapter(this, mDataOne, new TouTiaoTwoMyAdapter.AdapterCallBack() {
            @Override
            public void onItemClickListener(TouTiaoTwoMyAdapter.ViewHolder viewHolder, int pos) {
                //点击监听
                //处于编辑状态
                if (isEdit){
                    //点击移除,前两个除外
                    if (pos>0){
                        System.out.println(pos);
                        if(mDataOne.size()<=pos)
                            return;
                        //加入频道推荐,//将其移动到第一个位置
                        String title = mDataOne.get(pos);
                        //Log.e(TAG, "onItem--my--Click: "+position+"||"+title );
                        mDataTwo.add(0,title);
                        mTouTiaoTwoAdapter.notifyItemInserted(0);
                        //mTouTiaoTwoAdapter.notifyDataSetChanged();
                        //我的频道,移除点击的数据
                        mDataOne.remove(pos);
                        mTouTiaoTwoMyAdapter.notifyItemRemoved(pos);
                        //刷新数据
                        mTouTiaoTwoMyAdapter.notifyItemChanged(pos,mDataOne.size()-pos);
                        //mTouTiaoOneAdapter.notifyDataSetChanged();
                    }
                }else {
                    Intent intent = new Intent(TouTiaoTwoActivity.this, NewsActivity.class);
                    intent.putExtra("choosePosition", pos);
                    setResult(RESULT_OK, intent);
                    finish();
                    //点击进入对应详情页
//                    String title = mDataOne.get(pos);
//                    Intent intent = new Intent(TouTiaoTwoActivity.this, TouTiaoDetailActivity.class);
//                    intent.putExtra("title",title);
//                    startActivity(intent);
                }
            }

            @Override
            public boolean onItemLongClickListener(TouTiaoTwoMyAdapter.ViewHolder viewHolder, int pos) {
                if (isEdit){
                    return false;
                }
                if (pos > 0){
                    //长按处于编辑状态
                    isEdit=true;
                    //点击变成编辑状态
                    tvtoutiaooneedit.setText("完成");
                    tvtoutiaoonemy.setText("拖拽可以排序");
                    //我的频道右上角图标显示
                    mTouTiaoTwoMyAdapter.showDeleteIcon(true);
                    return true;
                }
                return false;
            }
        });

        //自定义类实现拖拽,侧滑删除
        MyItemTouchHandler myItemTouchHandler = new MyItemTouchHandler(mTouTiaoTwoMyAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(myItemTouchHandler);
        itemTouchHelper.attachToRecyclerView(rvtoutioaonemy);

        rvtoutioaonemy.setAdapter(mTouTiaoTwoMyAdapter);
        //设置添加,移除动画
        rvtoutioaonemy.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            // 监控返回键
            Intent intent = new Intent(TouTiaoTwoActivity.this, NewsActivity.class);
            intent.putExtra("choosePosition", 0);
            setResult(RESULT_OK, intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
