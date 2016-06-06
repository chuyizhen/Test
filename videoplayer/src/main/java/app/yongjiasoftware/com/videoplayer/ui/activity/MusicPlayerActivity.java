package app.yongjiasoftware.com.videoplayer.ui.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import app.yongjiasoftware.com.videoplayer.R;
import app.yongjiasoftware.com.videoplayer.bean.MedieModel;
import app.yongjiasoftware.com.videoplayer.service.PlayerService;

/**
 * @Title MusicPlayerActivity
 * @Description:
 * @Author: alvin
 * @Date: 2016/6/1.10:10
 * @E-mail: 49467306@qq.com
 */
public class MusicPlayerActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private static final String TAG = MusicPlayerActivity.class.getSimpleName();
    private PlayerService.MyBinder mBinder;
    private ArrayList<MedieModel> mList;
    private int mIndex;
    private ImageButton previousImageButton, nextImageButton, playImageButton;
    private SeekBar mSeekBar;
    private TextView leftTextView, rightTextView;
    private PlayReceiver mReceiver;


    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "连接成功");
            mBinder = (PlayerService.MyBinder) service;
            mBinder.starPlay();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        initDatas();
        initReceiver();
        initViews();

    }

    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.mediaplayer.play");
        intentFilter.addAction("android.mediaplayer.pause");
        intentFilter.addAction("android.mediaplayer.stop");
        intentFilter.addAction("android.mediaplayer.updateProgress");

        mReceiver = new PlayReceiver();
        registerReceiver(mReceiver, intentFilter);


    }

    private void initDatas() {
        if (getIntent() != null) {
            mList = getIntent().getParcelableArrayListExtra("Music");
            mIndex = getIntent().getIntExtra("index", 0);
        }
        Intent intent = new Intent(this, PlayerService.class);
        intent.putExtra("Music", mList);
        intent.putExtra("index", mIndex);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private void initViews() {
        previousImageButton = (ImageButton) findViewById(R.id.id_btn_previous);
        previousImageButton.setOnClickListener(this);
        playImageButton = (ImageButton) findViewById(R.id.id_btn_play);
        playImageButton.setOnClickListener(this);
        nextImageButton = (ImageButton) findViewById(R.id.id_btn_next);
        nextImageButton.setOnClickListener(this);

        mSeekBar = (SeekBar) findViewById(R.id.id_seekBar);
        mSeekBar.setOnSeekBarChangeListener(this);

        leftTextView = (TextView) findViewById(R.id.id_tv_leftTime);
        rightTextView = (TextView) findViewById(R.id.id_tv_rightTime);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mConnection != null) {
            unbindService(mConnection);
        }
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_previous:
                if (mBinder != null) {
                    mBinder.toPrevious();
                }
                break;
            case R.id.id_btn_play:
                if (mBinder != null) {
                    if (mBinder.isPlaying()) {
                        mBinder.pausePlay();
                        playImageButton.setBackgroundResource(R.drawable.play);
                    } else {
                        mBinder.toPlay();
                        playImageButton.setBackgroundResource(R.drawable.pause);
                    }
                }
                break;
            case R.id.id_btn_next:
                if (mBinder != null) {
                    mBinder.toNext();
                    Log.i(TAG, "播放音乐下一首！");
                }
                break;
        }
    }

    // 将ms转换为分秒时间显示函数
    public String ShowTime(int time) {
        // 将ms转换为s
        time /= 1000;
        // 求分
        int minute = time / 60;
        // 求秒
        int second = time % 60;
        minute %= 60;
        return String.format("%02d:%02d", minute, second);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (mBinder != null) {
            mBinder.setSeekBar(seekBar.getProgress());
        }
    }

    class PlayReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals("android.mediaplayer.play")) {
                if (mBinder != null) {
                    Log.i(TAG, "接受到广播");
                    rightTextView.setText(ShowTime(mBinder.getDuration()));
                    mSeekBar.setMax(mBinder.getDuration());
                }

            } else if (intent.getAction().equals("android.mediaplayer.stop")) {
                mSeekBar.setProgress(0);

            } else if (intent.getAction().equals("android.mediaplayer.pause")) {
            } else if (intent.getAction().equals("android.mediaplayer.updateProgress")) {
                int progress = intent.getIntExtra("progress", -1);
                if (progress != -1) {
                    mSeekBar.setProgress(mBinder.getCurrentPosition());
                    leftTextView.setText(ShowTime(mBinder.getCurrentPosition()));
                }
            }

        }
    }
}
