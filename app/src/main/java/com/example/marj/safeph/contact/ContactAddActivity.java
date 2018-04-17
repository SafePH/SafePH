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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.marj.safeph.HomeActivity;
import com.example.marj.safeph.SigninActivity;
import com.example.marj.safeph.hospital.HospitalActivity;
import com.example.marj.safeph.profile.ProfileActivity;
import com.example.marj.safeph.R;
import com.example.marj.safeph.hotline.HotlineActivity;
import com.example.marj.safeph.session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Marj on 3/14/2018.
 */

public class ContactAddActivity extends AppCompatActivity{
    SessionManager session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_add);

        session = new SessionManager(getApplicationContext());

        setupButtons();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!mWifi.isConnected()) {
            final WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            alertDialogBuilder.setTitle("Wi-Fi Settings");
            alertDialogBuilder
                    .setMessage("Wi-Fi is disabled, Internet connection is required to proceed with this feature")
                    .setCancelable(false)
                    .setPositiveButton("Turn on",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            wifiManager.setWifiEnabled(true);
                        }
                    }).setNegativeButton("Go back", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(ContactAddActivity.this,ContactActivity.class);
                    startActivity(intent);
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    private void setupButtons() {
        final EditText etName = findViewById(R.id.eca_name_et);
        final EditText etPhone = findViewById(R.id.eca_phone_et);
        final EditText etRelation = findViewById(R.id.eca_relation_et);
        ImageView cancelBtn = findViewById(R.id.contact_add_cancel_btn);
        ImageView saveBtn = findViewById(R.id.contact_add_save_btn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancel = new Intent(ContactAddActivity.this, ContactActivity.class);
                Intent data = getIntent();

                String name = data.getStringExtra("name");
                String phone = data.getStringExtra("phone");
                String relation = data.getStringExtra("relation");
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
                String nameInput = etName.getText().toString();
                String phoneInput = etPhone.getText().toString();
                String relationInput = etRelation.getText().toString();

                if (!nameInput.isEmpty() && !phoneInput.isEmpty() && !relationInput.isEmpty()){
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if(success){
                                    Toast.makeText(ContactAddActivity.this,"Add contact successful",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ContactAddActivity.this, ContactActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(ContactAddActivity.this,"Add contact not successful",Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    HashMap<String, String> user = session.getUserDetails();
                    String username = user.get(SessionManager.KEY_NAME);

                    ContactAddRequest contactAddRequest = new ContactAddRequest(username,nameInput,phoneInput,relationInput,responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ContactAddActivity.this);
                    queue.add(contactAddRequest);
                    SigninActivity signinActivity = new SigninActivity();
                    signinActivity.getContacts(getApplicationContext());
                }
                else
                    Toast.makeText(ContactAddActivity.this, "No field must be empty", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
