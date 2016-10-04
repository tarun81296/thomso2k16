package com.example.tarun.thomso2k16;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tarun.thomso2k16.adapter.NavDrawerAdapter;
import com.example.tarun.thomso2k16.adapter.SingleEventPage;

import java.util.HashMap;

public class NavigationDrawerPage extends AppCompatActivity {
    private static DrawerLayout mDrawerLayout;
    private static ListView mDrawerList;
    private String[] title;
    public static int mSelectedItem_text_pos;
    private ActionBarDrawerToggle mDrawerToggle;
    int selectedfragment;
    public static RelativeLayout mDrawer;
    private int position;
    private Context context;
    private SessionManager session;
   public ArrayAdapter<String> drawerItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer_page);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerList=(ListView)findViewById(R.id.navList);
        context = getApplicationContext();
        session = new SessionManager(context);
        mDrawer= (RelativeLayout)findViewById(R.id.drawer);
        addDrawerItems();
        setupDrawer();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }
    private void addDrawerItems() {

            title = new String[]{
                    "",
                    "Events",
                    "Recent Events",
                    "Maps",
                    "Team",
                    "Sponsors",
            ""};
                    if (session.isLoggedIn()){
                   title[6]= "Log Out";
                    }
                     else
                    {
                        title[6]="Log In";
                    };
           /* icon = new int[]{
                    R.drawable.userprofile,
                    R.drawable.home_icon,
                    R.drawable.categoryicon,
                    R.drawable.chaticon,
                    R.drawable.purchaseicon,
                    R.drawable.allshopicon,
                    R.drawable.openshop_icon,
                    R.drawable.ic_printing_magazine_icon,
                    R.drawable.ic_group_black_24dp,
                    R.drawable.settingsicon,
                    R.drawable.help_icon,
                    R.drawable.info_icon,
                    R.drawable.signout_icon};*/

int [] icon =new int[]{R.drawable.ic_event_black_24dp,
                       R.drawable.ic_event_black_24dp,
                       R.drawable.ic_schedule_black_24dp,
                       R.drawable.ic_place_black_24dp,
                       R.drawable.ic_info_outline_black_24dp,
                       R.drawable.ic_shopping_cart_black_24dp,
                       R.drawable.ic_power_settings_new_black_24dp};
        NavDrawerAdapter nda= new NavDrawerAdapter(context,title,icon);
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

                        selectedfragment = position;
                      //  isBackPress = false;
                        FragmentTransaction ft = getSupportFragmentManager()
                                .beginTransaction();
                      //  isInternetPresent = cd.isConnectingToInternet();

                            switch (position) {

                                case 0:

                                    break;
                                case 1:
                                 Intent i = new Intent(NavigationDrawerPage.this,EventsPage.class);
                                    startActivity(i);
                                    break;
                                case 2:
                                //       Fragment Day0 = new EventsDay0();
                                  //  ft.replace(R.id.content_frame,Day0);
                                    //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                    break;
                                case 3:
                                  //  Fragment Day1 = new EventsDay1();
                                 //   ft.replace(R.id.content_frame,Day1);
                                //    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                    break;

                                case 4:
                                 //   Fragment Day2 = new EventsDay2();
                                //    ft.replace(R.id.content_frame,Day2);
                                //    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                    break;

                                case 5:
                                 //   Fragment Day3 = new EventsDay3();
                                //    ft.replace(R.id.content_frame,Day3);
                                //    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                    break;
                                case 6:
                                 //         Intent i2 = new Intent(NavigationDrawerPage.this, SingleEventPage.class);
                                //    startActivity(i2);
                                    break;
                                case 7:

                                    break;
                                case 8:

                                    break;
                                case 9:
                                    break;
                                case 10:
                                      if (session.isLoggedIn()) {
                                          break;
                                      }
                                    else{
                                       Intent i1 = new Intent (context,SignIn.class);
                                          startActivity(i1);
                                          break;
                                      }
                            }


                        // FragmentTransaction ft = mActivity.getFragmentManager().beginTransaction();
                        // ft.detach(mFragment);

                        ft.commit();
                        if(selectedfragment!=1)
                        {
                            setSelectedItem(position, (TextView) view.findViewById(R.id.title));
                            mDrawerList.setItemChecked(position, true);
                        }
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
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
            mDrawerLayout.closeDrawers();
            return;
        }
        if (selectedfragment != 1) {
            selectedfragment = 1;

            NavigationDrawerPage.mSelectedItem_text_pos = 1;
         //   Fragment fragment = new HomePage();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
         //   ft.replace(R.id.content_frame, fragment);
            ft.commit();
         //   mDrawerList.setAdapter(mMenuAdapter);
         //   mMenuAdapter.notifyDataSetChanged();
        } else {
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
            }//// else {
            //    isBackPress = true;
           /// }
        }
    public static void closeDrawers() {
        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mDrawer)) {
            mDrawerLayout.closeDrawer(mDrawer);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(!mDrawerLayout.isDrawerOpen(Gravity.START)) {
                    mDrawerLayout.openDrawer(GravityCompat.START);  // OPEN DRAWER
                }
                else{
                    mDrawerLayout.closeDrawers();
                }

               return true;

        }
        return super.onOptionsItemSelected(item);
    }
    }

