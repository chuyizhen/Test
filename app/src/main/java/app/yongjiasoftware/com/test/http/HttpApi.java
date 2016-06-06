package app.yongjiasoftware.com.test.http;


import java.util.List;

import app.yongjiasoftware.com.test.bean.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @Title HttpApi
 * @Description: 网络访问接口
 * @Author: alvin
 * @Date: 2016/5/7.13:48
 * @E-mail: 49467306@qq.com
 */
public interface HttpApi {

    @POST("user/login")
    @FormUrlEncoded
    Call<User> login(@Field("account") String account, @Field("password") String password);

    @GET("Home/GetUserList")
    Call<List<User>> getUsers();
}
