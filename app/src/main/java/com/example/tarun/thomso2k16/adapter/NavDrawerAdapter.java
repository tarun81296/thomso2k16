package com.example.tarun.thomso2k16.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tarun.thomso2k16.NavigationDrawerPage;
import com.example.tarun.thomso2k16.R;
import com.example.tarun.thomso2k16.SessionManager;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by tarun on 02-09-2016.
 */
public class NavDrawerAdapter extends BaseAdapter {
    Context context;
    String[] title;
    int[] icon;
    View itemView;
    LayoutInflater inflater;
    SessionManager sm;
    public NavDrawerAdapter(Context context, String[] title,int[] icon ){
        this.context=context;
        this.title=title;
        this.icon=icon;
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
        ImageView title_icon;
        TextView nav_title;
        CircleImageView profile_pic;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        itemView = inflater.inflate(R.layout.drawer_list_items_ , parent, false);
        RelativeLayout hv= (RelativeLayout)itemView.findViewById(R.id.header_view);
        title_icon = (ImageView) itemView.findViewById(R.id.icon);
        nav_title = (TextView)itemView.findViewById(R.id.titles);
        profile_pic = (CircleImageView)itemView.findViewById(R.id.profile_image);
        sm = new SessionManager(context);
        nav_title.setTextColor(Color.parseColor("#FFFFFF"));
      if(position==0) {
          hv.setVisibility(View.VISIBLE);
          if(sm.isLoggedIn()){
              Glide.with(context).load(sm.getImage()).into(profile_pic);
          }
      }
       else if(position>=0) {
            nav_title.setText(title[position]);
            title_icon.setImageResource(icon[position]);
          Log.e("debug",position+": "+title[position]);
        }
        return itemView;
    }
}
