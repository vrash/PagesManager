package vrashabh.fbpagesmanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import vrashabh.fbpagesmanager.Adapters.PageViewAdapter;
import vrashabh.fbpagesmanager.ORMpackages.AccountsResponse;
import vrashabh.fbpagesmanager.utilities.Utilities;


public class ManagedPagesViewActivity extends ActionBarActivity {

    ArrayList<AccountsResponse> responseAccounts;
    Context mContext = this;
    PageViewAdapter mpAdapter;
    private ProgressDialog dialog;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managed_pages_view);
        dialog = new ProgressDialog(mContext);
        dialog.setMessage("Loading all the facebook goodness");
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        new GetPageDataASync().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_managed_pages_view, menu);
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

    private class GetPageDataASync extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
         /* make the API call */
            new Request(
                    FBPagesManager.sessionInstance,
                    "/me/accounts",
                    null,
                    HttpMethod.GET,
                    new Request.Callback() {
                        public void onCompleted(Response response) {

                            //Gson gsonResponse = new Gson();
                            //responseAccounts = gsonResponse.fromJson(response.getRawResponse(), AccountsResponse.class);
                            //Shitty parse - godammn gson has circular references !
                            try {
                                JSONObject jobj = new JSONObject(response.getRawResponse());
                                JSONArray data = jobj.getJSONArray("data");
                                responseAccounts = new ArrayList<AccountsResponse>();
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject indi = data.getJSONObject(i);
                                    AccountsResponse nbj = new AccountsResponse();
                                    nbj.setAccess_token(indi.getString("access_token"));
                                    nbj.setCategory(indi.getString("category"));
                                    nbj.setName(indi.getString("name"));
                                    nbj.setId(indi.getString("id"));
                                    responseAccounts.add(nbj);
                                }
                            } catch (Exception e) {
                                Log.e("ManagedPagesView", e.getMessage());
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

            //For the embarassing case when you dont own facebook pages
            if (responseAccounts.size() == 0) {
                AccountsResponse nbj = new AccountsResponse();
                nbj.setName("Sorry you don't manage any pages :( ");
                nbj.setCategory("But you can get started today! :) ");
                nbj.setId("-1");
                nbj.setAccess_token("-1");
                responseAccounts.add(nbj);
            }

            ListView mpList = (ListView) findViewById(R.id.managedPagesView);
            mpAdapter = new PageViewAdapter(mContext, responseAccounts);
            mpList.setAdapter(mpAdapter);

            mpList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    FBPagesManager.pageAccessToken = responseAccounts.get(position).getAccess_token().toString();
                    if (FBPagesManager.pageAccessToken == "-1") {
/*TODO:MESSAGE WHEN NO ITEMS PRESENT AND LIST IS CLICKED*/
                    } else {
                        FBPagesManager.pageID = responseAccounts.get(position).getId().toString();
                        FBPagesManager.pageTitle = responseAccounts.get(position).getName().toString();

                        // Call the rendering activity
                        Intent i = new Intent(mContext, LearningTheAPIActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        i.putExtra("EXTRA_PAGE_NAME", responseAccounts.get(position).getName().toString());
                        startActivity(i);

                    }
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


}
