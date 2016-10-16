package com.tarun.thomso2k16;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;



public class UserDetails extends AppCompatActivity {

    private CircleImageView profile_img;
    private ImageView qrcode;
    private TextView name;
    private TextView thomso_id;
    private TextView college;
    private Context context;
    String image,sqrcode;
    SessionManager sm;
    HashMap<String,String> userDetails;
    private TextView registerqr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        context=getApplicationContext();
        profile_img=(CircleImageView)findViewById(R.id.profile_image_user_details);
        qrcode = (ImageView)findViewById(R.id.qrcode_user_details);
        name = (TextView)findViewById(R.id.name_user_details);
        thomso_id=(TextView)findViewById(R.id.thomsoid_user_details);
        college=(TextView)findViewById(R.id.college_user_details);
        registerqr=(TextView)findViewById(R.id.register_with_qr_code);
        sm = new SessionManager(context);
        userDetails=sm.getUserDetails();
        name.setText(userDetails.get(SessionManager.KEY_NAME));
        thomso_id.setText(userDetails.get(SessionManager.KEY_USERID));
        college.setText(userDetails.get(SessionManager.KEY_COLLEGE));
        image=userDetails.get(SessionManager.KEY_USERIMAGE);
        sqrcode=userDetails.get(SessionManager.KEY_QRCODE);
        generateQRCode(sqrcode);
        Glide.with(getApplicationContext()).load(image).into(profile_img);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void generateQRCode(String sqrcode) {
        if(!sqrcode.equalsIgnoreCase("0")) {
            QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(sqrcode,
                    null,
                    Contents.Type.TEXT,
                    BarcodeFormat.QR_CODE.toString(),
                    150);
            try {
                Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
                qrcode.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        else{
            qrcode.setVisibility(View.GONE);
            registerqr.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(UserDetails.this, NavigationDrawerPage.class);
                startActivity(i);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(UserDetails.this, NavigationDrawerPage.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}
