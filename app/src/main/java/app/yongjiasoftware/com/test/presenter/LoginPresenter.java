package app.yongjiasoftware.com.test.presenter;


import android.text.TextUtils;

import com.google.gson.Gson;

import app.yongjiasoftware.com.test.bean.User;
import app.yongjiasoftware.com.test.http.HttpApi;
import app.yongjiasoftware.com.test.view.LoginView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Title LoginPresenter
 * @Description:
 * @Author: alvin
 * @Date: 2016/5/27.10:57
 * @E-mail: 49467306@qq.com
 */
public class LoginPresenter extends BasePresenter<LoginView> {
    private static final String TAG = LoginPresenter.class.getSimpleName();
    private HttpApi mHttpApi;

    public LoginPresenter() {
        mHttpApi = getRetrofit().create(HttpApi.class);
    }

    public void Login() {
        if (!TextUtils.isEmpty(mView.getAccount()) && !TextUtils.isEmpty(mView.getPassword())) {
            Call<User> userCall = mHttpApi.login(mView.getAccount(), mView.getPassword());
            mView.showLoading();
            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    mView.hideLoading();
                    mView.showMessage(new Gson().toJson(response.body()));
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    mView.hideLoading();
                    mView.showMessage("登录失败，情稍后重试！");
                }
            });
        } else {
            mView.showMessage("用户名/密码不能为空！");
            mView.hideLoading();
        }
    }
}
