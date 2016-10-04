package com.example.tarun.thomso2k16;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tarun.thomso2k16.adapter.EventsListAdapter;
import com.example.tarun.thomso2k16.adapter.SingleEventPage;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link EventsDay0#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventsDay0 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String COLUMN_EventDay="EventDay";
    private static final String TABLE_EVENTS = "EVENT_DETAILS";
    public DBhelper dbh;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Boolean isInternetPresent;
    private Context context;
    public ListView eventslist;
    public EventsListAdapter eventsAdapter;
    public EventsDay0() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static EventsDay0 newInstance(String param1, String param2) {
        EventsDay0 fragment = new EventsDay0();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        context= this.getActivity();
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = this.getActivity();
        View rootView =inflater.inflate(R.layout.events_day0_fragment, container, false);
        CheckInternetConnection cic= new CheckInternetConnection(context);
        eventslist= (ListView)rootView.findViewById(R.id.evens_list);
        dbh= new DBhelper(context,null,null,1);
        List<Events_pojo> event = dbh.showEvents("SELECT * FROM " + TABLE_EVENTS + " WHERE "+COLUMN_EventDay+" ='0' "+" ;");
        int n = dbh.getEventsCount("SELECT * FROM " + TABLE_EVENTS + " WHERE "+COLUMN_EventDay+" ='0' "+" ;");
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
