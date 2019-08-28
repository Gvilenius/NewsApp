package com.java.news.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.java.news.R;

public class MainActivity extends AppCompatActivity implements  MainContract.View{

    private MainContract.Presenter mPresent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    public void switch2Favorites(){

    }
}
