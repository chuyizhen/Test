package app.yongjiasoftware.com.videoplayer.bean;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Title MedieModel
 * @Description:
 * @Author: alvin
 * @Date: 2016/5/31.14:58
 * @E-mail: 49467306@qq.com
 */
public class MedieModel implements Parcelable {
    private static final String TAG = MedieModel.class.getSimpleName();
    private String name;
    private Bitmap mBitmap;


    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeParcelable(this.mBitmap, flags);
        dest.writeString(this.url);
    }

    public MedieModel() {
    }

    protected MedieModel(Parcel in) {
        this.name = in.readString();
        this.mBitmap = in.readParcelable(Bitmap.class.getClassLoader());
        this.url = in.readString();
    }

    public static final Parcelable.Creator<MedieModel> CREATOR = new Parcelable.Creator<MedieModel>() {
        @Override
        public MedieModel createFromParcel(Parcel source) {
            return new MedieModel(source);
        }

        @Override
        public MedieModel[] newArray(int size) {
            return new MedieModel[size];
        }
    };
}
