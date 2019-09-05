package com.java.news.myitems;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.java.news.R;

import java.util.List;

/**
 * FileName: TouTiaoTwoMyAdapter
 *
 * @author : Lss kiwilss
 * e-mail : kiwilss@163.com
 * time   : 2018/7/10
 * desc   : ${DESCRIPTION}
 * Description: ${DESCRIPTION}
 */
public class TouTiaoTwoMyAdapter extends MyItemTouchHandler.ItemTouchAdapterImpl {


    private final LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<String> mData;
    AdapterCallBack mAdapterCallBack;

    public TouTiaoTwoMyAdapter(Context context, List<String> data, AdapterCallBack adapterCallBack) {
        mContext = context;
        mData = data;
        this.mAdapterCallBack=adapterCallBack;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        String temp=mData.remove(fromPosition);
        mData.add(toPosition,temp);
    }

    /**禁止自动拖拽
     * @return
     */
//    @Override
//    protected boolean autoOpenDrag() {
//        return false;
//    }



    /**禁止滑动删除
     * @return
     */
    @Override
    protected boolean autoOpenSwipe() {
        return false;
    }

    @Override
    public void onItemRemove(int position) {
        mData.remove(position);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_toutiao_one, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder,
                                 @SuppressLint("RecyclerView") final int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).tvTitle.setText(mData.get(position));

                if (isDelete){
                    if (position > 0) {
                        ((ViewHolder) holder).ivDelete.setVisibility(View.VISIBLE);
                    }
                }else {
                    ((ViewHolder) holder).ivDelete.setVisibility(View.GONE);
                }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //点击监听
                    mAdapterCallBack.onItemClickListener((ViewHolder) holder, position);
                }
            });

//            //长按点击监听
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mAdapterCallBack.onItemLongClickListener((ViewHolder) holder,position);
                    return false;
                }
            });

        }
    }
    private boolean isDelete;
    public void showDeleteIcon(boolean isDelete){
        this.isDelete = isDelete;
        notifyDataSetChanged();
    }

    public interface AdapterCallBack{
        void onItemClickListener(ViewHolder viewHolder, int pos);
        boolean onItemLongClickListener(ViewHolder viewHolder, int pos);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView ivDelete;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_toutiao_one_title);
            ivDelete = itemView.findViewById(R.id.iv_item_toutiao_one_delete);
        }
    }
}
