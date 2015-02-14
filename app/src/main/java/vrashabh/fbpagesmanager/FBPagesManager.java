package vrashabh.fbpagesmanager;

import android.app.Application;

import com.facebook.Session;

/**
 * Created by vrashabh on 2/13/15.
 */
public class FBPagesManager extends Application {

    //Saved across the application for demo-ing pleasure
    public static String pageID;
    public static String pageAccessToken;

    //Facebook key goodness
    public static Session sessionInstance;


}
