package com.java.news.main;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.java.news.R;
import com.java.news.data.NewsEntity;
import com.java.news.http.NewsResponse;
import com.java.news.http.RetrofitManager;
import com.java.news.mybutton.MyButton;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity implements  MainContract.View{


    protected MainContract.Presenter mPresent;
    private RetrofitManager mManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyButton button1 = (MyButton) findViewById(R.id.bt1);
        button1.textView1.setText("点击进入下拉刷新测试");

    }


    @SuppressLint("CheckResult")
    public void sendMessage(View view) {

        Intent intent = new Intent(this, NewsActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);

        RetrofitManager.getInstance().fetchNewsList("300", "", "")
                .subscribe(new Observer<NewsResponse>(){
                    private Disposable disposable;
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(NewsResponse value){
                        Realm realm=null;
                        realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        List<NewsEntity> newsList = value.getNewsList();
                        for (NewsEntity news: newsList){
                            realm.copyToRealmOrUpdate(news);
                            System.out.println(news.getNewsID());
                            break;
                        }
        //                To handle the data here, for exmple
                        realm.commitTransaction();

                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Error");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        Realm realm=Realm.getDefaultInstance();
        RealmResults<NewsEntity> result2 = realm.where(NewsEntity.class)
                .equalTo("newsID", "201908300059ff4a44f1896d4c5ca6cb2f11940c402f")
                .findAll();
        for (NewsEntity news: result2){
            System.out.println(news.getContent());
        }
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
