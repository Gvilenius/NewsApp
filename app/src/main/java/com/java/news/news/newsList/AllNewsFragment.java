package com.java.news.news.newsList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.java.news.R;

import java.util.ArrayList;
import java.util.List;

public class AllNewsFragment extends Fragment {
//    private TabLayout mTablayout;
    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, null);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mViewPager.setOffscreenPageLimit(3);
        setupViewPager(mViewPager);
//        mTablayout.addTab(mTablayout.newTab().setText(R.string.top));
//        mTablayout.addTab(mTablayout.newTab().setText(R.string.nba));
//        mTablayout.addTab(mTablayout.newTab().setText(R.string.cars));
//        mTablayout.addTab(mTablayout.newTab().setText(R.string.jokes));
//        mTablayout.setupWithViewPager(mViewPager);
        return view;
    }

    private void setupViewPager(ViewPager mViewPager) {
        //Fragment中嵌套使用Fragment一定要使用getChildFragmentManager(),否则会有问题
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        System.out.println("start");
        adapter.addFragment(NewsFragment.newInstance("推荐"), getString("推荐"));
        adapter.addFragment(NewsFragment.newInstance("财经"), getString("财经"));
        adapter.addFragment(NewsFragment.newInstance("娱乐"), getString("娱乐"));
        adapter.addFragment(NewsFragment.newInstance("体育"), getString("体育"));
        mViewPager.setAdapter(adapter);
    }

    private String getString(String type) {
        return type;
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            System.out.println("adding");
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
