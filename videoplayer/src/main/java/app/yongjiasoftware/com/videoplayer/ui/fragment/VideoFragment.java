package app.yongjiasoftware.com.videoplayer.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import app.yongjiasoftware.com.videoplayer.R;
import app.yongjiasoftware.com.videoplayer.adapter.VideoRecyclerViewAdapter;
import app.yongjiasoftware.com.videoplayer.bean.MedieModel;
import app.yongjiasoftware.com.videoplayer.ui.activity.VideoPlayerActivity;
import app.yongjiasoftware.com.videoplayer.widget.DividerItemDecoration;

/**
 * @Title VideoFragment
 * @Description:
 * @Author: alvin
 * @Date: 2016/5/31.14:22
 * @E-mail: 49467306@qq.com
 */
public class VideoFragment extends Fragment {
    private static final String TAG = VideoFragment.class.getSimpleName();
    private View mView;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private VideoRecyclerViewAdapter mAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_video, container, false);
        initViews();
//        ViewGroup p = (ViewGroup) mView.getParent();
//        if (p != null) {
//            p.removeAllViewsInLayout();
//        }
        return mView;
    }

    private void initViews() {
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.id_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
        MedieModel medieModel = new MedieModel();
        medieModel.setUrl("http://gslb.miaopai.com/stream/~87NAgp0sfBSon~KPKuDeA__.mp4");
        final List<MedieModel> list = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            list.add(medieModel);
        mAdapter = new VideoRecyclerViewAdapter(mContext, list);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new VideoRecyclerViewAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("videoPath", list.get(position).getUrl());
                intent.setClass(mContext, VideoPlayerActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

    }
}
