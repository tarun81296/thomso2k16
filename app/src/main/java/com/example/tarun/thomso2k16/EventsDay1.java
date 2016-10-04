package com.example.tarun.thomso2k16;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tarun.thomso2k16.adapter.EventsListAdapter;
import com.example.tarun.thomso2k16.adapter.SingleEventPage;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link EventsDay1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventsDay1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String COLUMN_EventDay="EventDay";
    private static final String TABLE_EVENTS = "EVENT_DETAILS";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context context;
    private ListView eventslist;
    private DBhelper dbh;
    private EventsListAdapter eventsAdapter;


    public EventsDay1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventsDay1.
     */
    // TODO: Rename and change types and number of parameters
    public static EventsDay1 newInstance(String param1, String param2) {
        EventsDay1 fragment = new EventsDay1();
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
        View rootView =inflater.inflate(R.layout.events_day1, container, false);
        context = this.getActivity();
        eventslist= (ListView)rootView.findViewById(R.id.evens_list_day1);
        dbh= new DBhelper(context,null,null,1);
        List<Events_pojo> event = dbh.showEvents("SELECT * FROM " + TABLE_EVENTS + " WHERE "+COLUMN_EventDay+" ='1' "+" ;");
        int n = dbh.getEventsCount("SELECT * FROM " + TABLE_EVENTS + " WHERE "+COLUMN_EventDay+" ='1' "+" ;");
        String[] tar = new String[n];
        Log.e("debug","debug 1");
        int i = 0 ;
        Log.e("debug","n= "+n);
        for( Events_pojo ep: event){
            tar[i]=ep.getEventName();
            Log.e("debug","Name:- "+ep.getEventName());
            i++;
        }
        eventsAdapter= new EventsListAdapter(context,tar);
        eventslist.setAdapter(eventsAdapter);
        eventslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(),SingleEventPage.class);
                i.putExtra("EventName",eventsAdapter.getItem(position).toString());
                Log.e("debug",eventsAdapter.getItem(position).toString());
                startActivity(i);
            }
        });
        return rootView;
    }



}
