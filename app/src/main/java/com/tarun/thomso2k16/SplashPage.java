package com.tarun.thomso2k16;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

import java.util.HashMap;
import java.util.Random;

public class SplashPage extends AppCompatActivity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;
    public JsonObjectRequest jsonObjReq;
    public Context context;
    public CheckInternetConnection cic;
    public Boolean isInternetPresent = false;
    public DBhelper dbh;
    // Session Manager Class
    SessionManager session;
    CommonIdSessionManager commonsession;
    String commonid;
    ImageView invisible;
    RelativeLayout sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String android_id = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        context = getApplicationContext();
        cic = new CheckInternetConnection(context);
        session = new SessionManager(context);
        sp = (RelativeLayout) findViewById(R.id.splash_page);
        commonsession = new CommonIdSessionManager(context);
        invisible = (ImageView) findViewById(R.id.imageView);
        dbh = new DBhelper(context, null, null, 1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if (cic.isConnectingToInternet()) {
                    context.deleteDatabase("THOMSO.db");
                    JsonRequest("http://thomso.in/app/Events-new.php");
                    JsonRequest1("http://thomso.in/app/team.php");
                }
                if (!session.isData() && !cic.isConnectingToInternet()) {
                     Log.e("no data","no data");
                    Toast.makeText(context, "You need to connect to internet for first time", Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("run","in run");
                            finish();
                        }
                    },1500);
                }
                if (session.isLoggedIn()) {
//					Random r = new Random(System.currentTimeMillis());
//					commonsession.createcommonidSession(android_id);

                   /* Intent i = new Intent(SplashPage.this, NavigationDrawerPage.class);
                    startActivity(i);
                    finish();*/
                    //  overridePendingTransition(R.anim.enter,R.anim.exit);
                } else {
                    HashMap<String, String> user1 = commonsession.getUserDetails();
                    //CommonId = user1.get(CommonIDSessionManager.KEY_COMMONID);
                    if (!user1.containsKey(CommonIdSessionManager.KEY_COMMONID) || user1.get(CommonIdSessionManager.KEY_COMMONID) == null || user1.get(CommonIdSessionManager.KEY_COMMONID).equalsIgnoreCase("commonid")) {
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
        isInternetPresent = cic.isConnectingToInternet();
        if (isInternetPresent) {
            // call json and update local db
        }
    }

    private void JsonRequest(String Url) {
        System.out.println("------------Events------------------" + Url);
        jsonObjReq = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("------------Events Response--------" + response);
                try {
                    JSONArray jarray = response.getJSONArray("Day0");
                    if (jarray.length() > 0) {
                        for (int i = 0; i < jarray.length(); i++) {
                            JSONObject object = jarray.getJSONObject(i);
                            Events_pojo event = new Events_pojo();
                            event.setEventName(object.getString("Name"));
                            event.setEventDescription(object.getString("Description"));
                            event.setEventDate(object.getString("Date"));
                            event.setEventTime(object.getString("Time"));
                            event.setEventVenue(object.getString("Venue"));
                            event.setEventDay(object.getString("Day"));
                            event.setEventImage("http://thomso.in/" + object.getString("Image"));
                            Log.e("EventImage", "http://thomso.in/" + object.getString("Image"));
                            event.setCoordinatorName1(object.getString("Coordinator Name1"));
                            event.setCoordinatorName2(object.getString("Coordinator Name2"));
                           /* Glide.
                                    with(SplashPage.this).
                                    load("http://www.thomso.in/register/"+object.getString("Image"))
                                    //    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                                    .into(invisible);*/
                            event.setCoordinatorNo1(object.getString("Coordinator Number1"));
                            event.setCoordinatorNo2(object.getString("Coordinator Number2"));
                            dbh.getInput(event);

                        }
                    }
                    JSONArray jarray1 = response.getJSONArray("Day1");
                    if (jarray.length() > 0) {
                        for (int i = 0; i < jarray1.length(); i++) {
                            JSONObject object1 = jarray1.getJSONObject(i);
                            Events_pojo event1 = new Events_pojo();
                            event1.setEventName(object1.getString("Name"));
                            event1.setEventDescription(object1.getString("Description"));
                            event1.setEventDate(object1.getString("Date"));
                            event1.setEventTime(object1.getString("Time"));
                            event1.setEventVenue(object1.getString("Venue"));
                            event1.setEventDay(object1.getString("Day"));
                            event1.setEventImage("http://thomso.in/" + object1.getString("Image"));
                            Log.e("EventImage", object1.getString("Image"));
                            event1.setCoordinatorName1(object1.getString("Coordinator Name1"));
                            event1.setCoordinatorName2(object1.getString("Coordinator Name2"));
                            /*Glide.
                                    with(SplashPage.this).
                                    load("http://www.thomso.in/register/"+object1.getString("Image"))
                                    //    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                                    .into(invisible);*/
                            event1.setCoordinatorNo1(object1.getString("Coordinator Number1"));
                            event1.setCoordinatorNo2(object1.getString("Coordinator Number2"));
                            dbh.getInput(event1);

                        }
                    }
                    JSONArray jarray2 = response.getJSONArray("Day2");
                    if (jarray.length() > 0) {
                        for (int i = 0; i < jarray2.length(); i++) {
                            JSONObject object1 = jarray2.getJSONObject(i);
                            Events_pojo event1 = new Events_pojo();
                            event1.setEventName(object1.getString("Name"));
                            event1.setEventDescription(object1.getString("Description"));
                            event1.setEventDate(object1.getString("Date"));
                            event1.setEventTime(object1.getString("Time"));
                            event1.setEventVenue(object1.getString("Venue"));
                            event1.setEventDay(object1.getString("Day"));
                            event1.setEventImage("http://thomso.in/" + object1.getString("Image"));
                            Log.e("EventImage", "http://thomso.in/" + object1.getString("Image"));
                            event1.setCoordinatorName1(object1.getString("Coordinator Name1"));
                            event1.setCoordinatorName2(object1.getString("Coordinator Name2"));
                            /*Glide.
                                    with(SplashPage.this).
                                    load("http://www.thomso.in/register/"+object1.getString("Image"))
                                    //    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                                    .into(invisible);*/
                            event1.setCoordinatorNo1(object1.getString("Coordinator Number1"));
                            event1.setCoordinatorNo2(object1.getString("Coordinator Number2"));
                            dbh.getInput(event1);
                        }
                    }
                    JSONArray jarray3 = response.getJSONArray("Day3");
                    if (jarray.length() > 0) {
                        for (int i = 0; i < jarray3.length(); i++) {
                            JSONObject object1 = jarray3.getJSONObject(i);
                            Events_pojo event1 = new Events_pojo();
                            event1.setEventName(object1.getString("Name"));
                            event1.setEventDescription(object1.getString("Description"));
                            event1.setEventDate(object1.getString("Date"));
                            event1.setEventTime(object1.getString("Time"));
                            event1.setEventVenue(object1.getString("Venue"));
                            event1.setEventDay(object1.getString("Day"));
                            event1.setEventImage("http://thomso.in/" + object1.getString("Image"));
                            Log.e("EventImage", "http://thomso.in/" + object1.getString("Image"));
                            event1.setCoordinatorName1(object1.getString("Coordinator Name1"));
                            event1.setCoordinatorName2(object1.getString("Coordinator Name2"));
                            /*Glide.
                                    with(SplashPage.this).
                                    load("http://www.thomso.in/register/"+object1.getString("Image"))
                                    //    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                                    .into(invisible);*/
                            event1.setCoordinatorNo1(object1.getString("Coordinator Number1"));
                            event1.setCoordinatorNo2(object1.getString("Coordinator Number2"));
                            dbh.getInput(event1);
                        }
                    }
                    session.dataEntered();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //------on post execute-----
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(context, "Unable to fetch data from server", Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    //TODO
                    Toast.makeText(context, "AuthFailureError", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    //TODO
                    Toast.makeText(context, "ServerError", Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {

                    Toast.makeText(context, "NetworkError", Toast.LENGTH_LONG).show();
                    //TODO
                } else if (error instanceof ParseError) {
                    //TODO
                    Toast.makeText(context, "ParseError", Toast.LENGTH_LONG).show();
                }
            }
        });
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void JsonRequest1(String Url) {
        System.out.println("------------Team------------------" + Url);
        jsonObjReq = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println("------------Team Response--------" + response);
                try {

                    JSONArray jarray = response.getJSONArray("team");
                    if (jarray.length() > 0) {
                        for (int i = 0; i < jarray.length(); i++) {
                            JSONObject object = jarray.getJSONObject(i);
                            TeamPojo tp = new TeamPojo();
                            tp.setName(object.getString("name"));
                            tp.setNumber(object.getString("number"));
                            tp.setPost(object.getString("post"));
                            dbh.getTeamInput(tp);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(context, "Unable to fetch data from server", Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    //TODO
                    Toast.makeText(context, "AuthFailureError", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    //TODO
                    Toast.makeText(context, "ServerError", Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {

                    Toast.makeText(context, "NetworkError", Toast.LENGTH_LONG).show();
                    //TODO
                } else if (error instanceof ParseError) {
                    //TODO
                    Toast.makeText(context, "ParseError", Toast.LENGTH_LONG).show();
                }
            }
        });
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }
}
