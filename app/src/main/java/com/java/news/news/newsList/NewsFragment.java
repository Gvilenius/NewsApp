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

public class NewsFragment extends Fragment {
    private ViewPager mViewPager;
    private ArrayList<String> classesMy;
    private MyListener mLis;
    public void setmLis(MyListener lis){
        mLis=lis;
    }

    public static NewsFragment newInstance(ArrayList<String> type) {
        Bundle args = new Bundle();
        NewsFragment fragment = new NewsFragment();
        args.putStringArrayList("category_list", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        classesMy = getArguments().getStringArrayList("category_list");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, null);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mViewPager.setOffscreenPageLimit(3);
        setupViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                mLis.changeScroll(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        return view;
    }

    private void setupViewPager(ViewPager mViewPager) {
        //Fragment中嵌套使用Fragment一定要使用getChildFragmentManager(),否则会有问题
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        for(String s:classesMy)
        {
            adapter.addFragment(NewsListFragment.newInstance(s));
        }
        mViewPager.setAdapter(adapter);
    }

    private String getString(String type) {
        return type;
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        public void addFragment(Fragment fragment) {
            mFragments.add(fragment);
        }
        public void removeAllFragment() {
            mFragments.clear();
        }
        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
    public void resetPage(int pos)
    {
        MyPagerAdapter adapter = (MyPagerAdapter) getmViewPager().getAdapter();
        adapter.removeAllFragment();
        for(String s:classesMy)
        {
            adapter.addFragment(NewsListFragment.newInstance(s));
        }
        adapter.notifyDataSetChanged();
        setPage(pos);
    }
    public void setPage(int pos)
    {
        getmViewPager().setCurrentItem(pos);
    }

    public ViewPager getmViewPager() {
        return mViewPager;
    }
}
