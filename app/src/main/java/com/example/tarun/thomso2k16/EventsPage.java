package com.example.tarun.thomso2k16;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.example.tarun.thomso2k16.adapter.Events_ViewPagerAdapter;

public class EventsPage extends AppCompatActivity {
 public ViewPager event_daywise_tabs;
    public Context context;
    public Events_ViewPagerAdapter days_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        String titles[]={"Day 0","Day 1","Day 2", "Day 3"};
        int Numoftabs=4;
        days_adapter=new Events_ViewPagerAdapter(getSupportFragmentManager(),titles,Numoftabs);
        // Initialize the ViewPager and set an adapter
        event_daywise_tabs= (ViewPager) findViewById(R.id.event_daywise_tabs);

        event_daywise_tabs.setAdapter(days_adapter);

        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.events_days_tabs);
        tabs.setTextColor(Color.WHITE );
        tabs.setIndicatorColor(Color.WHITE);

        tabs.setViewPager(event_daywise_tabs);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(EventsPage.this,NavigationDrawerPage.class);
                startActivity(i);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onBackPressed(){
        Intent i = new Intent(EventsPage.this,NavigationDrawerPage.class);
        startActivity(i);
        finish();
    }
}
