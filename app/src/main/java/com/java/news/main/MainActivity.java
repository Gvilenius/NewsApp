package com.java.news.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.java.news.R;
import com.java.news.data.NewsDetail;
import com.java.news.http.NewsResponse;
import com.java.news.http.RetrofitManager;
import com.java.news.mybutton.MyButton;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements  MainContract.View{

    protected MainContract.Presenter mPresent;
    private RetrofitManager mManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyButton button1 = (MyButton) findViewById(R.id.bt1);
        button1.textView1.setText("点击进入下拉刷新测试");
        mManager = RetrofitManager.getInstance();
    }


    public void sendMessage(View view) {
        Intent intent = new Intent(this, NewsActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);


//        mManager.fetchNewsList("15", "特朗普", "科技")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<NewsResponse>(){
//                    private Disposable mDisposable;
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        mDisposable = d;
//                    }
//
//                    @Override
//                    public void onNext(NewsResponse value) {
//                        MyButton button1 = (MyButton) findViewById(R.id.bt1);
//                        List<NewsDetail> newsList = value.getNewsList();
//
//                        //To handle the data here
//                        mDisposable.dispose();//注销
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        mDisposable.dispose();//注销
//                    }
//
//                    @Override
//                    public void onComplete() {}
//                });

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
