package app.yongjiasoftware.com.rxjava_retrofit.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Title User
 * @Description:
 * @Author: alvin
 * @Date: 2016/5/7.13:54
 * @E-mail: 49467306@qq.com
 */
public class User implements Parcelable {
    private static final String TAG = User.class.getSimpleName();

    /**
     * id : 4
     * account : test01
     * password : 123456
     * type : 1
     */

    private int id;
    private String account;
    private String password;
    private String name;



    private int age;
    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public User() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.account);
        dest.writeString(this.password);
        dest.writeString(this.name);
        dest.writeInt(this.age);
        dest.writeInt(this.type);
    }

    protected User(Parcel in) {
        this.id = in.readInt();
        this.account = in.readString();
        this.password = in.readString();
        this.name = in.readString();
        this.age = in.readInt();
        this.type = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
