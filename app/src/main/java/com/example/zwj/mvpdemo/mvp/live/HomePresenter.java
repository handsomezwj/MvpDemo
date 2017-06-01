package com.example.zwj.mvpdemo.mvp.live;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.zwj.mvpdemo.R;
import com.example.zwj.mvpdemo.base.BaseActivity;
import com.example.zwj.mvpdemo.base.BasePresenter;
import com.example.zwj.mvpdemo.bean.GankBean;
import com.example.zwj.mvpdemo.http.api.GankApi;
import com.example.zwj.mvpdemo.http.callback.OnResultCallBack;
import com.example.zwj.mvpdemo.http.subscriber.HttpSubscriber;
import com.example.zwj.mvpdemo.utils.FCLogger;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * <b>创建时间</b> 17/5/31 <br>
 *
 * @author zhouwenjun
 */

public class HomePresenter extends BasePresenter<HomeContact.View> {

    private SlidingTabLayout mSlHome;
    private ViewPager mVpHome;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "小视频", "关注", "热门"
    };

    @Inject
    public HomePresenter(HomeContact.View rootView) {
        super(rootView);
    }

    /**
     * 加载首页数据
     */
    public void getMeiZhiData(Context context, String type, int count, int page) {
        HttpSubscriber<List<GankBean>> mHttpObserver = new HttpSubscriber<>(new OnResultCallBack<List<GankBean>>() {
            @Override
            public void onSuccess(List<GankBean> gankBeanList) {
                if (gankBeanList != null && gankBeanList.size() > 0) {
                    mRootView.dismissLoading();
                }
            }

            @Override
            public void onError(int code, String errorMsg) {
                FCLogger.debug(errorMsg);
            }
        });
        GankApi.getInstance(context).getGankData(mHttpObserver, type, count, page);
    }

    /**
     * 初始化tabLayout
     *
     * @param context
     */
    public void initSlidingTab(View rootView, BaseActivity context) {
        for (int i = 0; i < mTitles.length; i++) {
            mFragments.add(HomeTypeFragment.newInstance(mTitles[i], i + ""));
        }
        mSlHome = (SlidingTabLayout) rootView.findViewById(R.id.tl_home);
        mVpHome = (ViewPager) rootView.findViewById(R.id.vp_home);
        MyPagerAdapter mAdapter = new MyPagerAdapter(context.getSupportFragmentManager());
        mVpHome.setAdapter(mAdapter);
        mSlHome.setViewPager(mVpHome);
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
