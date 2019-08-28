package com.java.news.mybutton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.java.news.R;

import java.util.List;

public class NewsAdaptor extends ArrayAdapter<NewsItem> {
    private int newResourceId;
    public NewsAdaptor(Context context, int resourceId, List<NewsItem> newsList){
        super(context, resourceId, newsList);
        newResourceId = resourceId;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent){

        NewsItem news = getItem (position);
        View view = LayoutInflater.from (getContext ()).inflate (newResourceId, parent, false);

        TextView newsName = view.findViewById (R.id.news_name);
        ImageView newsImage = view.findViewById (R.id.news_image);

        newsName.setText (news.getName ());
        newsImage.setImageResource (news.getImageId ());
        return view;
    }

}
