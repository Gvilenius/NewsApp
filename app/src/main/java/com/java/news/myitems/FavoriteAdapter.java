package com.java.news.myitems;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.java.news.R;

import java.util.List;

public class FavoriteAdapter extends BaseAdapter {
    List<MyData> mDatas;
    Context mContext;
    public FavoriteAdapter(Context context, List<MyData> data) {
        super();
        mContext=context;
        mDatas=data;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View v = LayoutInflater.from(mContext).inflate(R.layout.news_item, null);
        TextView text=v.findViewById(R.id.news_name);
        ImageView image=v.findViewById(R.id.news_image);
        String str = mDatas.get(position).mTitle;
        text.setText(str);
        String theUrl=mDatas.get(position).mURL;
        if(theUrl.equals("null"))
            image.setVisibility(View.GONE);
        else
        {
            image.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(theUrl).placeholder( R.drawable.heart ).error( R.drawable.heart ).dontAnimate().into(image);
        }
        return v;
    }
}
