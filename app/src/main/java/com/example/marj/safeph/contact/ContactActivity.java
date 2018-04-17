package com.example.marj.safeph.contact;

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
import com.example.marj.safeph.hotline.HotlineActivity;
import com.example.marj.safeph.session.UserManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Marj on 3/14/2018.
 */

public class ContactActivity extends AppCompatActivity {
    private final static String TAG = "ContactActivity";
    private final static int REQUEST_PHONE_CALL_CONTACTS = 99;

    private ArrayList<ContactModel> contacts;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter contactAdapter;
    private RecyclerView.LayoutManager contactLayout;

    UserManager userManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        userManager = new UserManager(getApplicationContext());

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkCallPermission();
        }

        getContacts();
        setupButtons();
        setupUI();
        setupMenu();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getContacts();
    }

    private void getContacts() {
        HashMap<String, String> user = userManager.getUserDetails();

        String jsonText = user.get(UserManager.KEY_CONTACTS);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<ContactModel>>(){}.getType();
        contacts = gson.fromJson(jsonText, type);
        for(int i = 0; i < contacts.size(); i++){
            Log.d(TAG, "getContacts(): " +
                    "Name: " + contacts.get(i).getName() +
                    " Phone: " + contacts.get(i).getPhone() +
                    " Relation: " + contacts.get(i).getRelation());
        }

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.ec_recyclerView);
        recyclerView.setHasFixedSize(true);

        contactLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(contactLayout);

        contactAdapter = new ContactAdapter(contacts);
        recyclerView.setAdapter(contactAdapter);
    }

    private void setupUI() {

    }

    private void setupButtons() {
        final ImageView ivHome = findViewById(R.id.profile_home_btn);
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        ImageView addContactBtn = findViewById(R.id.ec_add_contacts_btn);
        addContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactActivity.this, ContactAddActivity.class);
                startActivity(intent);
            }
        });

        ImageView ivSync = findViewById(R.id.contacts_sync_btn);
        ivSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SigninActivity signinActivity = new SigninActivity();
                signinActivity.getContacts(getApplicationContext());
                Intent intent = new Intent(ContactActivity.this, ContactActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupMenu() {
        final ImageView ivHospitals = findViewById(R.id.ec_hospital);
        final ImageView ivHotlines = findViewById(R.id.ec_hotlines);
        final ImageView ivContacts = findViewById(R.id.ec_contacts);
        final ImageView ivProfile = findViewById(R.id.ec_profile);

        ivHospitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactActivity.this, HospitalActivity.class);
                startActivity(intent);
            }
        });

        ivHotlines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactActivity.this, HotlineActivity.class);
                startActivity(intent);
            }
        });

        ivContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactActivity.this, ContactActivity.class);
                startActivity(intent);
            }
        });

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkCallPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL_CONTACTS);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL_CONTACTS);
            }
            return false;
        } else return true;
    }

}
