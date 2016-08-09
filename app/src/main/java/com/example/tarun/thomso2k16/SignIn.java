package com.example.tarun.thomso2k16;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SignIn extends AppCompatActivity {
    public EditText userName;
    public EditText password;
    public String mUsername;
    public String mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        password = (EditText) findViewById(R.id.password_edittext);
        userName = (EditText) findViewById(R.id.username_edittext);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(SignIn.this, NavigationDrawerPage.class);
                startActivity(i);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void submitClicked(View view) {
        mPassword = password.getText().toString();
        mUsername = userName.getText().toString();
        if (mUsername.length() == 0) {
            erroredit(userName, "Username can't be empty");
        } else {
            if (mPassword.length() == 0) {
                erroredit(password, "Password can't be empty");
            } else {
                LoginAsyncTask async_Event_Post = new LoginAsyncTask();
                async_Event_Post.execute("http://www.thomso.in/quiz/login.php");
            }
        }
    }

    private void erroredit(EditText editname, String msg) {
        editname.requestFocus();
        Animation shake = AnimationUtils.loadAnimation(SignIn.this, R.anim.shake);
        editname.startAnimation(shake);

//        ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.parseColor("#ffffff"));
//        SpannableStringBuilder ssbuilder = new SpannableStringBuilder(msg);
//        ssbuilder.setSpan(fgcspan, 0, msg.length(), 0);
        editname.setError(msg);
    }

    public void onBackPressed() {
        Intent i = new Intent(SignIn.this, NavigationDrawerPage.class);
        startActivity(i);
        finish();
    }

    class LoginAsyncTask extends AsyncTask<String, Void, Boolean> {

        String msg = "", response_status = "";
        int status;
        String Response = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(String... urls) {

            // Session class instance
            //  session_manager = new SessionManager(ActionBarActivity_Subclass_Homepage.this);
            // commonsession = new CommonIDSessionManager(ActionBarActivity_Subclass_Homepage.this);

            // get user data from session
            //   HashMap<String, String> user = commonsession.getUserDetails();
            // commonid = user.get(CommonIDSessionManager.KEY_COMMONID);

            // code to post data to server

            try {
                HttpClient httpclient = new DefaultHttpClient();
                httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, "thomsomobileapp");
                HttpPost httppost = new HttpPost(urls[0]);

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

                nameValuePairs.add(new BasicNameValuePair("username", mUsername));
                nameValuePairs.add(new BasicNameValuePair("password", mPassword));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                Response = EntityUtils.toString(entity);
                status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    JSONObject object = new JSONObject(Response);
                    response_status = object.getString("status");
                    msg = object.getString("message");

                    if (response_status.equalsIgnoreCase("1")) {
                        Toast.makeText(SignIn.this, "Logged In!!!", Toast.LENGTH_SHORT).show();
                    }
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(Boolean result) {


        }
    }

}