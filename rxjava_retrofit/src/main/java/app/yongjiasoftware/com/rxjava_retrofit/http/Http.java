package app.yongjiasoftware.com.rxjava_retrofit.http;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Title Http
 * @Description:
 * @Author: alvin
 * @Date: 2016/5/30.16:19
 * @E-mail: 49467306@qq.com
 */
public class Http {
    private static final String TAG = Http.class.getSimpleName();
    private static OkHttpClient mOkHttpClient = new OkHttpClient();
    private static HttpAPI mHttpAPI;
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    public static HttpAPI getHttpAPI() {
        if (mHttpAPI == null) {
            Retrofit.Builder retrofit = new Retrofit.Builder();
            retrofit.client(mOkHttpClient);
            retrofit.addCallAdapterFactory(rxJavaCallAdapterFactory);
            retrofit.addConverterFactory(gsonConverterFactory);
            retrofit.baseUrl("http://192.168.1.112/TestDome/");
            mHttpAPI = retrofit.build().create(HttpAPI.class);
        }
        return mHttpAPI;
    }

}
