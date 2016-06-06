/*
 * Copyright (C) 2013 Zhang Rui <bbcallen@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.yongjiasoftware.com.videoplayer.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import app.yongjiasoftware.com.videoplayer.R;
import app.yongjiasoftware.com.videoplayer.video.widget.MediaController;
import app.yongjiasoftware.com.videoplayer.video.widget.VideoView;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class VideoPlayerActivity extends Activity {
    private VideoView mVideoView;
    private View mBufferingIndicator;
    private MediaController mMediaController;

    private String mVideoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        mVideoPath = getIntent().getStringExtra("videoPath");

        Intent intent = getIntent();
        String intentAction = intent.getAction();
        if (!TextUtils.isEmpty(intentAction) && intentAction.equals(Intent.ACTION_VIEW)) {
            mVideoPath = intent.getDataString();
        }

        mBufferingIndicator = findViewById(R.id.buffering_indicator);
        mMediaController = new MediaController(this);

        mVideoView = (VideoView) findViewById(R.id.video_view);
        mVideoView.setMediaController(mMediaController);
        mVideoView.setMediaBufferingIndicator(mBufferingIndicator);

        if (mVideoPath.contains("http")) {
            mVideoView.setVideoURI(Uri.parse(mVideoPath));
        } else {
            mVideoView.setVideoPath(mVideoPath);
        }
        mVideoView.requestFocus();

        mVideoView.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        IjkMediaPlayer.native_profileEnd();
    }
}
