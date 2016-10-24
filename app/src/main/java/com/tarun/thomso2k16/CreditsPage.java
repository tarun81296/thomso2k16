package com.tarun.thomso2k16;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class CreditsPage extends AppCompatActivity {

    private ImageView dev_fb;
    private ImageView design_web;
    private ImageView design_fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        dev_fb= (ImageView)findViewById(R.id.team_dev_fb);
        design_web=(ImageView)findViewById(R.id.team_design_web);
        design_fb = (ImageView)findViewById(R.id.team_design_fb);
        dev_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.facebook.com/tarun81296"));
                startActivity(browserIntent);
            }
        });
        design_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.facebook.com/designstudio.iitr/?fref=ts&__mref=message_bubble"));
                startActivity(browserIntent);
            }
        });
        design_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://designstudio.cc/"));
                startActivity(browserIntent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(CreditsPage.this, NavigationDrawerPage.class);
                startActivity(i);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(CreditsPage.this, NavigationDrawerPage.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}
