package com.tarun.thomso2k16;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tarun.thomso2k16.adapter.SingleEventPage;
import com.tarun.thomso2k16.adapter.TeamAdapter;

import java.util.List;

public class TeamActivity extends AppCompatActivity {

    public ListView teamList;
    private Context context;
    private DBhelper dbh;
    private TeamAdapter teamAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        teamList=(ListView)findViewById(R.id.team_list);
        context= getApplicationContext();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        dbh=new DBhelper(context,null,null,1);
        int n= dbh.getTeamCount();
        List<TeamPojo> team=dbh.getTeam();
        String[] name = new String[n];
        String[] number = new String[n];
        String[] post = new String[n];
        int i = 0;
        Log.e("debug", "n= " + n);
        for (TeamPojo ep : team) {
            name[i] = ep.getName();
            number[i] = ep.getNumber();
            post[i] = ep.getPost();
            Log.e("debug", "Name:- " + ep.getName());
            i++;
        }
        teamAdapter = new TeamAdapter(context,name,number,post);
        teamList.setAdapter(teamAdapter);
        teamList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String number = "tel:" + teamAdapter.getItem(position);
                Intent call = new Intent(Intent.ACTION_CALL, Uri.parse(number));
                if (ActivityCompat.checkSelfPermission(TeamActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Log.e("phone prmissions", "not given");
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(call);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(TeamActivity.this, NavigationDrawerPage.class);
                startActivity(i);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(TeamActivity.this, NavigationDrawerPage.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}
