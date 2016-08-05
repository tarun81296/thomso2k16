package com.example.tarun.thomso2k16;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.HashMap;
import java.util.Random;
import android.os.Handler;

public class SplashPage extends AppCompatActivity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;
      public Context context;
    // Session Manager Class
    SessionManager session;
    CommonIdSessionManager commonsession;
    public CheckInternetConnection cic;
    public Boolean isInternetPresent=false;
    String commonid;
    public DBhelper dbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String android_id = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        context=getApplicationContext();
        cic = new CheckInternetConnection(context);
        session = new SessionManager(context);
        commonsession= new CommonIdSessionManager(context);
        dbh= new DBhelper(context,null,null,1);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
               // session.createUserlogedActivity("");
                //CLEAR IMAGE CACHE FROM PHONE SD CARD
               // FileCache aa=new FileCache(SplashPage.this);
                //aa.clear();
                context.deleteDatabase("THOMSO.db");
                Events_pojo event = new Events_pojo();
                event.setEventName("Name1");
                event.setEventDescription("Description1");
                event.setEventDate("Date1");
                event.setEventTime("time1");
                event.setEventVenue("venue1");
                dbh.getInput(event);
                if (session.isLoggedIn())
                {
//					Random r = new Random(System.currentTimeMillis());
//					commonsession.createcommonidSession(android_id);

                    Intent i = new Intent(SplashPage.this, NavigationDrawerPage.class);
                    startActivity(i);
                    finish();
                  //  overridePendingTransition(R.anim.enter,R.anim.exit);
                }
                else
                {
                    HashMap<String, String> user1 = commonsession.getUserDetails();
                    //CommonId = user1.get(CommonIDSessionManager.KEY_COMMONID);
                    if(!user1.containsKey(CommonIdSessionManager.KEY_COMMONID) || user1.get(CommonIdSessionManager.KEY_COMMONID) ==null||user1.get(CommonIdSessionManager.KEY_COMMONID).equalsIgnoreCase("commonid")){
                        Random r = new Random(System.currentTimeMillis());
                        commonsession.createcommonidSession(android_id);
                    }


                    Intent i = new Intent(SplashPage.this, NavigationDrawerPage.class);
                    startActivity(i);
                    finish();
                   // overridePendingTransition(R.anim.enter,R.anim.exit);
                }
            }
        }, SPLASH_TIME_OUT);
        isInternetPresent=cic.isConnectingToInternet();
        if(isInternetPresent){
            // call json and update local db
        }
    }
}
