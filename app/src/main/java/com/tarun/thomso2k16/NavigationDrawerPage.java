package com.tarun.thomso2k16;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Choreographer;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.tarun.thomso2k16.adapter.NavDrawerAdapter;

public class NavigationDrawerPage extends ActionBarActivity {
    public static int mSelectedItem_text_pos;
    public static RelativeLayout mDrawer;
    private static DrawerLayout mDrawerLayout;
    private static ListView mDrawerList;
    public ArrayAdapter<String> drawerItems;
    int selectedfragment;
    ImageButton login;
    ImageButton logout;
    private String[] title;
    private ActionBarDrawerToggle mDrawerToggle;
    private int position;
    CheckInternetConnection cic;
    private Context context;
    private SessionManager session;
    private SliderLayout sliderShow;
    private FrameLayout mainFrame;

    public static void closeDrawers() {
        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mDrawer)) {
            mDrawerLayout.closeDrawer(mDrawer);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer_page);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.navList);
        sliderShow = (SliderLayout) findViewById(R.id.slider);
        login = (ImageButton) findViewById(R.id.login);
        logout = (ImageButton) findViewById(R.id.logout);
        mainFrame = (FrameLayout)findViewById(R.id.content_frame);
        context = getApplicationContext();
        cic= new CheckInternetConnection(context);
        session = new SessionManager(context);
        mDrawer = (RelativeLayout) findViewById(R.id.drawer);
        if (!session.isData() && !cic.isConnectingToInternet()){
            finish();
        }
        if (session.isLoggedIn()) {
            logout.setVisibility(View.VISIBLE);
            login.setVisibility(View.GONE);

        } else {
            login.setVisibility(View.VISIBLE);
            logout.setVisibility(View.GONE);
        }
        addDrawerItems();
        setupDrawer();
        addSlider();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


    }

    private void addSlider() {
        DefaultSliderView dsv = new DefaultSliderView(this);
        DefaultSliderView dsv1 = new DefaultSliderView(this);
        dsv.image(R.drawable.homescreen);
        dsv1.image(R.drawable.layer8);
        sliderShow.addSlider(dsv);
        sliderShow.addSlider(dsv1);
        sliderShow.startAutoCycle();
    }

    public void eventsClicked(View view) {
        Intent i = new Intent(NavigationDrawerPage.this, EventsPage.class);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void recentClicked(View view) {
        FragmentTransaction ft = getSupportFragmentManager()
                .beginTransaction();
        Fragment recentEvents = new OnGoingEvents();
        ft.replace(R.id.content_frame, recentEvents);
        selectedfragment=2;
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        ft.commit();
    }

    public void mapClicked(View view) {
        Intent map = new Intent(NavigationDrawerPage.this, MapActivity.class);
        startActivity(map);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void teamsClicked(View view) {
        Intent wvt = new Intent(NavigationDrawerPage.this, TeamActivity.class);
      //  wvt.putExtra("url", "http://thomso.in/about.html");
        startActivity(wvt);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void sponsorClicked(View view) {
        Intent wvs = new Intent(NavigationDrawerPage.this, WebView.class);
        wvs.putExtra("url", "http://thomso.in/sponsors.html");
        startActivity(wvs);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void loginClicked(View view) {
        if (session.isLoggedIn()) {
            session.logoutUser();
            finish();
            recreate();

        } else {
            Intent i1 = new Intent(context, SignIn.class);
            startActivity(i1);
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        }
    }

    private void addDrawerItems() {

        title = new String[]{
                "",
                "Events",
                "On Going Events",
                "Maps",
                "Team",
                "Sponsors",
                ""};
        if (session.isLoggedIn()) {
            title[6] = "Log Out";
        } else {
            title[6] = "Log In";
        }
        ;
        int[] icon = new int[]{R.drawable.ic_event_black_24dp,
                R.drawable.ic_event_black_24dp,
                R.drawable.ic_schedule_black_24dp,
                R.drawable.ic_place_black_24dp,
                R.drawable.ic_info_outline_black_24dp,
                R.drawable.ic_shopping_cart_black_24dp,
                R.drawable.ic_power_settings_new_black_24dp};
        NavDrawerAdapter nda = new NavDrawerAdapter(context, title, icon);
        mDrawerList.setAdapter(nda);
        //   drawerItems = new ArrayAdapter<String>(this, R.layout.drawer_list_item, title);
        //  mDrawerList.setAdapter(drawerItems);

        NavigationDrawerPage.mSelectedItem_text_pos = 1;

        //   mMenuAdapter = new HomeMenuListAdapter(NavigationDrawerPage.this,
        //          title, icon);
        //  mDrawerList.setAdapter(mMenuAdapter);
        //   mMenuAdapter.notifyDataSetChanged();

        mDrawerList
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {


                        //  isBackPress = false;
                        FragmentTransaction ft = getSupportFragmentManager()
                                .beginTransaction();
                        //  isInternetPresent = cd.isConnectingToInternet();

                        switch (position) {

                            case 0:
                                if(session.isLoggedIn()){
                                    Intent ud = new Intent(NavigationDrawerPage.this,UserDetails.class);
                                    startActivity(ud);
                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                    finish();
                                }
                              //     Intent ud = new Intent(NavigationDrawerPage.this,UserDetails.class);
                              //  startActivity(ud);
                              //  overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                break;
                            case 1:
                                Intent i = new Intent(NavigationDrawerPage.this, EventsPage.class);
                                startActivity(i);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                finish();
                                break;
                            case 2:
                                Fragment recentEvents = new OnGoingEvents();
                                ft.replace(R.id.content_frame, recentEvents);
                                selectedfragment=2;
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                break;
                            case 4:
                                Intent wvt = new Intent(NavigationDrawerPage.this, TeamActivity.class);
                                startActivity(wvt);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                finish();
                                break;

                            case 5:
                                Log.e("debug", "sponsors clicked");
                                Intent wvs = new Intent(NavigationDrawerPage.this, WebView.class);
                                wvs.putExtra("url", "http://thomso.in/sponsors.html");
                                startActivity(wvs);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                finish();
                                break;

                            case 3:
                                Intent map = new Intent(NavigationDrawerPage.this, MapActivity.class);
                                startActivity(map);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                finish();
                                break;
                            case 6:
                                if (session.isLoggedIn()) {
                                    session.logoutUser();
                                    recreate();
                                    finish();
                                    break;
                                } else {
                                    Intent i1 = new Intent(context, SignIn.class);
                                    startActivity(i1);
                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                    finish();
                                    break;
                                }

                        }


                        // FragmentTransaction ft = mActivity.getFragmentManager().beginTransaction();
                        // ft.detach(mFragment);

                        ft.commit();

                        mDrawerLayout.closeDrawer(mDrawer);

                    }
                });

    }

    public void setSelectedItem(int selectedItem_pos, TextView view) {
        mSelectedItem_text_pos = selectedItem_pos;
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(NavigationDrawerPage.this,
                mDrawerLayout, R.string.openDrawer, R.string.closeDrawer) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //  getSupportActionBar().setTitle("Navigation!");
                //  getSupportActionBar().setIcon(R.drawable.logo);
                // invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
                supportInvalidateOptionsMenu();
            }


            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //  getSupportActionBar().setTitle(mActivityTitle);
                //   getSupportActionBar().setIcon(R.drawable.logo);
                // invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
                supportInvalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerToggle.syncState();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_credits, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
            mDrawerLayout.closeDrawers();
            return;
        }

            //   if (isBackPress) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Exit");
            dialog.setMessage("Are you sure you want to exit?");
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialog.show();
        //// else {
        //    isBackPress = true;
        /// }
    }

    @Override
    protected void onStop() {
        sliderShow.stopAutoCycle();
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (!mDrawerLayout.isDrawerOpen(Gravity.START)) {
                    mDrawerLayout.openDrawer(GravityCompat.START);  // OPEN DRAWER
                } else {
                    mDrawerLayout.closeDrawers();
                }

                return true;
            case R.id.credits:
                Intent i = new Intent(NavigationDrawerPage.this,CreditsPage.class);
                startActivity(i);
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

}

