package app.yongjiasoftware.com.test.presenter;


import app.yongjiasoftware.com.test.Constant;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Title BasePresenter
 * @Description:
 * @Author: alvin
 * @Date: 2016/5/27.11:04
 * @E-mail: 49467306@qq.com
 */
public class BasePresenter<T> {
    private static final String TAG = BasePresenter.class.getSimpleName();
    private Retrofit mRetrofit;
    public T mView;

    public Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constant.HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    public void attach(T mView) {
        this.mView = mView;
    }

    public void dettach() {
        mView = null;
    }
}
