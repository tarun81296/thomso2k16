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

public class OnGoingEvents extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public ListView eventslist;
    public EventsListAdapter eventsAdapter;
    private Context context;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DBhelper dbh;


    public OnGoingEvents() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static OnGoingEvents newInstance(String param1, String param2) {
        OnGoingEvents fragment = new OnGoingEvents();
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
        context = this.getActivity();
        View rootView = inflater.inflate(R.layout.fragment_on_going_events, container, false);
        View empty = inflater.inflate(R.layout.activity_empty_on_going_events, container, false);
        eventslist = (ListView) rootView.findViewById(R.id.on_going_evens_list);
        dbh = new DBhelper(context, null, null, 1);
        List<Events_pojo> event = dbh.getOnGoingEvents();
        int n = dbh.p;

        if (n == 0) {
            // noRecentEvents();
            Intent i = new Intent(getActivity(), EmptyOnGoingEvents.class);
            startActivity(i);
            getActivity().finish();
        }

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
        // Inflate the layout for this fragment
        return rootView;
    }

    private void noRecentEvents() {

    }


}







