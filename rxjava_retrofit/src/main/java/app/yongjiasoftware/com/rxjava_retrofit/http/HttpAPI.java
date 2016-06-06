package app.yongjiasoftware.com.rxjava_retrofit.http;

import app.yongjiasoftware.com.rxjava_retrofit.bean.User;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @Title HttpAPI
 * @Description:
 * @Author: alvin
 * @Date: 2016/5/30.16:10
 * @E-mail: 49467306@qq.com
 */
public interface HttpAPI {
    @POST("user/login")
    @FormUrlEncoded
    Observable<User> logon(@Field("account") String account, @Field("password") String password);
}
