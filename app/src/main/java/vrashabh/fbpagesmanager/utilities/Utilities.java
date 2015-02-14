package vrashabh.fbpagesmanager.utilities;

import android.content.Context;
import android.content.Intent;

import com.facebook.Session;

import vrashabh.fbpagesmanager.FBPagesManager;
import vrashabh.fbpagesmanager.LoginActivity;

/**
 * Created by vrashabh on 2/14/15.
 */
public class Utilities {
    public static void logout(Context mContext){

        // find the active session which can only be facebook in my app
        Session session = Session.getActiveSession();
        // run the closeAndClearTokenInformation which does the following
        // DOCS : Closes the local in-memory Session object and clears any persistent
        // cache related to the Session.
        session.closeAndClearTokenInformation();
        FBPagesManager.sessionInstance=null;
        // return the user to the login screen
        Intent newIntent = new Intent(mContext,LoginActivity.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(newIntent);
        // make sure the user can not access the page after he/she is logged out
        // clear the activity stack

    }
}
