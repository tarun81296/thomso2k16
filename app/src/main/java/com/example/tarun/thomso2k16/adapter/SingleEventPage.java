package com.example.tarun.thomso2k16.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tarun.thomso2k16.DBhelper;
import com.example.tarun.thomso2k16.Events_pojo;
import com.example.tarun.thomso2k16.R;

public class SingleEventPage extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Context context;
    private DBhelper dbh;
    private Bundle b;
    String eName,eDescription,eVenue,eTime,eDay,eDate,eImage,coordinatorName,CoordinatorNo;
    private Events_pojo eventDetails;
    private ImageView eventImage;
    private TextView eventDescription;
    private TextView eventTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_event_page);
        context = getApplicationContext();
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbarTextAppearance();
        dynamicToolbarColoring();
        initialize();

        collapsingToolbarLayout.setTitle("Test");
    }

    private void toolbarTextAppearance() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }

    private void initialize() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        dbh = new DBhelper(context,null,null,1);
        eventImage=(ImageView)findViewById(R.id.event_image);
        eventDescription=(TextView)findViewById(R.id.event_description);
        eventTitle=(TextView)findViewById(R.id.event_title);
        b= new Bundle();
        b= getIntent().getExtras();
        eName=b.getString("EventName");
        eventDetails= dbh.getEventDetails(eName);
        eDescription= eventDetails.getEventDescription();
        eImage=eventDetails.getEventImage();
        eVenue=eventDetails.getEventVenue();
        eTime=eventDetails.getEventTime();
        eDay=eventDetails.getEventDay();
        eDate=eventDetails.getEventDate();
        coordinatorName=eventDetails.getCoordinatorName();
        CoordinatorNo=eventDetails.getCoordinatorNo();
        Log.e("Desc ",eDescription);
        Log.e("eImage ",eImage);
        Log.e("eTime ",eTime);

        setData();
    }

    private void setData() {
        Glide.
                with(SingleEventPage.this).
                load(eImage)
                //    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(eventImage);
    }

    private void dynamicToolbarColoring() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.landscape);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {

            @Override
            public void onGenerated(Palette palette) {
                collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(R.attr.colorPrimary));
                collapsingToolbarLayout.setStatusBarScrimColor(palette.getMutedColor(R.attr.colorPrimaryDark));
            }
        });
    }
}
