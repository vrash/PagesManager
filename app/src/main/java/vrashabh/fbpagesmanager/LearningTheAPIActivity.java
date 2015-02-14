package vrashabh.fbpagesmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.Session;

import vrashabh.fbpagesmanager.utilities.Utilities;


public class LearningTheAPIActivity extends ActionBarActivity {

    Context mContext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_the_api);
        String pageName = getIntent().getStringExtra("EXTRA_PAGE_NAME");
        //Set the name of the page
        TextView pageNamer = (TextView) findViewById(R.id.pageactionname);
        pageNamer.setText(pageName);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_learning_the_api, menu);
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
        else if(id == R.id.action_logout)
        {
            Utilities.logout(mContext);
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    //Override them four views
    public void ViewPostsOnPage(View v) {


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode,
                resultCode, data);
    }

    public void CreateAPost(View v) {

        switch (v.getId()) {
            case R.id.button:
                Intent i = new Intent(mContext,FeedView.class);
                mContext.startActivity(i);
                break;
            case R.id.button2:
                break;
            case R.id.button3:
                break;
            case R.id.button4:
                break;
            case R.id.button5:
                break;

        }
    }
}

