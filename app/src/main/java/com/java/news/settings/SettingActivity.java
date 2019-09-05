package com.java.news.settings;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.java.news.R;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_setting);
    }
}
