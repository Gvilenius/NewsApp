package com.java.news.myitems;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    public RefreshAdapter(Context context, List<String> titles, List<String> urls, List<String> ids) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mTitles = titles;
        mURLs = urls;
        mIDs = ids;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.news_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            String str = mTitles.get(position);
            itemViewHolder.mTvContent.setText(str);
            itemViewHolder.mId=mIDs.get(position);
            Glide.with(mContext).load(mURLs.get(position))
                    .placeholder( R.drawable.heart ).error( R.drawable.heart ).dontAnimate().into(itemViewHolder.mImage);
        }
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
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
