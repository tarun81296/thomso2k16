package com.tarun.thomso2k16;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import java.util.HashMap;

/**
 * Created by tarun on 29-07-2016.
 */
public class SessionManager {
    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";
    // Email address (make variable public to access from outside)
    public static final String KEY_COLLEGE = "college";
    public static final String KEY_USERID = "userid";
    public static final String KEY_QRCODE = "qrcode";
    public static final String KEY_DATA = "data";
    public static final String KEY_USERIMAGE = "userimage";
    public static final String KEY_SIGNOUT_GOOGLEPLUS = "googleplus";
    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    // Shared Preferences
    android.content.SharedPreferences pref;
    // Editor for Shared preferences
    android.content.SharedPreferences.Editor editor;
    // Context
    Context context;

    // public static final String KEY_USERACTIVITY = "useractivity";

    //  public static final String KEY_IS_SELLER = "is_seller";

    //  public static final String KEY_CARTCOUNT = "cartcount";

    //  public static final String KEY_HOMEPAGE_IDENTIFY = "homepage";
    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Constructor
    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(String name, String college, String userid, String qrcodevalue, String userimage) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_COLLEGE, college);
        editor.putString(KEY_USERID, userid);
        editor.putString(KEY_QRCODE, qrcodevalue);
        editor.putString(KEY_USERIMAGE, userimage);
        //  editor.putString(KEY_IS_SELLER, isseller);

        // commit changes
        editor.commit();
    }

    public void dataEntered() {
        editor.putBoolean(KEY_DATA, true);
        editor.commit();
    }

    public void createUserImagesession(String userimage) {
        // Storing login value as TRUE
        editor.putString(KEY_USERIMAGE, userimage);

        // commit changes
        editor.commit();
    }

    public void SetGoogleSignout(String check) {
        // Storing login value as TRUE
        editor.putString(KEY_SIGNOUT_GOOGLEPLUS, check);
        // commit changes
        editor.commit();
    }


    /**
     * Check login method wil check user login status If false it will redirect
     * user to login page Else won't do anything
     */
    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, NavigationDrawerPage.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);
        }

    }

    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, ""));
        user.put(KEY_COLLEGE, pref.getString(KEY_COLLEGE, ""));
        user.put(KEY_USERID, pref.getString(KEY_USERID, ""));
        user.put(KEY_QRCODE, pref.getString(KEY_QRCODE, ""));
        user.put(KEY_USERIMAGE, pref.getString(KEY_USERIMAGE, ""));
        //   user.put(KEY_USERACTIVITY, pref.getString(KEY_USERACTIVITY, ""));
        //   user.put(KEY_IS_SELLER, pref.getString(KEY_IS_SELLER, ""));

        // return user
        return user;
    }

    public String getImage() {
        return pref.getString(KEY_USERIMAGE, "");
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(context, NavigationDrawerPage.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(i);

    }


    public HashMap<String, String> GetGoogleSignout() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_SIGNOUT_GOOGLEPLUS, pref.getString(KEY_SIGNOUT_GOOGLEPLUS, ""));
        return user;
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public boolean isData() {
        return pref.getBoolean(KEY_DATA, false);
    }
}
