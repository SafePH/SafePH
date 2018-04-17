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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.marj.safeph.SigninActivity;
import com.example.marj.safeph.hospital.HospitalActivity;
import com.example.marj.safeph.hotline.HotlineEditActivity;
import com.example.marj.safeph.profile.ProfileActivity;
import com.example.marj.safeph.R;
import com.example.marj.safeph.hotline.HotlineActivity;
import com.example.marj.safeph.session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Marj on 3/30/2018.
 */

public class ContactDetails extends AppCompatActivity{
    SessionManager session;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_details);

        session = new SessionManager(getApplicationContext());
        intent = getIntent();

        setupUI();
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
                    Intent intent = new Intent(ContactDetails.this,ContactActivity.class);
                    startActivity(intent);
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    private void setupUI() {
        String name = intent.getStringExtra("name");
        String phone = intent.getStringExtra("phone");
        String relation = intent.getStringExtra("relation");

        TextView tvName = findViewById(R.id.contact_details_name);
        TextView tvPhone = findViewById(R.id.contact_details_phone);
        TextView tvRelation = findViewById(R.id.contact_details_relation);

        tvName.setText(name);
        tvPhone.setText(phone);
        tvRelation.setText(relation);
    }


    private void setupButtons() {
        ImageView editBtn = findViewById(R.id.contact_details_edit_btn);
        ImageView deleteBtn = findViewById(R.id.contacts_details_delete_btn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactDetails.this,ContactEditActivity.class);
                Intent data = getIntent();

                String name = data.getStringExtra("name");
                String phone = data.getStringExtra("phone");
                String relation = data.getStringExtra("relation");
                intent.putExtra("name",name);
                intent.putExtra("phone",phone);
                intent.putExtra("relation",relation);
                startActivity(intent);
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameInput = intent.getStringExtra("name");
                String phoneInput = intent.getStringExtra("phone");
                String relationInput = intent.getStringExtra("relation");

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                Toast.makeText(ContactDetails.this,"Delete contact successful",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ContactDetails.this, ContactActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(ContactDetails.this,"Delete contact not successful",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                HashMap<String, String> user = session.getUserDetails();
                String username = user.get(SessionManager.KEY_NAME);

                ContactDeleteRequest contactDeleteRequest = new ContactDeleteRequest(username,nameInput,phoneInput,relationInput,responseListener);
                RequestQueue queue = Volley.newRequestQueue(ContactDetails.this);
                queue.add(contactDeleteRequest);
                SigninActivity signinActivity = new SigninActivity();
                signinActivity.getContacts(getApplicationContext());

            }
        });
    }
}
