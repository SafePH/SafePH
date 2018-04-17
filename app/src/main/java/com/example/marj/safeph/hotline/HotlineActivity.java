package com.example.marj.safeph.hotline;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.marj.safeph.HomeActivity;
import com.example.marj.safeph.SigninActivity;
import com.example.marj.safeph.hospital.HospitalActivity;
import com.example.marj.safeph.profile.ProfileActivity;
import com.example.marj.safeph.R;
import com.example.marj.safeph.contact.ContactActivity;
import com.example.marj.safeph.session.UserManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Marj on 3/13/2018.
 */

public class HotlineActivity extends AppCompatActivity{
    private final static String TAG = "HotlineActivity";

    private ArrayList<HotlineModel> hotlines;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter hotlineAdapter;
    private RecyclerView.LayoutManager hotlineLayout;
    public static final int REQUEST_PHONE_CALL_HOTLINES = 99;

    UserManager userManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotlines);

        userManager = new UserManager(getApplicationContext());

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkCallPermission();
        }

        getHotlines();
        setupButtons();
        setupUI();
        setupMenu();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getHotlines();
    }

    private void getHotlines(){
        HashMap<String, String> user = userManager.getUserDetails();

        String jsonText = user.get(UserManager.KEY_HOTLINES);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<HotlineModel>>(){}.getType();
        hotlines = gson.fromJson(jsonText, type);
        for(int i = 0; i < hotlines.size(); i++){
            Log.d(TAG, "getHotlines(): " +
                    "Name: " + hotlines.get(i).getName() +
                    " Phone: " + hotlines.get(i).getPhone());
        }

        setupRecyclerView();
    }

    private void setupRecyclerView(){
        recyclerView = findViewById(R.id.hotline_recyclerview);
        recyclerView.setHasFixedSize(true);

        hotlineLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(hotlineLayout);

//        for(int i = 0; i < hotlines.size(); i++){
//            Log.d(TAG, "hotlines in setupRV(): " +
//                    "Name: " + hotlines.get(i).getName() +
//                    " Phone: " + hotlines.get(i).getPhone());
//        }
        hotlineAdapter = new HotlineAdapter(hotlines);
        recyclerView.setAdapter(hotlineAdapter);
    }

    private void setupButtons(){
        final ImageView addHotlineBtn = findViewById(R.id.hotline_iv_addhotline_btn);
        addHotlineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HotlineActivity.this, HotlineAddActivity.class);
                startActivity(intent);
            }
        });

        final ImageView ivHome = findViewById(R.id.profile_home_btn);
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HotlineActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        final ImageView ivSync = findViewById(R.id.hotlines_sync_btn);
        ivSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SigninActivity signinActivity = new SigninActivity();
                signinActivity.getHotlines(getApplicationContext());
                Intent intent = new Intent(HotlineActivity.this, HotlineActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupUI() {

    }

    private void setupMenu() {
        final ImageView ivHospitals = findViewById(R.id.profile_iv_hospitals);
        final ImageView ivHotlines = findViewById(R.id.profile_iv_hotlines);
        final ImageView ivContacts = findViewById(R.id.profile_iv_contacts);
        final ImageView ivProfile = findViewById(R.id.profile_iv_profile);

        ivHospitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HotlineActivity.this, HospitalActivity.class);
                startActivity(intent);
            }
        });

        ivHotlines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HotlineActivity.this, HotlineActivity.class);
                startActivity(intent);
            }
        });

        ivContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HotlineActivity.this, ContactActivity.class);
                startActivity(intent);
            }
        });

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HotlineActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkCallPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL_HOTLINES);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL_HOTLINES);
            }
            return false;
        } else return true;
    }
}
