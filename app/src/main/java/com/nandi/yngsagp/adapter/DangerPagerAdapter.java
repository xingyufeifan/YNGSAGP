package com.nandi.yngsagp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nandi.yngsagp.fragment.DangerFragment;

import java.util.ArrayList;

/**
 * Created by qingsong on 2017/11/15.
 */

public class DangerPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String> titleList;

    public DangerPagerAdapter(FragmentManager fm, ArrayList<String> titleList) {
        super(fm);
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return DangerFragment.newInstance(titleList.get(position));
    }

    @Override
    public int getCount() {
        return titleList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}

