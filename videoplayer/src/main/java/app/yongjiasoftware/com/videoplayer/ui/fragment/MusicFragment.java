package app.yongjiasoftware.com.videoplayer.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.List;

import app.yongjiasoftware.com.videoplayer.R;
import app.yongjiasoftware.com.videoplayer.adapter.VideoRecyclerViewAdapter;
import app.yongjiasoftware.com.videoplayer.bean.MedieModel;
import app.yongjiasoftware.com.videoplayer.ui.activity.MainActivity;
import app.yongjiasoftware.com.videoplayer.ui.activity.MusicPlayerActivity;
import app.yongjiasoftware.com.videoplayer.ui.activity.VideoPlayerActivity;
import app.yongjiasoftware.com.videoplayer.widget.DividerItemDecoration;

/**
 * @Title VideoFragment
 * @Description:
 * @Author: alvin
 * @Date: 2016/5/31.14:22
 * @E-mail: 49467306@qq.com
 */
public class MusicFragment extends Fragment {
    private static final String TAG = MusicFragment.class.getSimpleName();
    private View mView;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private VideoRecyclerViewAdapter mAdapter;
    private ImageButton playImageButton;
    private SeekBar mSeekBar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_music, container, false);
        initViews();
        return mView;
    }


    private void initViews() {

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.id_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
        MedieModel medieModel = new MedieModel();
        medieModel.setUrl("http://sc1.111ttt.com/2016/5/06/02/199021429327.mp3");
        final List<MedieModel> list = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            list.add(medieModel);
        mAdapter = new VideoRecyclerViewAdapter(mContext, list);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new VideoRecyclerViewAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("Music", (ArrayList<? extends Parcelable>) list);
                intent.putExtra("index", position);
                intent.setClass(mContext, MusicPlayerActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });


    }
}
