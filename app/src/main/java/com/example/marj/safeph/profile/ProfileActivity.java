package com.example.marj.safeph.profile;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marj.safeph.HomeActivity;
import com.example.marj.safeph.R;
import com.example.marj.safeph.contact.ContactActivity;
import com.example.marj.safeph.contact.ContactModel;
import com.example.marj.safeph.hospital.HospitalActivity;
import com.example.marj.safeph.hospital.HospitalManager;
import com.example.marj.safeph.hotline.HotlineActivity;
import com.example.marj.safeph.session.SessionManager;
import com.example.marj.safeph.session.UserManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Marj on 3/11/2018.
 */

public class ProfileActivity extends AppCompatActivity {
    private final static int NOTIFICATION_ID = 24;

    SessionManager session;
    UserManager userManager;
    HospitalManager hospitalManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        session = new SessionManager(getApplicationContext());
        userManager = new UserManager(getApplicationContext());
        hospitalManager = new HospitalManager(getApplicationContext());

        setupUI();
        setupMenu();
        setupButtons();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setupUI();
    }

    private void setupUI() {
        final TextView tvName = findViewById(R.id.profile_tv_name);
        final TextView tvAddress = findViewById(R.id.profile_tv_address);
        final TextView tvBloodtype = findViewById(R.id.profile_tv_bloodtype);
        final TextView tvBirthday = findViewById(R.id.profile_tv_birthday);
        final TextView tvDiabetic = findViewById(R.id.profile_tv_diabetic);
        final TextView tvOrgandonor = findViewById(R.id.profile_tv_organdonor);
        final TextView tvMedication = findViewById(R.id.profile_tv_medication);
        final TextView tvAllergies = findViewById(R.id.profile_tv_allergies);
        final TextView tvContact1 = findViewById(R.id.profile_tv_contact1);
        final TextView tvContact2 = findViewById(R.id.profile_tv_contact2);
        final TextView tvContact3 = findViewById(R.id.profile_tv_contact3);
        final ImageView ivCall1 = findViewById(R.id.contact1_call);
        final ImageView ivCall2 = findViewById(R.id.contact2_call);
        final ImageView ivCall3 = findViewById(R.id.contact_call3);

        HashMap<String, String> user = userManager.getUserDetails();

        String name = user.get(UserManager.KEY_NAME);
        String address = user.get(UserManager.KEY_ADDRESS);
        String bloodtype = user.get(UserManager.KEY_BLOODTYPE);
        String birthday = user.get(UserManager.KEY_BIRTHDAY);
        String diabetic = user.get(UserManager.KEY_DIABETIC);
        String organdonor = user.get(UserManager.KEY_ORGANDONOR);
        String medication = user.get(UserManager.KEY_MEDICATIONS);
        String allergies = user.get(UserManager.KEY_ALLERGIES);
        String jsonText = user.get(UserManager.KEY_CONTACTS);
        final ArrayList<ContactModel> contacts;

//        Log.d("Profile--","name = "+name);
//        Log.d("Profile--","bloodtype = "+bloodtype);
//        Log.d("Profile--","birthday = "+birthday);
//        Log.d("Profile--","address = "+address);
//        Log.d("Profile--","diabetic = "+diabetic);
//        Log.d("Profile--","medications = "+medication);
//        Log.d("Profile--","allergies = "+allergies);
//        Log.d("Profile--","organdonor = "+organdonor);

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<ContactModel>>(){}.getType();
        contacts = gson.fromJson(jsonText, type);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf = new SimpleDateFormat("MMM dd, yyyy");
        String yourFormatedDateString = sdf.format(date);

        tvName.setText(name);
        tvAddress.setText(address);
        tvBloodtype.setText(bloodtype);
        tvBirthday.setText(yourFormatedDateString);
        tvDiabetic.setText("Diabetic: " + diabetic);
        tvOrgandonor.setText("Organ Donor: " + organdonor);
        tvMedication.setText("Medication: " + medication);
        tvAllergies.setText("Allergies: " + allergies);
        if (contacts.size() == 0){
            tvContact1.setText("N/A");
            tvContact2.setText("N/A");
            tvContact3.setText("N/A");
        }
        else if (contacts.size() == 1){
            tvContact1.setText(contacts.get(0).getName()+" ("+contacts.get(0).getPhone()+")");
            tvContact2.setText("N/A");
            tvContact3.setText("N/A");
            ivCall1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+contacts.get(0).getPhone()));
                    if (ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    context.startActivity(callIntent);
                }
            });
        }
        else if (contacts.size() == 2){
            tvContact1.setText(contacts.get(0).getName()+" ("+contacts.get(0).getPhone()+")");
            tvContact2.setText(contacts.get(1).getName()+" ("+contacts.get(1).getPhone()+")");
            tvContact3.setText("N/A");
            ivCall1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+contacts.get(0).getPhone()));
                    if (ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    context.startActivity(callIntent);
                }
            });
            ivCall2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+contacts.get(1).getPhone()));
                    if (ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    context.startActivity(callIntent);
                }
            });
        }
        if (contacts.size() >= 3) {
            tvContact1.setText(contacts.get(0).getName()+" ("+contacts.get(0).getPhone()+")");
            tvContact2.setText(contacts.get(1).getName()+" ("+contacts.get(1).getPhone()+")");
            tvContact3.setText(contacts.get(2).getName()+" ("+contacts.get(2).getPhone()+")");
            ivCall1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+contacts.get(0).getPhone()));
                    if (ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    context.startActivity(callIntent);
                }
            });
            ivCall2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+contacts.get(1).getPhone()));
                    if (ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    context.startActivity(callIntent);
                }
            });
            ivCall3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+contacts.get(2).getPhone()));
                    if (ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    context.startActivity(callIntent);
                }
            });
        }
    }

    private void setupButtons() {
        final ImageView ivHome = findViewById(R.id.profile_home_btn);
        final ImageView ivLogout = findViewById(R.id.profile_iv_logout);
        final ImageView ivEdit = findViewById(R.id.profile_edit_btn);
        final ImageView ivEmergency = findViewById(R.id.profile_eh_btn);

        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userManager.clearUserDetails();
                hospitalManager.clearHospitalsDetails();
                session.logoutUser();
                NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancelAll();
            }
        });
        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, ProfileEditActivity.class);
                startActivity(intent);
            }
        });
        ivEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+911));
                if (ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                context.startActivity(callIntent);
            }
        });
    }

    private void setupMenu(){
        final ImageView ivHospitals = findViewById(R.id.profile_iv_hospitals);
        final ImageView ivHotlines = findViewById(R.id.profile_iv_hotlines);
        final ImageView ivContacts = findViewById(R.id.profile_iv_contacts);
        final ImageView ivProfile = findViewById(R.id.profile_iv_profile);

        ivHospitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, HospitalActivity.class);
                startActivity(intent);
            }
        });

        ivHotlines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, HotlineActivity.class);
                startActivity(intent);
            }
        });

        ivContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, ContactActivity.class);
                startActivity(intent);
            }
        });

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
