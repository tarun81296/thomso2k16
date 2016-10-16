package com.tarun.thomso2k16.adapter;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tarun.thomso2k16.CheckInternetConnection;
import com.tarun.thomso2k16.DBhelper;
import com.tarun.thomso2k16.Events_pojo;
import com.tarun.thomso2k16.R;

public class SingleEventPage extends AppCompatActivity {

    String eName, eDescription, eVenue, eTime, eDay, eDate, eImage, coordinatorName1, CoordinatorNo1;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Context context;
    private DBhelper dbh;
    private Bundle b;
    private Events_pojo eventDetails;
    private ImageView eventImage;
    private TextView eventDescription;
    private TextView eventTitle;
    private FloatingActionButton contact_fab;
    private String coordinatorName2;
    private String CoordinatorNo2;
    public CheckInternetConnection cd;
    public Drawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_event_page);
        context = getApplicationContext();
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbarTextAppearance();
        dynamicToolbarColoring();
        initialize();
        contact_fab.setImageResource(R.drawable.ic_phone_white_24dp);
        // contact_fab.setBackgroundColor(Color.parseColor("#ffa203"));
        collapsingToolbarLayout.setTitle(eName);
        contact_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(SingleEventPage.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.call_radiobutton);
                RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.radio_group);
                //   Button left = (Button) dialog.findViewById(R.id.call_button);

                RadioButton rb = new RadioButton(SingleEventPage.this); // dynamically creating RadioButton and adding to RadioGroup.
                RadioButton rb1 = new RadioButton(SingleEventPage.this);
                rb.setText(coordinatorName1 + " - " + CoordinatorNo1);
                rb1.setText(coordinatorName2 + " - " + CoordinatorNo2);
                rg.addView(rb);
                rg.addView(rb1);
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        int childCount = group.getChildCount();
                        for (int x = 0; x < childCount; x++) {
                            Log.e("value x:", " " + x);
                            RadioButton btn = (RadioButton) group.getChildAt(x);
                            Log.e("btn.getId()", " " + btn.getId());
                            Log.e("checkedId", " " + checkedId);
                            if (btn.getId() == checkedId && btn.getId() % 2 != 0) {
                                Log.e("selected RadioButton->", btn.getId() + "  " + btn.getText().toString());
                                String number = "tel:" + CoordinatorNo1;
                                Intent call = new Intent(Intent.ACTION_CALL, Uri.parse(number));
                                if (ActivityCompat.checkSelfPermission(SingleEventPage.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
                                Log.e("value x:", " " + x);
                            } else if (btn.getId() == checkedId && btn.getId() % 2 == 0) {
                                Log.e("selected RadioButton->", btn.getId() + "  " + btn.getText().toString());
                                String number = "tel:" + CoordinatorNo2;
                                Intent call = new Intent(Intent.ACTION_CALL, Uri.parse(number));
                                if (ActivityCompat.checkSelfPermission(SingleEventPage.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
                                Log.e("value x:", " " + x);
                            }
                        }
                    }
                });
                dialog.show();
            }
        });
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
        dbh = new DBhelper(context, null, null, 1);
        cd= new CheckInternetConnection(context);
        eventImage = (ImageView) findViewById(R.id.event_image);
        eventDescription = (TextView) findViewById(R.id.event_description);
        eventTitle = (TextView) findViewById(R.id.event_title);
        contact_fab = (FloatingActionButton) findViewById(R.id.fab_contact);
        b = new Bundle();
        b = getIntent().getExtras();
        eName = b.getString("EventName");
        eventDetails = dbh.getEventDetails(eName);
        eDescription = eventDetails.getEventDescription();
        eImage = eventDetails.getEventImage();
        eVenue = eventDetails.getEventVenue();
        eTime = eventDetails.getEventTime();
        eDay = eventDetails.getEventDay();
        eDate = eventDetails.getEventDate();
        coordinatorName1 = eventDetails.getCoordinatorName1();
        coordinatorName2 = eventDetails.getCoordinatorName2();
        CoordinatorNo1 = eventDetails.getCoordinatorNo1();
        CoordinatorNo2 = eventDetails.getCoordinatorNo2();
        Log.e("Desc ", eDescription);
        Log.e("eImage ", eImage);
        Log.e("eTime ", eTime);
        String eImage1=eImage.split("/")[4];
        Log.e("eImage1",eImage1.replace(".",",").split(",")[0]);
        drawable = getResources().getDrawable(getResources().getIdentifier(eImage1.replace(".",",").split(",")[0],"drawable",getPackageName()));
        Log.e("drawable",drawable.toString());
        setData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void setData() {
        if(cd.isConnectingToInternet()) {
            Glide.
                    with(SingleEventPage.this).
                    load(eImage)
                    .into(eventImage);
        }
        else{
            eventImage.setImageDrawable(drawable);
        }
        eventTitle.setText(eName);
        eventDescription.setText(eDescription);
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
