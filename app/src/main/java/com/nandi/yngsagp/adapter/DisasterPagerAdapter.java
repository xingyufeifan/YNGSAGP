package com.nandi.yngsagp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nandi.yngsagp.fragment.DisasterFragment;

import java.util.ArrayList;

/**
 * Created by qingsong on 2017/11/15.
 */

public class DisasterPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String> titleList;

    public DisasterPagerAdapter(FragmentManager fm, ArrayList<String> titleList) {
        super(fm);
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return DisasterFragment.newInstance(titleList.get(position));
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

