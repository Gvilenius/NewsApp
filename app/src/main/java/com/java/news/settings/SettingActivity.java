package com.java.news.settings;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.java.news.R;
import com.java.news.myitems.NightMode;
import com.java.news.news.newsList.MyListener2;

public class SettingActivity extends AppCompatActivity {

    MyListener2 mLis;
    Switch mSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(NightMode.getDeleg());
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_setting);
        mLis=NightMode.newsLis;
        mSwitch=findViewById(R.id.switch1);
        mSwitch.setChecked(NightMode.getMode());
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                NightMode.changeMode();
                mLis.changeMode();
                startActivity(new Intent(SettingActivity.this,SettingActivity.class));
//                overridePendingTransition(R.anim.animo_alph_close, R.anim.animo_alph_close);
                finish();
            }
        });
    }

}
