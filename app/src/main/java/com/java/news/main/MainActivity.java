package com.java.news.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.java.news.R;
import com.java.news.mybutton.MyButton;

public class MainActivity extends AppCompatActivity implements  MainContract.View{

    private MainContract.Presenter mPresent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        MyButton button1 = (MyButton) findViewById(R.id.bt1);
        button1.textView1.setText("点击进入下拉刷新测试");
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, NewsActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }









    @Override
    public void setPresenter(MainContract.Presenter presenter){
        mPresent = presenter;
    }

    @Override
    public void switch2News(){

    }

    @Override
    public void switch2Settings(){

    }

    @Override
    public void switch2Favorites() {
    }
}
