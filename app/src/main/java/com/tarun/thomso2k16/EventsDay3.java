package com.tarun.thomso2k16;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tarun.thomso2k16.adapter.EventsListAdapter;
import com.tarun.thomso2k16.adapter.SingleEventPage;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link EventsDay3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventsDay3 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String COLUMN_EventDay = "EventDay";
    private static final String TABLE_EVENTS = "EVENT_DETAILS";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context context;
    private ListView eventslist;
    private DBhelper dbh;
    private EventsListAdapter eventsAdapter;


    public EventsDay3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventsDay3.
     */
    // TODO: Rename and change types and number of parameters
    public static EventsDay3 newInstance(String param1, String param2) {
        EventsDay3 fragment = new EventsDay3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.events_day3, container, false);
        context = this.getActivity();
        eventslist = (ListView) rootView.findViewById(R.id.evens_list_day3);
        dbh = new DBhelper(context, null, null, 1);
        List<Events_pojo> event = dbh.showEvents("SELECT * FROM " + TABLE_EVENTS + " WHERE " + COLUMN_EventDay + " ='3' " + " ;");
        int n = dbh.getEventsCount("SELECT * FROM " + TABLE_EVENTS + " WHERE " + COLUMN_EventDay + " ='3' " + " ;");
        String[] tar = new String[n];
        String[] venue = new String[n];
        String[] time = new String[n];
        Log.e("debug", "debug 1");
        int i = 0;
        Log.e("debug", "n= " + n);
        for (Events_pojo ep : event) {
            tar[i] = ep.getEventName();
            venue[i] = ep.getEventVenue();
            time[i] = ep.getEventTime();
            Log.e("debug", "Name:- " + ep.getEventName());
            i++;
        }
        eventsAdapter = new EventsListAdapter(context, tar, venue, time);
        eventslist.setAdapter(eventsAdapter);
        eventslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), SingleEventPage.class);
                i.putExtra("EventName", eventsAdapter.getItem(position).toString());
                Log.e("debug", eventsAdapter.getItem(position).toString());
                startActivity(i);
            }
        });
        return rootView;
    }
}
