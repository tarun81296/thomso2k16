package com.example.tarun.thomso2k16;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SplashPage extends AppCompatActivity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;
    public JsonObjectRequest jsonObjReq;
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

              //  Events_pojo event = new Events_pojo();
               // event.setEventName("Name1");
               // event.setEventDescription("Description1");
               // event.setEventDate("Date1");
               // event.setEventTime("time1");
               // event.setEventVenue("venue1");
              // dbh.getInput(event);
                if(cic.isConnectingToInternet()){
                    context.deleteDatabase("THOMSO.db");
                    JsonRequest("http://dev-test.co.nf/events.php");
                }
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
    private void JsonRequest(String Url)
    {


        System.out.println("------------Events------------------"+Url);

        jsonObjReq = new JsonObjectRequest(Request.Method.GET,Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {

                System.out.println("------------Events Response--------"+response);
                try {

                    JSONArray jarray = response.getJSONArray("Day0");
                    if(jarray.length()>0)
                    {
                        for (int i = 0; i < jarray.length(); i++)
                        {
                            JSONObject object = jarray.getJSONObject(i);


                            Events_pojo event = new Events_pojo();
                            event.setEventName(object.getString("Name"));
                            event.setEventDescription(object.getString("Description"));
                            event.setEventDate(object.getString("Date"));
                            event.setEventTime(object.getString("Time"));
                            event.setEventVenue(object.getString("Venue"));
                            event.setEventDay(object.getString("Day"));
                            dbh.getInput(event);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }



                //------on post execute-----


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {


                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(context,"Unable to fetch data from server",Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    //TODO
                    Toast.makeText(context,"AuthFailureError",Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    //TODO
                    Toast.makeText(context,"ServerError",Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {

                    Toast.makeText(context,"NetworkError",Toast.LENGTH_LONG).show();
                    //TODO
                } else if (error instanceof ParseError) {
                    //TODO
                    Toast.makeText(context,"ParseError",Toast.LENGTH_LONG).show();
                }
            }
        });

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }
}
