package me.eagzzycsl.darkroom.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PickPagerAdapter extends FragmentPagerAdapter {
    private String[] title = {
            "用户应用",
            "系统应用",
    };
    private Fragment[] fragments = {};

    public PickPagerAdapter(FragmentManager fm, FragmentOnDeviceApp[] fragments) {
        super(fm);
        this.fragments = fragments;

    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
