package com.java.news.myitems;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.java.news.R;

import java.util.List;

public class TouTiaoTwoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public TouTiaoTwoAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_item_toutiao_two_title,item);
    }
}
