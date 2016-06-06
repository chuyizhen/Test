package app.yongjiasoftware.com.videoplayer.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import app.yongjiasoftware.com.videoplayer.bean.MedieModel;

/**
 * @Title PlayerService
 * @Description: 音乐播放服务
 * @Author: alvin
 * @Date: 2016/6/1.09:04
 * @E-mail: 49467306@qq.com
 */
public class PlayerService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {
    private static final String TAG = PlayerService.class.getSimpleName();
    private MediaPlayer mMediaPlayer;
    private String mPath;
    private MyBinder mBinder;
    private ArrayList<MedieModel> mList;
    private int mIndex;
    private Handler mHandler = new Handler();
    private Runnable mRunnable;

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mBinder = new MyBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (mMediaPlayer != null) {
////            mMediaPlayer.stop();
//            mMediaPlayer.release();
//        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (mBinder == null)
            mBinder = new MyBinder();
        if (intent != null) {
            mList = intent.getParcelableArrayListExtra("Music");
            mIndex = intent.getIntExtra("index", 0);
        }
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        if (intent != null) {
//            mList = intent.getParcelableArrayListExtra("Music");
//            mIndex = intent.getIntExtra("index", 0);
//        }
        Log.i(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
        if (mRunnable != null) {
            mHandler.removeCallbacks(mRunnable);
        }
        return super.onUnbind(intent);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.e(TAG, "播放结束");
        if (mBinder != null)
            mBinder.toNext();
        if (mRunnable != null)
            mHandler.removeCallbacks(mRunnable);
        sendBroadcast(new Intent("android.mediaplayer.stop"));
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.i(TAG, "音乐文件准备完毕");
        mp.start();
        sendBroadcast(new Intent("android.mediaplayer.play"));
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (mMediaPlayer.isPlaying()) {
                    Intent intent = new Intent("android.mediaplayer.updateProgress");
                    intent.putExtra("progress", mMediaPlayer.getCurrentPosition());
                    sendBroadcast(intent);
                }
                mHandler.postDelayed(this, 100);
            }
        };
        mHandler.postDelayed(mRunnable, 100);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mMediaPlayer.reset();
        return false;
    }


    public class MyBinder extends Binder {
        /**
         * 开始播放
         */
        public void starPlay() {
            try {
                mMediaPlayer.reset();
                mMediaPlayer.setDataSource(getApplicationContext(), Uri.parse(mList.get(mIndex).getUrl()));
                mMediaPlayer.prepareAsync();
//                mMediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 暂停播放
         */
        public void pausePlay() {
            mMediaPlayer.pause();
            sendBroadcast(new Intent("android.mediaplayer.pause"));
        }

        public void toPlay() {
            mMediaPlayer.start();
        }

        /**
         * 后一首
         */
        public void toNext() {
            if (mList.size() > (++mIndex)) {
                try {
                    mMediaPlayer.reset();
                    mMediaPlayer.setDataSource(getApplicationContext(), Uri.parse(mList.get(mIndex).getUrl()));
                    mMediaPlayer.prepareAsync();
//                    mMediaPlayer.start();
                    Log.e(TAG, "成功切换下一首" + "index:" + mIndex);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                mMediaPlayer.stop();
            }
        }

        /**
         * 前一首
         */
        public void toPrevious() {
            if ((--mIndex) > 0) {
                try {
                    Log.e(TAG, "成功切换到上一首");
                    mMediaPlayer.reset();
                    mMediaPlayer.setDataSource(getApplicationContext(), Uri.parse(mList.get(mIndex).getUrl()));
                    mMediaPlayer.prepareAsync();
//                    mMediaPlayer.start();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                mMediaPlayer.stop();
            }
        }

        public int getDuration() {
            if (mMediaPlayer.isPlaying())
                return mMediaPlayer.getDuration();
            else
                return 0;
        }

        public int getCurrentPosition() {
            if (mMediaPlayer.isPlaying())
                return mMediaPlayer.getCurrentPosition();
            else
                return 0;
        }

        public void setSeekBar(int progress) {
            if (mMediaPlayer != null)
                mMediaPlayer.seekTo(progress);

        }

        public boolean isPlaying() {
            return mMediaPlayer.isPlaying();
        }
    }
}
