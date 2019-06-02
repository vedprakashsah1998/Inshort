package com.client.vpman.inshort;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MainFragment.newInstance("MainFragment, Instance 1");
            default:
                return MainFragment.newInstance("MainFragment, Default");
        }
    }

    @Override
    public int getCount() {
        return 1;
    }

}
