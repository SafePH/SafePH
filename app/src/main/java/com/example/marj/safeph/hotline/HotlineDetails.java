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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.marj.safeph.SigninActivity;
import com.example.marj.safeph.contact.ContactEditActivity;
import com.example.marj.safeph.hospital.HospitalActivity;
import com.example.marj.safeph.profile.ProfileActivity;
import com.example.marj.safeph.R;
import com.example.marj.safeph.contact.ContactActivity;
import com.example.marj.safeph.session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Marj on 3/14/2018.
 */

public class HotlineDetails extends AppCompatActivity{
    Intent intent;
    SessionManager session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotline_details);

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
                    Intent intent = new Intent(HotlineDetails.this,HotlineActivity.class);
                    startActivity(intent);
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    private void setupUI(){
        String name = intent.getStringExtra("name");
        String phone = intent.getStringExtra("phone");

        TextView tvName = findViewById(R.id.hotline_details_tv_name);
        TextView tvPhone = findViewById(R.id.hotline_details_tv_phone);

        tvName.setText(name);
        tvPhone.setText(phone);
    }

    private void setupButtons(){
        ImageView editBtn = findViewById(R.id.hotline_detail_edit_btn);
        ImageView deleteBtn = findViewById(R.id.hotline_detail_delete_btn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HotlineDetails.this,HotlineEditActivity.class);
                Intent data = getIntent();

                String name = data.getStringExtra("name");
                String phone = data.getStringExtra("phone");
                intent.putExtra("name",name);
                intent.putExtra("phone",phone);
                startActivity(intent);
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameInput = intent.getStringExtra("name");
                String phoneInput = intent.getStringExtra("phone");

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                Toast.makeText(HotlineDetails.this,"Delete hotline successful",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(HotlineDetails.this, HotlineActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(HotlineDetails.this,"Delete hotline not successful",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                HashMap<String, String> user = session.getUserDetails();
                String username = user.get(SessionManager.KEY_NAME);

                HotlineDeleteRequest hotlineDeleteRequest = new HotlineDeleteRequest(username,nameInput,phoneInput,responseListener);
                RequestQueue queue = Volley.newRequestQueue(HotlineDetails.this);
                queue.add(hotlineDeleteRequest);
                SigninActivity signinActivity = new SigninActivity();
                signinActivity.getHotlines(getApplicationContext());

            }
        });
    }
}
