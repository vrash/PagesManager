package vrashabh.fbpagesmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.google.gson.Gson;

import java.util.Arrays;

import vrashabh.fbpagesmanager.ORMpackages.AccountsResponse;
import vrashabh.fbpagesmanager.ORMpackages.UserInfo;


public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

    Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        LoginButton fbLogin = (LoginButton) findViewById(R.id.authButton);
        //Its a lot of permissions, but then I dont like the workaround yet
        fbLogin.setPublishPermissions(Arrays.asList("basic_info", "email", "publish_stream", "user_likes", "manage_pages", "publish_actions", "read_insights"));

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode,
                resultCode, data);


    }

    @Override
    public void onClick(View v) {
        // start Facebook Login
        Session.openActiveSession(this, true, new Session.StatusCallback() {

            // callback when session changes state
            @Override
            public void call(Session session, SessionState state,
                             Exception exception) {
                if (session.isOpened()) {

                    // make request to the /me API
                    Request.newMeRequest(session,
                            new Request.GraphUserCallback() {

                                // callback after Graph API response with user
                                // object
                                @Override
                                public void onCompleted(GraphUser user,
                                                        Response response) {
                                    if (user != null) {
                                        UserInfo uInfo = new UserInfo();
                                        uInfo.setUserName(user.getName());

                                    }
                                }
                            }).executeAsync();

                    /* make the API call */
                    new Request(
                            session,
                            "/me/accounts",
                            null,
                            HttpMethod.GET,
                            new Request.Callback() {
                                public void onCompleted(Response response) {

                                    Gson gsonResponse = new Gson();
                                    /*JSONObject jsonObj=null;
                                    try {
                                         jsonObj = new JSONObject(response.toString());
                                    }
                                    catch(Exception e)
                                    {
                                        Log.d("LoginActivity", "JSON FORMAT IS PROBABLY WRONG")
                                    }*/
                                    AccountsResponse responseAccounts = gsonResponse.fromJson(response.getRawResponse(), AccountsResponse.class);

                                }
                            }
                    ).executeAsync();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        //Session session = Session.getActiveSession();
        FBPagesManager.sessionInstance = Session.getActiveSession();
        if (FBPagesManager.sessionInstance != null && FBPagesManager.sessionInstance.isOpened()) {
            Toast.makeText(mContext, FBPagesManager.sessionInstance.getAccessToken(), Toast.LENGTH_LONG).show();
            //All done, now go to the LearningActivityClass
            Intent x = new Intent(mContext, ManagedPagesViewActivity.class);
            mContext.startActivity(x);
        }

    }
}
