package com.example.tarun.thomso2k16;

import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by tarun on 08-08-2016.
 */
public class AppController extends MultiDexApplication {
    public static final String TAG = AppController.class.getSimpleName();
    /* Be sure to fill in the two strings below. */
    private RequestQueue mRequestQueue;

    private static AppController mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        MultiDex.install(AppController.this);
        //Dexter.initialize(this);
       /* CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
*/

    }

//    public void createPermissionListeners(HockeyActivity ctx) {
//        contactsPermissionListener = new DCPermissionListener(ctx);
//
//        Dexter.checkPermission(contactsPermissionListener, Manifest.permission.READ_CONTACTS);
//
//        if (!Dexter.isRequestOngoing()) {
//            Dexter.checkPermission(contactsPermissionListener, Manifest.permission.READ_CONTACTS);
//        }
//    }
//
//    public void createPermissionListeners(HockeyActivity ctx, String title, String msg, String permission) {
//        contactsPermissionListener = new DCPermissionListener(ctx, title, msg);
//
//        Dexter.checkPermission(contactsPermissionListener, permission);
//
//        if (!Dexter.isRequestOngoing()) {
//            Dexter.checkPermission(contactsPermissionListener, permission);
//        }
//    }

    public static synchronized AppController  getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
