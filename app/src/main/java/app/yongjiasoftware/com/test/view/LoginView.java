package app.yongjiasoftware.com.test.view;

/**
 * @Title LoginView
 * @Description: View模板
 * @Author: alvin
 * @Date: 2016/5/27.09:57
 * @E-mail: 49467306@qq.com
 */
public interface LoginView {
    public String getAccount();

    public String getPassword();

    void showLoading();

    void hideLoading();

    void showMessage(String msg);
}
