package com.java.news.myitems;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.java.news.R;

import java.util.ArrayList;

public class ClassAdapter extends BaseAdapter {
    int savePosition;
    ArrayList<String> mDatas;
    Context mContext;
    public ClassAdapter(Context context, ArrayList<String> data) {
        super();
        mContext=context;
        mDatas=data;
        savePosition=0;
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
        View v = LayoutInflater.from(mContext).inflate(R.layout.class_item, null);
        TextView text=v.findViewById(R.id.class_text);
        text.setText(mDatas.get(position));
        if(savePosition==position)
            text.setTextColor(Color.RED);
        else
            text.setTextColor(Color.BLACK);
        return v;
    }

    public void setPosition(int position){
        savePosition=position;
        notifyDataSetChanged();
    }
    public int getPosition(){
        return savePosition;
    }
}
