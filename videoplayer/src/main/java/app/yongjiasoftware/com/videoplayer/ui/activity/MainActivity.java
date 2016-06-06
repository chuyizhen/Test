package app.yongjiasoftware.com.videoplayer.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.yongjiasoftware.com.videoplayer.R;
import app.yongjiasoftware.com.videoplayer.ui.fragment.MusicFragment;
import app.yongjiasoftware.com.videoplayer.ui.fragment.VideoFragment;

public class MainActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private String[] mTitle = {"视频播放", "音乐播放"};
    private Fragment[] mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initDatas();
    }


    private void initViews() {
        mTabLayout = (TabLayout) findViewById(R.id.id_tabLayout);


        mViewPager = (ViewPager) findViewById(R.id.id_viewPager);


    }

    private void initDatas() {
        mFragments = new Fragment[2];
        mFragments[0] = new VideoFragment();
        mFragments[1] = new MusicFragment();
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }

            @Override
            public int getCount() {
                return mFragments.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle[position];
            }
        };
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
