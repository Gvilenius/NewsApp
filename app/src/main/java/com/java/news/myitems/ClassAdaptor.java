package com.java.news.myitems;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.java.news.R;

import java.util.List;
import java.util.Map;

public class ClassAdaptor extends SimpleAdapter {
    int savePosition;
    public ClassAdaptor(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        savePosition=0;
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View v = super.getView(position, convertView, parent);
        TextView text=v.findViewById(R.id.class_text);
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
}
