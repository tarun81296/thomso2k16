package com.example.tarun.thomso2k16.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tarun.thomso2k16.R;

/**
 * Created by tarun on 01-10-2016.
 */
public class EventsListAdapter extends BaseAdapter {
    Context context;
    String[] title;
    String[] venue;
    String[] time;
    View eventView;
    LayoutInflater inflater;


    public EventsListAdapter(Context context, String[] title, String[] venue, String[] time) {
        this.context = context;
        this.title = title;
        this.venue = venue;
        this.time = time;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public Object getItem(int position) {
        return title[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv_eventName;
        TextView tv_eventTime;
        TextView tv_eventVenue;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        eventView = inflater.inflate(R.layout.event_list_item, parent, false);
        tv_eventName = (TextView) eventView.findViewById(R.id.event_name);
        tv_eventVenue = (TextView) eventView.findViewById(R.id.event_venue);
        tv_eventTime = (TextView) eventView.findViewById(R.id.event_time);
        tv_eventName.setText(title[position]);
        tv_eventVenue.setText(venue[position]);
        tv_eventTime.setText(time[position]);
        return eventView;
    }
}
