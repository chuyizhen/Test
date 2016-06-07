package app.yongjiasoftware.com.videoplayer.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
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
public class PlayerService extends Service implements MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnBufferingUpdateListener {
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
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型
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
        if (mRunnable != null)
            mHandler.removeCallbacks(mRunnable);

        Intent intent = new Intent("android.mediaplayer.stop");
        sendBroadcast(intent);
    }

    @Override
    public void onPrepared(final MediaPlayer mp) {
        Log.i(TAG, "音乐文件准备完毕");
        mp.start();
        sendBroadcast(new Intent("android.mediaplayer.play"));
        mp.setOnBufferingUpdateListener(this);
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (mp.isPlaying()) {
                    Intent intent = new Intent("android.mediaplayer.updateProgress");
                    intent.putExtra("progress", mp.getCurrentPosition());
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

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        int d = mp.getDuration() / 100;
        Intent intent = new Intent("android.mediaplayer.secondSeekBar");
        intent.putExtra("secondSeekBar", percent * d);
        sendBroadcast(intent);
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
         * 切换到下一首并返回当前位置
         *
         * @return
         */
        public int toNext() {
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
                mIndex = mList.size() - 1;
                mMediaPlayer.stop();
            }
            return mIndex;
        }


        /**
         * 切换到上一首 并返回位置
         *
         * @return
         */
        public int toPrevious() {
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
                mIndex = 0;
                mMediaPlayer.stop();
            }
            return mIndex;
        }

        /**
         * 获取歌曲的总长度
         *
         * @return
         */
        public int getDuration() {
            if (mMediaPlayer.isPlaying())
                return mMediaPlayer.getDuration();
            else
                return 0;
        }

        /**
         * 获取当前的播放进度
         *
         * @return
         */
        public int getCurrentPosition() {
            if (mMediaPlayer.isPlaying())
                return mMediaPlayer.getCurrentPosition();
            else
                return 0;
        }

        /**
         * 跳转到SeekBar 所在位置
         *
         * @param progress
         */
        public void setSeekBar(int progress) {
            if (mMediaPlayer != null)
                mMediaPlayer.seekTo(progress);

        }

        /**
         * 获取当前音乐文件的播放位置
         *
         * @return
         */
        public int getIndex() {
            return mIndex;
        }

        /**
         * 判断播放器当前播放状态
         *
         * @return
         */
        public boolean isPlaying() {
            return mMediaPlayer.isPlaying();
        }
    }
}
