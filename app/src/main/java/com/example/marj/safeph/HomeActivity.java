package com.example.marj.safeph;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marj.safeph.contact.ContactActivity;
import com.example.marj.safeph.hospital.HospitalActivity;
import com.example.marj.safeph.hotline.HotlineActivity;
import com.example.marj.safeph.profile.ProfileActivity;
import com.example.marj.safeph.session.AlertDialogManager;
import com.example.marj.safeph.session.SessionManager;
import com.example.marj.safeph.session.UserManager;

import java.util.HashMap;

/**
 * Created by Marj on 3/31/2018.
 */

public class HomeActivity extends AppCompatActivity{
    AlertDialogManager alert = new AlertDialogManager();
    SessionManager session;
    UserManager userManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupSession();
        setupNotification();
        setupUI();
        setupButtons();
        setupMenu();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setupUI() {

    }

    private void setupNotification() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent ii = new Intent(getApplicationContext(), ProfileActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.setBigContentTitle("In case of emergency");
        bigText.bigText("Show medical data and emergency contacts");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.main_logo);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);
        mBuilder.setOngoing(true);
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.main_logo));
        mBuilder.setBadgeIconType(R.drawable.main_logo);
        mBuilder.setLights(Color.MAGENTA, 5000, 5000);
        mBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        NotificationManager mNotificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify_001",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
        }

        mNotificationManager.notify(0, mBuilder.build());

    }

    private void setupButtons() {
        ImageView ivEmergency = findViewById(R.id.home_emergency_btn);
        ivEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:911"));
                if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });

        ImageView hospitalsBtn = findViewById(R.id.home_hospitals_btn);
        ImageView contactsBtn = findViewById(R.id.home_contacts_btn);
        ImageView hotlinesBtn = findViewById(R.id.home_hotlines_btn);

        hospitalsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, HospitalActivity.class);
                startActivity(intent);
            }
        });
        hotlinesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, HotlineActivity.class);
                startActivity(intent);
            }
        });
        contactsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ContactActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupSession() {
        session = new SessionManager(getApplicationContext());
        userManager = new UserManager(getApplicationContext());
        TextView tvName = findViewById(R.id.home_welcome);

        //Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = userManager.getUserDetails();

        // name
        String name = user.get(SessionManager.KEY_NAME);

        // displaying user data
        tvName.setText("Welcome, "+name);
    }

    private void setupMenu(){
        final ImageView ivHospitals = findViewById(R.id.profile_iv_hospitals);
        final ImageView ivHotlines = findViewById(R.id.profile_iv_hotlines);
        final ImageView ivContacts = findViewById(R.id.profile_iv_contacts);
        final ImageView ivProfile = findViewById(R.id.profile_iv_profile);

        ivHospitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, HospitalActivity.class);
                startActivity(intent);
            }
        });

        ivHotlines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, HotlineActivity.class);
                startActivity(intent);
            }
        });

        ivContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ContactActivity.class);
                startActivity(intent);
            }
        });

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

}
