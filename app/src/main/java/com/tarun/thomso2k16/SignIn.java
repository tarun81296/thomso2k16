package com.tarun.thomso2k16;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class SignIn extends AppCompatActivity {
    public EditText userName;
    public EditText password;
    public String mUsername;
    public String mPassword;
    public SessionManager sm;
    ProgressBar pb;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        context = getApplicationContext();
        password = (EditText) findViewById(R.id.password_edittext);
        userName = (EditText) findViewById(R.id.username_edittext);
        sm = new SessionManager(context);
        pb = (ProgressBar) findViewById(R.id.progress_bar);
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
        Log.e("debug", "submit clicked!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        mPassword = password.getText().toString();
        mUsername = userName.getText().toString();
        if (mUsername.length() == 0) {
            erroredit(userName, "Username can't be empty");
        } else {
            if (mPassword.length() == 0) {
                erroredit(password, "Password can't be empty");
            } else {
                LoginAsyncTask async_Event_Post = new LoginAsyncTask();
                async_Event_Post.execute("http://www.thomso.in/app/Login.php");
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

        String msg = "", response_status = "", name = "", college = "", thomsoid = "", image = "",qrcodevalue="";
        int status;
        String Response = null;

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            Log.e("debug", "on pre execute login async task");
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(String... urls) {

            try {
                HttpClient httpclient = new DefaultHttpClient();
                httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, "thomsomobileapp");
                HttpPost httppost = new HttpPost(urls[0]);

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

                nameValuePairs.add(new BasicNameValuePair("thomsoid", mUsername));
                nameValuePairs.add(new BasicNameValuePair("emailid", mPassword));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                Response = EntityUtils.toString(entity);
                status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    JSONArray array = new JSONArray(Response);
                    JSONObject object = array.getJSONObject(0);
                    Log.e("debug", " " + Response);
                    response_status = object.getString("success");


                    if (response_status.equalsIgnoreCase("1")) {
                        Log.e("doInBackgroung", "Logged in!!!");
                        name = object.getString("name");
                        college = object.getString("college");
                        thomsoid = object.getString("thomsoid");
                        qrcodevalue=object.getString("qrcodevalue");
                        image = "http://thomso.in/register/" + object.getString("image");
                        //  Toast.makeText(SignIn.this, "Logged In!!!", Toast.LENGTH_SHORT).show();
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
            pb.setVisibility(View.GONE);
            if (response_status.equalsIgnoreCase("1")) {
                sm.createLoginSession(name, college, thomsoid, qrcodevalue, image);
                sm.createUserImagesession(image);
                Intent i = new Intent(SignIn.this, NavigationDrawerPage.class);
                startActivity(i);
            } else if (response_status.equalsIgnoreCase("0")) {
                Toast.makeText(SignIn.this, "Invalid Details!!", Toast.LENGTH_LONG).show();
            }

        }
    }

}