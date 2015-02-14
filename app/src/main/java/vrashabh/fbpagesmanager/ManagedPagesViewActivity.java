package vrashabh.fbpagesmanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import vrashabh.fbpagesmanager.Adapters.PageViewAdapter;
import vrashabh.fbpagesmanager.ORMpackages.AccountsResponse;


public class ManagedPagesViewActivity extends ActionBarActivity {

    ArrayList<AccountsResponse> responseAccounts;
    Context mContext = this;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managed_pages_view);
        dialog = new ProgressDialog(mContext);
        dialog.setMessage("Loading all the facebook goodness");
        dialog.show();
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

            ListView mpList = (ListView)findViewById(R.id.managedPagesView);
            PageViewAdapter mpAdapter = new PageViewAdapter(mContext,responseAccounts);
            mpList.setAdapter(mpAdapter);
          /*  mpList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                //@Override
                public void onItemClick(AdapterView arg0, View view,
                                        int position, long id) {
                    // user clicked a list item, make it "selected"
                    mpAdapter.setSelectedPosition(position);
                    //Do your stuff here
                }});*/
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}
