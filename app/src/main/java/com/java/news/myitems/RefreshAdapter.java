package com.java.news.myitems;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.java.news.R;
import com.java.news.news.newsDetail.NewsDetailActivity;

import java.util.List;

public class RefreshAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> mTitles, mURLs, mIDs;

    private static final int TYPE_ITEM   = 0;
    private static final int TYPE_FOOTER = 1;
    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载中
    public static final int LOADING_MORE     = 1;
    //没有加载更多 隐藏
    public static final int NO_LOAD_MORE     = 2;
    //上拉加载更多状态-默认为0
    private int mLoadMoreStatus = LOADING_MORE;

    public RefreshAdapter(Context context, List<String> titles, List<String> urls, List<String> ids) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mTitles = titles;
        mURLs = urls;
        mIDs = ids;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View itemView = mInflater.inflate(R.layout.news_item, parent, false);
            return new ItemViewHolder(itemView);
        } else if (viewType == TYPE_FOOTER){
            View itemView = mInflater.inflate(R.layout.load_more, parent, false);
            return new FooterViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            String str = mTitles.get(position);
            itemViewHolder.mTvContent.setText(str);
            itemViewHolder.mId=mIDs.get(position);
            String theUrl=mURLs.get(position);
//            System.out.println(str);
            if(theUrl.equals("null"))
                itemViewHolder.mImage.setVisibility(View.GONE);
            else
            {
                itemViewHolder.mImage.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(theUrl).placeholder( R.drawable.heart ).error( R.drawable.heart ).dontAnimate().into(itemViewHolder.mImage);
//                System.out.println(theUrl);
            }
        }else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            switch (mLoadMoreStatus) {
                case PULLUP_LOAD_MORE:
                    footerViewHolder.mTvLoadText.setText("上拉加载更多");
                    footerViewHolder.mPbLoad.setVisibility(View.GONE);
                    break;
                case LOADING_MORE:
                    footerViewHolder.mTvLoadText.setText("正在加载...");
                    footerViewHolder.mPbLoad.setVisibility(View.VISIBLE);
                    break;
                case NO_LOAD_MORE:
                    //隐藏加载更多
                    footerViewHolder.mLoadLayout.setVisibility(View.GONE);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mTitles.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            //最后一个item设置为footerView
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView mTvContent;
        ImageView mImage;
        String mId;
        public ItemViewHolder(View itemView) {
            super(itemView);
            mTvContent = itemView.findViewById(R.id.news_name);
            mImage=itemView.findViewById(R.id.news_image);
            initListener(itemView);
        }

        private void initListener(View itemView) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, NewsDetailActivity.class);
                    String message = mId;
                    intent.putExtra("NewsID", message);
                    mContext.startActivity(intent);
//                    Toast.makeText(mContext, "info "+ mTvContent.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        ProgressBar mPbLoad;
        TextView     mTvLoadText;
        LinearLayout mLoadLayout;
        public FooterViewHolder(View itemView) {
            super(itemView);
            mPbLoad = itemView.findViewById(R.id.pbLoad);
            mTvLoadText = itemView.findViewById(R.id.tvLoadText);
            mLoadLayout = itemView.findViewById(R.id.loadLayout);
        }
    }

    /**
     * 更新加载更多状态
     * @param status
     */
    public void changeMoreStatus(int status){
        mLoadMoreStatus=status;
        notifyDataSetChanged();
    }

    public void AddHeaderItem(List<String> items){
        mTitles.addAll(0,items);
        notifyDataSetChanged();
    }

    public void AddFooterItem(List<String> items){
        mTitles.addAll(items);
        notifyDataSetChanged();
    }
    public void RefreshTheView(){
        notifyDataSetChanged();
    }
}
