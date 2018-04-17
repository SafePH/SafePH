package com.example.marj.safeph.hotline;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.marj.safeph.SigninActivity;
import com.example.marj.safeph.contact.ContactAddActivity;
import com.example.marj.safeph.hospital.HospitalActivity;
import com.example.marj.safeph.profile.ProfileActivity;
import com.example.marj.safeph.R;
import com.example.marj.safeph.contact.ContactActivity;
import com.example.marj.safeph.session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Marj on 3/14/2018.
 */

public class HotlineEditActivity extends AppCompatActivity{
    Intent intent;
    SessionManager session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotline_edit);

        intent = getIntent();
        session = new SessionManager(getApplicationContext());

        setupUI();
        setupButtons();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setupUI(){
        EditText etName = findViewById(R.id.hotline_edit_et_name);
        EditText etPhone = findViewById(R.id.hotline_edit_et_phone);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String phone = intent.getStringExtra("phone");

        etName.setText(name);
        etPhone.setText(phone);
    }

    private void setupButtons(){
        ImageView cancelBtn = findViewById(R.id.hotline_edit_cancel_btn);
        ImageView saveBtn = findViewById(R.id.hotline_edit_save_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancel = new Intent(HotlineEditActivity.this, HotlineDetails.class);
                Intent data = getIntent();

                String name = data.getStringExtra("name");
                String phone = data.getStringExtra("phone");
                cancel.putExtra("name",name);
                cancel.putExtra("phone",phone);
                startActivity(cancel);
                finish();
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldName = intent.getStringExtra("name");
                String oldPhone = intent.getStringExtra("phone");

                EditText etName = findViewById(R.id.hotline_edit_et_name);
                EditText etPhone = findViewById(R.id.hotline_edit_et_phone);
                String newName = intent.getStringExtra("name");
                String newPhone = intent.getStringExtra("phone");

                if(!etName.getText().toString().isEmpty())
                    newName = etName.getText().toString();
                if(!etPhone.getText().toString().isEmpty())
                    newPhone = etPhone.getText().toString();
                Log.d("HotlineEditActivity",oldName+" "+oldPhone+" -- "+newName+" "+newPhone);

                if (!etName.getText().toString().isEmpty() && !etPhone.getText().toString().isEmpty()){
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                Log.d("ContactEditActivity", String.valueOf(jsonResponse));
                                if(success){
                                    Toast.makeText(HotlineEditActivity.this,"Edit hotline successful",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(HotlineEditActivity.this, HotlineActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(HotlineEditActivity.this,"Edit hotline not successful",Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    HashMap<String, String> user = session.getUserDetails();
                    String username = user.get(SessionManager.KEY_NAME);

                    HotlineUpdateRequest hotlineUpdateRequest = new HotlineUpdateRequest(username,oldName,oldPhone,newName,newPhone,responseListener);
                    RequestQueue queue = Volley.newRequestQueue(HotlineEditActivity.this);
                    queue.add(hotlineUpdateRequest);
                    SigninActivity signinActivity = new SigninActivity();
                    signinActivity.getHotlines(getApplicationContext());
                }
                else
                    Toast.makeText(HotlineEditActivity.this, "No field must be empty", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
