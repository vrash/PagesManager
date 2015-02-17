package vrashabh.fbpagesmanager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;

import java.io.ByteArrayOutputStream;
import java.io.File;


public class PhotoPostCreationActivity extends ActionBarActivity {
    private static final int TAKE_PICTURE = 1;
    Context mContext = this;
    Bundle postParams;
    EditText message;
    ImageView camImage;
    Switch pubSwitch;
    Bitmap scaledPhoto;
    boolean isPostingError;
    String postingErrorMessage;
    private ProgressDialog dialog;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_post_creation);
        if (FBPagesManager.pageTitle != null)
            setTitle(FBPagesManager.pageTitle + " Feed");
        message = (EditText) findViewById(R.id.cammsg);
        camImage = (ImageView) findViewById(R.id.camimage);
        pubSwitch = (Switch) findViewById(R.id.pubSwitchCam);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo_post_creation, menu);
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

    public void PostPhotoToPage(View v) {
        //Empty pans make too much noi
        if (message.getText().toString().isEmpty())
            Toast.makeText(mContext, "Please enter a message to publish", Toast.LENGTH_LONG).show();
        else {
            dialog = new ProgressDialog(mContext);
            dialog.setMessage("Posting to Facebook Page");

            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
            new PostPhotoToPageAsync().execute();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    getContentResolver().notifyChange(selectedImage, null);

                    ContentResolver cr = getContentResolver();
                    Bitmap bitmap;
                    try {
                        bitmap = android.provider.MediaStore.Images.Media
                                .getBitmap(cr, selectedImage);
                        //Scale the image to the bracket size eh!
                        scaledPhoto = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
                        camImage.setImageBitmap(scaledPhoto);
                       /* Toast.makeText(this, selectedImage.toString(),
                                Toast.LENGTH_LONG).show();*/
                    } catch (Exception e) {
                        Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }
                }
        }
    }

    public void takePhoto(View view) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    private class PostPhotoToPageAsync extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            postParams = new Bundle();

            postParams.putString("message", message.getText().toString());
            postParams.putBoolean("published", pubSwitch.isChecked() ? true : false);
            //Post to the page as the page user
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            scaledPhoto.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            postParams.putByteArray("source", byteArray);
            postParams.putString("access_token", FBPagesManager.pageAccessToken);
         /* make the API call */
            new Request(
                    FBPagesManager.sessionInstance,
                    "/" + FBPagesManager.pageID + "/photos",
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
