package com.java.news.mybutton;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.java.news.R;

public class MyButton extends LinearLayout {
    public TextView textView1,textView2;

    public MyButton(Context context)
    {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.my_button, this,true);

        this.textView1 = (TextView)findViewById(R.id.button_textView);
        this.textView2 = (TextView)findViewById(R.id.button_textView2);

        this.setClickable(true);
        this.setFocusable(true);
    }
}
