package app.yongjiasoftware.com.rxjava_retrofit.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import app.yongjiasoftware.com.rxjava_retrofit.R;
import app.yongjiasoftware.com.rxjava_retrofit.bean.User;
import app.yongjiasoftware.com.rxjava_retrofit.http.Http;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Subscription mSubscription = Http.getHttpAPI()
                .logon("admin", "123456")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(User user) {
                        Log.e("测试", new Gson().toJson(user));
                    }
                });
    }
}
