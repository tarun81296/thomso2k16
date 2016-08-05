package com.example.tarun.thomso2k16;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

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
        password= (EditText) findViewById(R.id.password_edittext);
        userName= (EditText) findViewById(R.id.username_edittext);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(SignIn.this,NavigationDrawerPage.class);
                startActivity(i);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void submitClicked(View view){
        mPassword=password.getText().toString();
        mUsername=userName.getText().toString();
       if(mUsername.length()==0){
           erroredit(userName,"Username can't be empty");
       }
        else{
           if(mPassword.length()==0){
               erroredit(password,"Password can't be empty");
           }
           else{

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
    public void onBackPressed(){
        Intent i = new Intent(SignIn.this,NavigationDrawerPage.class);
        startActivity(i);
        finish();
    }
}
