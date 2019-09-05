package com.java.news.myitems;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.java.news.R;

public class CustomPopupWindow extends PopupWindow {
    public GridView gridView;
    private View mPopView;
//    private OnItemClickListener mListener;

    public CustomPopupWindow(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    init(context);
    setPopupWindow();
//    gridView.setOnClickListener(this);
    }
/**
 * 初始化
 *
 * @param context
 */
private void init(Context context) {        // TODO Auto-generated method stub
    LayoutInflater inflater = LayoutInflater.from(context);        //绑定布局
    mPopView = inflater.inflate(R.layout.activity_class, null);
    gridView=mPopView.findViewById(R.id.chooseClass_view);
}    /**
 * 设置窗口的相关属性
 */
@SuppressLint("InlinedApi")
private void setPopupWindow() {
    this.setContentView(mPopView);// 设置View
    this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
    this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);// 设置弹出窗口的高
    this.setFocusable(true);// 设置弹出窗口可
    this.setAnimationStyle(R.style.mypopwindow_anim_style);// 设置动画
    this.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明
    mPopView.setOnTouchListener(new View.OnTouchListener() {// 如果触摸位置在窗口外面则销毁

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // TODO Auto-generated method stub
            int height = mPopView.findViewById(R.id.chooseClass_view).getTop();
            int y = (int) event.getY();
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (y < height) {
                dismiss();
                }
            }
            return true;
        }
    });
}    /**
 * 定义一个接口，公布出去 在Activity中操作按钮的单击事件
 */
//public interface OnItemClickListener {
//    void setOnItemClick(View v);
//}
//public void setOnItemClickListener(OnItemClickListener listener) {
//    this.mListener = listener;
//}
//@Override
//public void onClick(View v) {
//    // TODO Auto-generated method stub
//    if (mListener != null) {
//        mListener.setOnItemClick(v);
//    }
//}

}

