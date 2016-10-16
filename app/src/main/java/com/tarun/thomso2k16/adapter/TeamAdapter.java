package com.tarun.thomso2k16.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tarun.thomso2k16.R;

/**
 * Created by tarun on 16-10-2016.
 */

public class TeamAdapter extends BaseAdapter {
    private String[] post;
    private String[] number;
    private String[] name;
    private Context context;
    private LayoutInflater inflater;
    private View teamView;

    public TeamAdapter(Context context, String[] name, String[] number, String[] post) {
        this.context = context;
        this.name = name;
        this.number = number;
        this.post = post;
    }
    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int position) {
        return number[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView teamName;
        TextView teamNumber;
        TextView teamPost;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        teamView = inflater.inflate(R.layout.team_single_item, parent, false);
        teamName=(TextView)teamView.findViewById(R.id.contact_name);
        teamNumber=(TextView)teamView.findViewById(R.id.contact_number);
        teamPost= (TextView)teamView.findViewById(R.id.contact_post);
        teamName.setText(name[position]);
        teamNumber.setText(number[position]);
        teamPost.setText(post[position]);
        return teamView;
    }
}
