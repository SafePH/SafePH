package com.example.marj.safeph.contact;

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
import com.example.marj.safeph.hospital.HospitalActivity;
import com.example.marj.safeph.profile.ProfileActivity;
import com.example.marj.safeph.R;
import com.example.marj.safeph.hotline.HotlineActivity;
import com.example.marj.safeph.session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Marj on 3/31/2018.
 */

public class ContactEditActivity extends AppCompatActivity{
    Intent intent;
    SessionManager session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_edit);

        intent = getIntent();
        session = new SessionManager(getApplicationContext());

        setupButtons();
        setupUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setupButtons() {
        ImageView cancelBtn = findViewById(R.id.contacts_edit_cancel_btn);
        ImageView saveBtn = findViewById(R.id.hotline_edit_save_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancel = new Intent(ContactEditActivity.this, ContactDetails.class);

                String name = intent.getStringExtra("name");
                String phone = intent.getStringExtra("phone");
                String relation = intent.getStringExtra("relation");
                cancel.putExtra("name",name);
                cancel.putExtra("phone",phone);
                cancel.putExtra("relation",relation);
                startActivity(cancel);
                finish();
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldName = intent.getStringExtra("name");
                String oldPhone = intent.getStringExtra("phone");
                String oldRelation = intent.getStringExtra("relation");

                EditText etName = findViewById(R.id.contact_edit_et_name);
                EditText etPhone = findViewById(R.id.contact_edit_et_phone);
                EditText etRelation = findViewById(R.id.contact_edit_et_relation);
                String newName = intent.getStringExtra("name");
                String newPhone = intent.getStringExtra("phone");
                String newRelation = intent.getStringExtra("relation");

                if(!etName.getText().toString().isEmpty())
                    newName = etName.getText().toString();
                if(!etPhone.getText().toString().isEmpty())
                    newPhone = etPhone.getText().toString();
                if(!etRelation.getText().toString().isEmpty())
                    newRelation = etRelation.getText().toString();
                //Log.d("ContactEditActivity",oldName+" "+oldPhone+" "+oldRelation+" -- "+newName+" "+newPhone+" "+newRelation);

                if (!etName.getText().toString().isEmpty() && !etPhone.getText().toString().isEmpty() && !etRelation.getText().toString().isEmpty()){
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if(success){
                                    Toast.makeText(ContactEditActivity.this,"Edit contact successful",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ContactEditActivity.this, ContactActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(ContactEditActivity.this,"Edit contact not successful",Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    HashMap<String, String> user = session.getUserDetails();
                    String username = user.get(SessionManager.KEY_NAME);

                    ContactUpdateRequest contactUpdateRequest = new ContactUpdateRequest(username,oldName,oldPhone,oldRelation,newName,newPhone,newRelation,responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ContactEditActivity.this);
                    queue.add(contactUpdateRequest);
                    SigninActivity signinActivity = new SigninActivity();
                    signinActivity.getContacts(getApplicationContext());
                }
                else
                    Toast.makeText(ContactEditActivity.this, "No field must be empty", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void setupUI(){
        EditText etName = findViewById(R.id.contact_edit_et_name);
        EditText etPhone = findViewById(R.id.contact_edit_et_phone);
        EditText etRelation = findViewById(R.id.contact_edit_et_relation);

        String name = intent.getStringExtra("name");
        String phone = intent.getStringExtra("phone");
        String relation = intent.getStringExtra("relation");

        etName.setText(name);
        etPhone.setText(phone);
        etRelation.setText(relation);
    }
}
