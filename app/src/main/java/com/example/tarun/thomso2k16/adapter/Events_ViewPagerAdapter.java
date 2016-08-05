package com.example.tarun.thomso2k16.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.tarun.thomso2k16.EventsDay0;
import com.example.tarun.thomso2k16.EventsDay1;
import com.example.tarun.thomso2k16.EventsDay3;
import com.example.tarun.thomso2k16.EventsDay2;

/**
 * Created by tarun on 30-07-2016.
 */
public class Events_ViewPagerAdapter extends FragmentStatePagerAdapter{
int NumbOfTabs;
    CharSequence titles[];

    public Events_ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabs) {
        super(fm);
        titles=mTitles;
        NumbOfTabs=mNumbOfTabs;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
    @Override
    public Fragment getItem(int position) {
        System.out.println("---------------getCount()-----------------"+getCount());
        if(position == 0)
        {
            EventsDay0 ed0= new EventsDay0();
            return ed0;
        }
        else if(position == 1)
        {
            EventsDay1 ed1= new EventsDay1();
            return ed1;
        }
        else if(position == 2)
        {
            EventsDay2 ed2= new EventsDay2();
            return ed2;
        }
        else
        {
            EventsDay3 ed3= new EventsDay3();
            return ed3;
        }
    }

}
