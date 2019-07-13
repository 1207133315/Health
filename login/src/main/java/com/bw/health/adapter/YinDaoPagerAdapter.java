package com.bw.health.adapter;



import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class YinDaoPagerAdapter extends FragmentPagerAdapter {
    private  List<Fragment> list;
    public YinDaoPagerAdapter(FragmentManager fm, List<Fragment> list1) {
        super(fm);
        this.list=list1;
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }
    @Override
    public int getCount() {
        return list.size();
    }
}
