package vrashabh.fbpagesmanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;


public class ContentCreationActivity extends ActionBarActivity {

    Context mContext = this;
    Bundle postParams;
    EditText message;
    EditText link;
    Switch pubSwitch;
    boolean isPostingError = false;
    String postingErrorMessage;
    Switch schedulePostForLater;
    private ProgressDialog dialog;
//    boolean isCameraUpload = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_creation);

        if (FBPagesManager.pageTitle != null)
            setTitle(FBPagesManager.pageTitle + " Feed");
        message = (EditText) findViewById(R.id.editText);
        link = (EditText) findViewById(R.id.editText2);
        pubSwitch = (Switch) findViewById(R.id.pubSwitch);
        schedulePostForLater = (Switch) findViewById(R.id.scheduledSwitch);

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
        //Empty pans make too much noi
        if (message.getText().toString().isEmpty())
            Toast.makeText(mContext, "Please enter a message to publish", Toast.LENGTH_LONG).show();
        else {
            dialog = new ProgressDialog(mContext);
            dialog.setMessage("Posting to Facebook Page");

            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
            new PostToPageAsync().execute();
        }
    }

    private class PostToPageAsync extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            postParams = new Bundle();

            postParams.putString("message", message.getText().toString());
            postParams.putString("link", link.getText().toString() == null ? "" : link.getText().toString());
            postParams.putBoolean("published", pubSwitch.isChecked() ? true : false);
            boolean scheduleForLater = schedulePostForLater.isChecked();
            //Timestamp scheduleTimeStamp = new Timestamp("2015-02-18 11:45:00.0");
            if(scheduleForLater) {
                long scheduledTimeStamp = (System.currentTimeMillis()/1000L + (15 * 60));
                postParams.putLong("scheduled_publish_time", scheduledTimeStamp);
            }
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
                            //An error occurred during posting to facebook
                            FacebookRequestError error = response.getError();
                            if (error != null) {
                                isPostingError = true;
                                postingErrorMessage = error.getErrorUserMessage();

                            } else {
                                isPostingError = false;
                            }

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
            if (!isPostingError)
                Toast.makeText(mContext, "Posted to Facebook page!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(mContext, "Sorry, " + postingErrorMessage, Toast.LENGTH_SHORT).show();
        }


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}
