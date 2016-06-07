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
    private boolean isPlaying = false;


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

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public MedieModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeParcelable(this.mBitmap, flags);
        dest.writeByte(this.isPlaying ? (byte) 1 : (byte) 0);
        dest.writeString(this.url);
    }

    protected MedieModel(Parcel in) {
        this.name = in.readString();
        this.mBitmap = in.readParcelable(Bitmap.class.getClassLoader());
        this.isPlaying = in.readByte() != 0;
        this.url = in.readString();
    }

    public static final Creator<MedieModel> CREATOR = new Creator<MedieModel>() {
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
