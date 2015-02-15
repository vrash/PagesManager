package vrashabh.fbpagesmanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import vrashabh.fbpagesmanager.Adapters.FeedViewAdapter;
import vrashabh.fbpagesmanager.ORMpackages.FeedData;
import vrashabh.fbpagesmanager.utilities.Utilities;

/*TODO: LONG PRESS ITEM FOR LIKE AND COMMENT ITEMS*/

public class FeedView extends ActionBarActivity {

    Context mContext = this;
    ArrayList<FeedData> feedStream;
    FeedViewAdapter fvAdapter;
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
                    //Limit to 10 posts for viewing at any point
                    /*TODO: IMPROVE PERFORMANCE TO UNLIMITED SCROLLING*/

                    FBPagesManager.sessionInstance,
                    "/" + FBPagesManager.pageID + "/feed",
                    null,
                    HttpMethod.GET,
                    new Request.Callback() {
                        public void onCompleted(Response response) {

                            try {
                                JSONObject jobj = new JSONObject(response.getRawResponse());
                                JSONArray data = jobj.getJSONArray("data");
                                //Parse the large feed stream - limit to 10 objects for now
                                feedStream = new ArrayList<FeedData>();
                                for (int i = 0; i < data.length(); i++) {
                                    FeedData feedData = new FeedData();
                                    JSONObject indiObjects = data.getJSONObject(i);
                                    feedData.setId(indiObjects.getString("id"));
                                    feedData.setCreated_time(indiObjects.getString("created_time"));
                                    feedData.setUpdated_time(indiObjects.getString("updated_time"));
                                    feedData.setType(indiObjects.getString("type"));

                                    if (indiObjects.getString("type").equals("photo")) {
                                        feedData.setPicture(indiObjects.getString("picture"));
                                        feedData.setLink(indiObjects.getString("link"));
                                        feedData.setIcon(indiObjects.getString("icon"));
                                        feedData.setStory(indiObjects.getString("story"));

                                    } else if (indiObjects.getString("type").equals("status")) {
                                        feedData.setMessage(indiObjects.getString("message"));
                                    }
                                    //Fill up the arraylist with the objects
                                    feedStream.add(feedData);
                                }

                            } catch (Exception ex) {
                                Log.e("FeedView", ex.getMessage());
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

            ListView feedList = (ListView) findViewById(R.id.feedlist);
            fvAdapter = new FeedViewAdapter(mContext, feedStream);
            feedList.setAdapter(fvAdapter);
            feedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Toast.makeText(mContext, "THIS IS A POST", Toast.LENGTH_SHORT).show();


                }

            });
        }


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    //Use this to get post insights directly on item click
    private class GetSomeInsights extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
         /* make the API call */

            new Request(

                    FBPagesManager.sessionInstance,
                    "/" + FBPagesManager.pageID + "/feed",
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
