package vrashabh.fbpagesmanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;

import vrashabh.fbpagesmanager.utilities.Utilities;


public class FeedView extends ActionBarActivity {

    Context mContext = this;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_view);
        dialog = new ProgressDialog(mContext);
        dialog.setMessage("Calling mission control...");
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        new GetPageFeedView().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feed_view, menu);
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
        } else if (id == R.id.action_logout) {
            Utilities.logout(mContext);
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private class GetPageFeedView extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
         /* make the API call */

            new Request(
                    FBPagesManager.sessionInstance,
                    "/"+FBPagesManager.pageID+"/feed",
                    null,
                    HttpMethod.GET,
                    new Request.Callback() {
                        public void onCompleted(Response response) {


                        }
                    }
            ).executeAndWait();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}
