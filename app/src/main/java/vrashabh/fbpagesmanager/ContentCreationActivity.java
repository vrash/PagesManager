package vrashabh.fbpagesmanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;


public class ContentCreationActivity extends ActionBarActivity {

    Context mContext = this;
    Bundle postParams;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_creation);
        if (FBPagesManager.pageTitle != null)
            setTitle(FBPagesManager.pageTitle + " Feed");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_content_creation, menu);
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

    public void PostToPage(View v) {
        dialog = new ProgressDialog(mContext);
        dialog.setMessage("Posting to Facebook Page");

        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        new PostToPageAsync().execute();
    }

    private class PostToPageAsync extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            postParams = new Bundle();
            postParams.putString("message", "message");
            postParams.putString("name", "name");
            //Post to the page as the page user
            postParams.putString("access_token", FBPagesManager.pageAccessToken);
         /* make the API call */
            new Request(
                    FBPagesManager.sessionInstance,
                    "/" + FBPagesManager.pageID + "/feed",
                    postParams,
                    HttpMethod.POST,
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
            Toast.makeText(mContext, "Posted to Facebook page!", Toast.LENGTH_SHORT).show();
        }


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}
