//package com.java.news.news;
//
//import androidx.fragment.app.Fragment;
//import androidx.viewpager.widget.PagerAdapter;
//import androidx.viewpager.widget.ViewPager;
//
//import java.util.Locale;
//
///*
// * Created by ljf on 2019-9-5.
// */
//
//public class NewsFragment extends Fragment {
//    private ViewPager mViewPager;
//    private MyPagerAdapter mPagerAdapter;
//
//
//
//
//
//
//    public class MyPagerAdapter extends PagerAdapter {
//        private List<Category> viewLists;
//
//        public MyPagerAdapter() {
//        }
//
//        public MyPagerAdapter(ArrayList<View> viewLists) {
//            super();
//            this.viewLists = viewLists;
//        }
//
//        @Override
//        public int getCount() {
//            return viewLists.size();
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == object;
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            container.addView(viewLists.get(position));
//            return viewLists.get(position);
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView(viewLists.get(position));
//        }
//    }
//}
